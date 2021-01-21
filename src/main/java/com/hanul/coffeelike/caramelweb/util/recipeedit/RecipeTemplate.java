package com.hanul.coffeelike.caramelweb.util.recipeedit;

import com.hanul.coffeelike.caramelweb.data.RecipeCategory;
import org.springframework.web.multipart.MultipartFile;

public final class RecipeTemplate{
	RecipeCategory category;
	String title;
	MultipartFile coverImage;
	StepTemplate[] steps;

	public RecipeCategory getCategory(){
		return category;
	}
	public String getTitle(){
		return title;
	}
	public MultipartFile getCoverImage(){
		return coverImage;
	}
	public StepTemplate[] getSteps(){
		return steps;
	}
}
