package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

import java.util.List;

public final class RecipeCoverListResult{
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
