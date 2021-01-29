package com.hanul.coffeelike.caramelweb.util;

import com.hanul.coffeelike.caramelweb.data.Post;
import com.hanul.coffeelike.caramelweb.data.Recipe;
import com.hanul.coffeelike.caramelweb.data.RecipeCover;
import com.hanul.coffeelike.caramelweb.data.RecipeStep;
import com.hanul.coffeelike.caramelweb.data.UserProfileData;
import org.springframework.lang.Nullable;

import java.io.File;

public final class AttachmentFileResolver{
	private AttachmentFileResolver(){}

	private static final File STORAGE_ROOT = new File("/images");

	public static File getStorage(AttachmentType type){
		return new File(STORAGE_ROOT, type.storageName);
	}

	/**
	 * @return 전달받은 파일명에 대응하는 프로필 이미지 파일.
	 */
	public static File getProfileImageFile(String filename){
		return new File(getStorage(AttachmentType.PROFILE_IMAGE), filename);
	}

	public static boolean doesProfileImageExists(@Nullable String filename){
		return filename!=null&&getProfileImageFile(filename).exists();
	}

	/**
	 * @return 전달받은 파일명에 대응하는 포스트 이미지 파일.
	 */
	public static File getPostImageFile(String filename){
		return new File(getStorage(AttachmentType.POST_IMAGE), filename);
	}

	public static boolean doesPostImageExists(@Nullable String filename){
		return filename!=null&&getPostImageFile(filename).exists();
	}

	/**
	 * @return 전달받은 파일명에 대응하는 레시피 커버 이미지 파일.
	 */
	public static File getRecipeCoverImageFile(String filename){
		return new File(getStorage(AttachmentType.RECIPE_COVER), filename);
	}

	public static boolean doesRecipeCoverImageExists(@Nullable String filename){
		return filename!=null&&getRecipeCoverImageFile(filename).exists();
	}

	/**
	 * @return 전달받은 파일명에 대응하는 레시피 스텝 이미지 파일.
	 */
	public static File getRecipeStepImageFile(String filename){
		return new File(getStorage(AttachmentType.RECIPE_STEP), filename);
	}

	public static boolean doesRecipeStepImageExists(@Nullable String filename){
		return filename!=null&&getRecipeStepImageFile(filename).exists();
	}

	public static void resolve(Recipe r){
		RecipeCover cover = r.getCover();
		resolve(cover);
		for(RecipeStep step : r.getSteps()){
			if(AttachmentFileResolver.doesRecipeStepImageExists(step.getImage())){
				step.setImage(AttachmentURLConverter.recipeStepImageFromId(step.getRecipe(), step.getStep()));
			}else{
				step.setImage(null);
			}
		}
	}

	public static void resolve(RecipeCover cover){
		if(AttachmentFileResolver.doesRecipeCoverImageExists(cover.getCoverImage())){
			cover.setCoverImage(AttachmentURLConverter.recipeCoverImageFromId(cover.getId()));
		}else cover.setCoverImage("imgs/post.png");
		resolve(cover.getAuthor());
	}

	public static void resolve(UserProfileData user){
		if(AttachmentFileResolver.doesProfileImageExists(user.getProfileImage())){
			user.setProfileImage(AttachmentURLConverter.profileImageFromId(user.getId()));
		}else user.setProfileImage("imgs/profile.png");
	}

	public static void resolve(Post post){
		if(AttachmentFileResolver.doesPostImageExists(post.getImage())){
			post.setImage(AttachmentURLConverter.postImageFromId(post.getId()));
		}else post.setImage("imgs/post.png");
		resolve(post.getAuthor());
	}
}
