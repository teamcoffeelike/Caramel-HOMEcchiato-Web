package com.hanul.coffeelike.recipeedit.exception.compile;

public class InvalidRecipeCategory extends CompileException{
	public InvalidRecipeCategory(String recipeCategory){
		super("부적합한 레시피 카테고리 "+recipeCategory);
	}

	@Override public String jsonErrorMessage(){
		return INVALID_RECIPE_CATEGORY;
	}
}
