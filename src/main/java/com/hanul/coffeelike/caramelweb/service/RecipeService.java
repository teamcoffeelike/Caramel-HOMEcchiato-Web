package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.RecipeDAO;
import com.hanul.coffeelike.caramelweb.data.Recipe;
import com.hanul.coffeelike.caramelweb.data.RecipeCategory;
import com.hanul.coffeelike.caramelweb.data.RecipeCover;
import com.hanul.coffeelike.caramelweb.data.RecipeCoverListResult;
import com.hanul.coffeelike.caramelweb.data.RecipeRateResult;
import com.hanul.coffeelike.caramelweb.data.RecipeStep;
import com.hanul.coffeelike.caramelweb.data.RecipeWriteResult;
import com.hanul.coffeelike.caramelweb.util.Validate;
import com.hanul.coffeelike.recipeedit.ast.RecipeEditMode;
import com.hanul.coffeelike.recipeedit.ast.RecipeEditMode.EditMode;
import com.hanul.coffeelike.recipeedit.ast.RecipeEditMode.WriteMode;
import com.hanul.coffeelike.recipeedit.ast.RecipeEditorAST;
import com.hanul.coffeelike.recipeedit.exception.RecipeEditorException;
import com.hanul.coffeelike.recipeedit.template.RecipeTemplate;
import com.hanul.coffeelike.recipeedit.template.StepTemplate;
import com.hanul.coffeelike.recipeedit.visitor.RecipeEditor;
import com.hanul.coffeelike.recipeedit.visitor.RecipeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

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
		RecipeTemplate t = new RecipeTemplate();
		t.category = recipeCategory;
		t.title = title;
		t.coverImage.setFile(coverImage);

		t.steps = new StepTemplate[stepsList.size()];
		for(int i = 0; i<stepsList.size(); i++){
			StepTemplate st = new StepTemplate();
			Entry<MultipartFile, String> e = stepsList.get(i);
			MultipartFile file = e.getKey();
			if(file!=null) st.image.setFile(file);
			st.text = e.getValue();
			t.steps[i] = st;
		}

		return write(author, t, null);
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

			return write(author, template, null);
		}else if(mode instanceof EditMode){
			int recipeId = ((EditMode)mode).id;
			Recipe recipe = recipe(recipeId, null);
			if(recipe==null) return new RecipeWriteResult("no_recipe");
			else if(recipe.getCover().getAuthor().getId()!=author) return new RecipeWriteResult("cannot_edit");

			RecipeEditor editor = new RecipeEditor(recipe);
			for(RecipeEditorAST f : functions) f.visit(editor);
			RecipeTemplate template = editor.compile();

			return write(author, template, recipe);
		}else{
			LOGGER.error("액션이 정의되지 않은 Mode "+mode);
			return new RecipeWriteResult("unexpected");
		}
	}

	private RecipeWriteResult write(int author, RecipeTemplate template, @Nullable Recipe editingRecipe){
		if(template.title!=null&&!Validate.recipeTitle(template.title = template.title.trim())){
			return new RecipeWriteResult("bad_title");
		}

		for(StepTemplate step : template.steps){
			if(step.text!=null&&!Validate.recipeStep(step.text)) return new RecipeWriteResult("bad_step_text");
		}

		if(editingRecipe!=null){
			int recipeId = editingRecipe.getCover().getId();

			String coverImageId;
			if(template.coverImage.isSet()){
				if(template.coverImage.imageId!=null){
					coverImageId = template.coverImage.imageId;
				}else{
					coverImageId = fileService.saveRecipeCoverImage(recipeId, Objects.requireNonNull(template.coverImage.getFile()));
				}
			}else coverImageId = null;

			try{
				for(int i = 0; i<template.steps.length; i++){
					StepTemplate step = template.steps[i];
					if(step.image.imageId==null&&step.image.getFile()!=null){
						step.image.imageId = fileService.saveRecipeStepImage(recipeId, i, step.image.getFile());
					}
				}

				recipeDAO.updateRecipe(recipeId, template.title, coverImageId, template.category);

				try{
					for(int index = 0; index<template.steps.length; index++){
						StepTemplate step = template.steps[index];
						if(index<editingRecipe.getSteps().size()){
							// UPDATE, 모든 데이터 복붙
							recipeDAO.updateRecipeStep(recipeId,
									index,
									step.image.imageId,
									step.text);
						}else{
							// 새 step INSERT
							recipeDAO.insertRecipeStep(recipeId,
									index,
									step.image.imageId,
									step.text);
						}
					}
					recipeDAO.trimStep(recipeId, template.steps.length);
				}catch(Exception ex){
					LOGGER.error("레시피 {} 수정 중 오류 발생.\n"+
							"진행된 수정 작업은 되돌려지지 않으며 백업 시스템을 구축할 정도로 본인이 한가하지 못하기 때문에, 데이터 일부가 손상되었을 수 있습니다.", editingRecipe.getCover().getId(), ex);
					throw ex;
				}

				String originalCover = editingRecipe.getCover().getCoverImage();
				if(coverImageId!=null&&originalCover!=null&&!coverImageId.equals(originalCover)){
					fileService.removeRecipeCoverImage(originalCover);
				}

				Set<String> resources = new HashSet<>();
				for(StepTemplate step : template.steps){
					if(step.image.imageId!=null) resources.add(step.image.imageId);
				}

				for(RecipeStep step : editingRecipe.getSteps()){
					String image = step.getImage();
					if(image!=null&&!resources.contains(image)){
						fileService.removeRecipeStepImage(image);
					}
				}

				return new RecipeWriteResult(recipeId);
			}catch(Exception ex){
				LOGGER.error("레시피 저장 중 오류 발생", ex);
				if(coverImageId!=null) fileService.removeRecipeCoverImage(coverImageId);
				return new RecipeWriteResult("unexpected");
			}
		}else{
			int recipeId = recipeDAO.generateRecipeId();

			String coverImageId = fileService.saveRecipeCoverImage(recipeId, template.coverImage.getFile());

			try{
				for(int i = 0; i<template.steps.length; i++){
					StepTemplate step = template.steps[i];
					MultipartFile image = step.image.getFile();

					if(image!=null) step.image.imageId = fileService.saveRecipeStepImage(recipeId, i, image);
				}

				recipeDAO.insertRecipe(recipeId, author, template.title, coverImageId, template.category);

				try{
					for(int index = 0; index<template.steps.length; index++){
						StepTemplate step = template.steps[index];
						recipeDAO.insertRecipeStep(recipeId,
								index,
								step.image.imageId,
								Objects.requireNonNull(step.text));
					}

					return new RecipeWriteResult(recipeId);
				}catch(Exception ex){
					recipeDAO.deleteRecipeAndSteps(recipeId);
					throw ex;
				}
			}catch(Exception ex){
				LOGGER.error("레시피 저장 중 오류 발생", ex);
				fileService.removeRecipeCoverImage(coverImageId);
				for(StepTemplate step : template.steps){
					if(step.image.imageId!=null){
						fileService.removeRecipeStepImage(step.image.imageId);
					}
				}
				return new RecipeWriteResult("unexpected");
			}
		}
	}
}
