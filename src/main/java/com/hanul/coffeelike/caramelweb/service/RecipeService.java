package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.RecipeDAO;
import com.hanul.coffeelike.caramelweb.data.Recipe;
import com.hanul.coffeelike.caramelweb.data.RecipeCategory;
import com.hanul.coffeelike.caramelweb.data.RecipeCover;
import com.hanul.coffeelike.caramelweb.data.RecipeStep;
import com.hanul.coffeelike.caramelweb.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;
import java.util.Map.Entry;

@Service
public class RecipeService{
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

	@Nullable public Recipe recipe(int id){
		RecipeCover cover = recipeDAO.getCover(id);
		if(cover==null) return null;
		List<RecipeStep> steps = recipeDAO.steps(id);
		return new Recipe(cover, steps);
	}

	public RecipeCover getCover(int id){
		return recipeDAO.getCover(id);
	}

	public boolean checkIfRecipeExists(int id){
		return recipeDAO.checkIfRecipeExists(id);
	}

	public void delete(int recipe){
		// TODO
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

		recipeDAO.insertRecipe(author, title, coverImageId, recipeCategory);

		for(int i = 0; i<stepsList.size(); i++){
			Entry<MultipartFile, String> e = stepsList.get(i);
			MultipartFile image = e.getKey();
			String stepImageId = image==null ? null : fileService.saveRecipeStepImage(recipeId, i, image);
			String text = e.getValue();

			recipeDAO.insertRecipeStep(recipeId, i, stepImageId, text);
		}

		return new RecipeWriteResult(recipeId);
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
}
