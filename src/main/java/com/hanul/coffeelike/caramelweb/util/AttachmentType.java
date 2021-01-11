package com.hanul.coffeelike.caramelweb.util;

public enum AttachmentType{
	PROFILE_IMAGE("profileImage", "ProfileImage"),
	POST_IMAGE("postImage", "PostImage"),
	RECIPE_COVER("recipeCover", "RecipeCover"),
	RECIPE_STEP("recipeStep", "RecipeStep");

	public final String storageName;
	public final String baseFileName;

	AttachmentType(String storageName, String baseFileName){
		this.storageName = storageName;
		this.baseFileName = baseFileName;
	}
}
