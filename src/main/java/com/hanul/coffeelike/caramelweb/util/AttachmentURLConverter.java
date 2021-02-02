package com.hanul.coffeelike.caramelweb.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public final class AttachmentURLConverter{
	private AttachmentURLConverter(){}

	public static String profileImageFromId(int userId){
		return resolve("profileImage", "id="+userId);
	}

	public static String postImageFromId(int postId){
		return resolve("postImage", "id="+postId);
	}

	public static String recipeCoverImageFromId(int recipeId){
		return resolve("recipeImage/cover", "id="+recipeId);
	}

	public static String recipeStepImageFromId(int recipe, int index){
		return resolve("recipeImage/step", "recipe="+recipe+"&step="+index);
	}

	private static String resolve(String type, String query){
		return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()+"/images/"+type+"?"+query;
	}
}
