package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.RecipeDAO;
import com.hanul.coffeelike.caramelweb.data.Recipe;
import com.hanul.coffeelike.caramelweb.data.RecipeCategory;
import com.hanul.coffeelike.caramelweb.data.RecipeCover;
import com.hanul.coffeelike.caramelweb.data.RecipeStep;
import com.hanul.coffeelike.caramelweb.util.Validate;
import com.hanul.coffeelike.caramelweb.util.recipeedit.RecipeEditMode;
import com.hanul.coffeelike.caramelweb.util.recipeedit.RecipeEditMode.EditMode;
import com.hanul.coffeelike.caramelweb.util.recipeedit.RecipeEditMode.WriteMode;
import com.hanul.coffeelike.caramelweb.util.recipeedit.RecipeEditor;
import com.hanul.coffeelike.caramelweb.util.recipeedit.RecipeEditorAST;
import com.hanul.coffeelike.caramelweb.util.recipeedit.RecipeEditorException;
import com.hanul.coffeelike.caramelweb.util.recipeedit.RecipeTemplate;
import com.hanul.coffeelike.caramelweb.util.recipeedit.RecipeWriter;
import com.hanul.coffeelike.caramelweb.util.recipeedit.StepTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

@Service
public class RecipeService{
	private static final Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);

	@Autowired
	private RecipeDAO recipeDAO;
	@Autowired
	private FileService fileService;

	public RecipeCoverListResult list(@Nullable Integer loginUser,
	                                  @Nullable Date since,
	                                  int pages,
	                                  @Nullable String category,
	                                  @Nullable Integer author){
		if(pages<1||pages>50) return new RecipeCoverListResult("bad_pages");
		List<RecipeCover> list = recipeDAO.list(loginUser, since, pages+1, category, author);
		if(list.size()>pages){
			while(list.size()>pages) list.remove(pages);
			return new RecipeCoverListResult(list, false);
		}
		return new RecipeCoverListResult(list, true);
	}

	@Nullable public Recipe recipe(int id, @Nullable Integer loginUser){
		RecipeCover cover = recipeDAO.getCover(id, loginUser);
		if(cover==null) return null;
		List<RecipeStep> steps = recipeDAO.steps(id);
		return new Recipe(cover, steps);
	}

	public RecipeCover getCover(int id, @Nullable Integer loginUser){
		return recipeDAO.getCover(id, loginUser);
	}

	public boolean checkIfRecipeExists(int id){
		return recipeDAO.checkIfRecipeExists(id);
	}

	public void delete(int recipe){
		recipeDAO.markDeleted(recipe);
	}

	public RecipeWriteResult writeRecipe(int author,
	                                     String title,
	                                     MultipartFile coverImage,
	                                     RecipeCategory recipeCategory,
	                                     List<Entry<MultipartFile, String>> stepsList){
		if(!Validate.recipeTitle(title = title.trim())){
			return new RecipeWriteResult("bad_title");
		}

		for(Entry<MultipartFile, String> e : stepsList){
			if(!Validate.recipeStep(e.getValue())) return new RecipeWriteResult("bad_step_text");
		}

		int recipeId = recipeDAO.generateRecipeId();

		String coverImageId = fileService.saveRecipeCoverImage(recipeId, coverImage);

		List<Entry<String, String>> steps = new ArrayList<>();
		try{
			for(int i = 0; i<stepsList.size(); i++){
				Entry<MultipartFile, String> e = stepsList.get(i);
				MultipartFile image = e.getKey();

				steps.add(new SimpleEntry<>(
						image==null ? null : fileService.saveRecipeStepImage(recipeId, i, image),
						e.getValue()));
			}

			recipeDAO.insertRecipe(recipeId, author, title, coverImageId, recipeCategory);

			try{
				for(int index = 0; index<steps.size(); index++){
					Entry<String, String> e = steps.get(index);
					recipeDAO.insertRecipeStep(recipeId, index, e.getKey(), e.getValue());
				}

				return new RecipeWriteResult(recipeId);
			}catch(Exception ex){
				recipeDAO.deleteRecipeAndSteps(recipeId);
				throw ex;
			}
		}catch(Exception ex){
			LOGGER.error("레시피 저장 중 오류 발생", ex);
			fileService.removeRecipeCoverImage(coverImageId);
			for(Entry<String, String> e : steps){
				String stepImageId = e.getKey();
				if(stepImageId!=null){
					fileService.removeRecipeStepImage(stepImageId);
				}
			}
			return new RecipeWriteResult("unexpected");
		}
	}

	public RecipeRateResult rateRecipe(int user, int recipe, double rating){
		if(rating<0||rating>5){
			return new RecipeRateResult("bad_rating");
		}
		if(!recipeDAO.checkIfRecipeExists(recipe)) return new RecipeRateResult("no_recipe");
		recipeDAO.deleteRecipeRating(user, recipe);
		recipeDAO.insertRecipeRating(user, recipe, rating);
		return new RecipeRateResult();
	}

	public boolean deleteRecipeRating(int user, int recipe){
		if(!recipeDAO.checkIfRecipeExists(recipe)) return false;
		recipeDAO.deleteRecipeRating(user, recipe);
		return true;
	}

	public RecipeWriteResult editRecipe(int author,
	                                    RecipeEditMode mode,
	                                    List<RecipeEditorAST> functions) throws RecipeEditorException{
		if(mode instanceof WriteMode){
			RecipeWriter writer = new RecipeWriter();
			for(RecipeEditorAST f : functions) f.visit(writer);
			RecipeTemplate template = writer.compile();

			int recipeId = recipeDAO.generateRecipeId();

			String coverImageId = fileService.saveRecipeCoverImage(recipeId, template.getCoverImage());

			List<Entry<String, String>> steps = new ArrayList<>();
			try{
				for(int i = 0; i<template.getSteps().length; i++){
					StepTemplate step = template.getSteps()[i];
					MultipartFile image = step.getImage();

					steps.add(new SimpleEntry<>(
							image==null ? null : fileService.saveRecipeStepImage(recipeId, i, image),
							step.getText()));
				}

				recipeDAO.insertRecipe(recipeId, author, template.getTitle(), coverImageId, template.getCategory());

				try{
					for(int index = 0; index<steps.size(); index++){
						Entry<String, String> e = steps.get(index);
						recipeDAO.insertRecipeStep(recipeId, index, e.getKey(), e.getValue());
					}

					return new RecipeWriteResult(recipeId);
				}catch(Exception ex){
					recipeDAO.deleteRecipeAndSteps(recipeId);
					throw ex;
				}
			}catch(Exception ex){
				LOGGER.error("레시피 저장 중 오류 발생", ex);
				fileService.removeRecipeCoverImage(coverImageId);
				for(Entry<String, String> e : steps){
					String stepImageId = e.getKey();
					if(stepImageId!=null){
						fileService.removeRecipeStepImage(stepImageId);
					}
				}
				return new RecipeWriteResult("unexpected");
			}
		}else if(mode instanceof EditMode){
			Recipe recipe = recipe(((EditMode)mode).id, null);
			if(recipe==null) throw new RecipeEditorException("존재하지 않는 레시피 수정");
			RecipeEditor editor = new RecipeEditor(recipe);

			// TODO
			return new RecipeWriteResult("FUCK");
		}else throw new RecipeEditorException("액션이 정의되지 않은 Mode "+mode);
	}


	public static final class RecipeCoverListResult{
		@Nullable private final List<RecipeCover> recipes;
		@Nullable private final Boolean endOfList;
		@Nullable private final String error;

		public RecipeCoverListResult(List<RecipeCover> recipes, boolean endOfList){
			this.recipes = recipes;
			this.error = null;
			this.endOfList = endOfList;
		}

		public RecipeCoverListResult(String error){
			this.recipes = null;
			this.error = error;
			this.endOfList = null;
		}

		@Nullable public List<RecipeCover> getRecipes(){
			return recipes;
		}
		@Nullable public String getError(){
			return error;
		}
		@Nullable public Boolean isEndOfList(){
			return endOfList;
		}
	}

	public static final class RecipeWriteResult{
		@Nullable private final Integer id;
		@Nullable private final String error;

		public RecipeWriteResult(@Nullable Integer id){
			this.id = id;
			this.error = null;
		}
		public RecipeWriteResult(@Nullable String error){
			this.id = null;
			this.error = error;
		}

		@Nullable public Integer getId(){
			return id;
		}
		@Nullable public String getError(){
			return error;
		}
	}

	public static final class RecipeRateResult{
		@Nullable private final String error;

		public RecipeRateResult(){
			this(null);
		}
		public RecipeRateResult(@Nullable String error){
			this.error = error;
		}

		@Nullable public String getError(){
			return error;
		}
	}
}
