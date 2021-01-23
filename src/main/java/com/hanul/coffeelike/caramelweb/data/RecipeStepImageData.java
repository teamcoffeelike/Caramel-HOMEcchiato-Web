package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

public class RecipeStepImageData{
	private int recipe;
	private int step;
	@Nullable private String image;

	public RecipeStepImageData(int recipe, int step, @Nullable String image){
		this.recipe = recipe;
		this.step = step;
		this.image = image;
	}

	public int getRecipe(){
		return recipe;
	}
	public void setRecipe(int recipe){
		this.recipe = recipe;
	}
	public int getStep(){
		return step;
	}
	public void setStep(int step){
		this.step = step;
	}
	@Nullable public String getImage(){
		return image;
	}
	public void setImage(@Nullable String image){
		this.image = image;
	}
}
