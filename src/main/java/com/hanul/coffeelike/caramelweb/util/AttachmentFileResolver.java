package com.hanul.coffeelike.caramelweb.util;

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

}
