package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.RecipeDAO;
import com.hanul.coffeelike.caramelweb.data.Recipe;
import com.hanul.coffeelike.caramelweb.data.RecipeCover;
import com.hanul.coffeelike.caramelweb.data.RecipeStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class RecipeService{
	@Autowired
	private RecipeDAO recipeDAO;

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
}
