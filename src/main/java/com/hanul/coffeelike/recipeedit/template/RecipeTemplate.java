package com.hanul.coffeelike.recipeedit.template;

import com.hanul.coffeelike.caramelweb.data.RecipeCategory;

public final class RecipeTemplate{
	public RecipeCategory category;
	public String title;
	public final AttachmentTemplate coverImage = new AttachmentTemplate();
	public StepTemplate[] steps;
}
