package com.hanul.coffeelike.caramelweb.util;

import java.sql.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.hanul.coffeelike.caramelweb.data.*;

/**
 * 귀찮은 Gson 인스턴스를 거치지 않고 JSON 객체를 생성할 수 있도록 하는 유틸리티 클래스.
 */
public final class JsonHelper{
	public static final Gson GSON = new GsonBuilder()
			.registerTypeAdapter(RecipeCategory.class, RecipeCategory.Json.INSTANCE)
			.registerTypeAdapter(RecipeTask.Timer.class, RecipeTask.Timer.Json.INSTANCE)
			.registerTypeAdapter(UserProfileData.class, UserProfileData.Json.INSTANCE)
			.registerTypeAdapter(Post.class, Post.Json.INSTANCE)
			.registerTypeAdapter(RecipeStep.class, RecipeStep.Json.INSTANCE)
			.registerTypeAdapter(RecipeCover.class, RecipeCover.Json.INSTANCE)
			.registerTypeAdapter(Date.class, DateSerializer.INSTANCE)
			.create();

	/**
	 * {@code error} 키 값으로 전달받은 에러 메시지를 가지는 JSON 오브젝트를 텍스트 형태로 반환합니다.
	 *
	 * @param error 에러 메시지
	 * @return 텍스트 형태의 JSON
	 */
	public static String failure(String error){
		JsonObject o = new JsonObject();
		o.addProperty("error", error);

		return GSON.toJson(o);
	}
}
