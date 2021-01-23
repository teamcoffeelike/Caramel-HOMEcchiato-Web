package com.hanul.coffeelike.caramelweb.dao;

import com.hanul.coffeelike.caramelweb.data.RecipeCategory;
import com.hanul.coffeelike.caramelweb.data.RecipeCover;
import com.hanul.coffeelike.caramelweb.data.RecipeStep;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RecipeDAO{
	@Autowired
	private SqlSession sql;

	public List<RecipeCover> list(@Nullable Integer loginUser,
	                              @Nullable Date since,
	                              int pages,
	                              @Nullable String category,
	                              @Nullable Integer author){
		Map<String, Object> m = new HashMap<>();
		if(loginUser!=null) m.put("loginUser", loginUser);
		if(since!=null) m.put("since", since);
		m.put("pages", pages);
		if(category!=null) m.put("category", category);
		if(author!=null) m.put("author", author);
		return sql.selectList("recipe.list", m);
	}

	@Nullable public RecipeCover getCover(int id, @Nullable Integer loginUser){
		Map<String, Object> m = new HashMap<>();
		m.put("id", id);
		if(loginUser!=null) m.put("loginUser", loginUser);
		return sql.selectOne("recipe.getCover", m);
	}

	public List<RecipeStep> steps(int id){
		return sql.selectList("recipe.steps", id);
	}

	public boolean checkIfRecipeExists(int id){
		return sql.selectOne("recipe.checkIfRecipeExists", id)!=null;
	}

	public int generateRecipeId(){
		return sql.selectOne("recipe.generateRecipeId");
	}

	public void insertRecipe(int id, int author, String title, String coverImageId, RecipeCategory recipeCategory){
		Map<String, Object> m = new HashMap<>();
		m.put("id", id);
		m.put("category", recipeCategory);
		m.put("title", title);
		m.put("author", author);
		m.put("coverImage", coverImageId);
		sql.insert("recipe.insertRecipe", m);
	}

	public void insertRecipeStep(int recipe, int step, @Nullable String image, String text){
		Map<String, Object> m = new HashMap<>();
		m.put("step", step);
		m.put("recipe", recipe);
		if(image!=null) m.put("image", image);
		m.put("text", text);
		sql.insert("recipe.insertRecipeStep", m);
	}

	public void updateRecipe(int id, @Nullable String title, @Nullable String coverImageId, @Nullable RecipeCategory recipeCategory){
		Map<String, Object> m = new HashMap<>();
		m.put("id", id);
		if(recipeCategory!=null) m.put("category", recipeCategory);
		if(title!=null) m.put("title", title);
		if(coverImageId!=null) m.put("coverImage", coverImageId);
		sql.insert("recipe.updateRecipe", m);
	}

	public void updateRecipeStep(int recipe, int step, @Nullable String image, String text){
		Map<String, Object> m = new HashMap<>();
		m.put("step", step);
		m.put("recipe", recipe);
		if(image!=null) m.put("image", image);
		m.put("text", text);
		sql.update("recipe.updateRecipeStep", m);
	}

	/**
	 * {@code steps} 이상 인덱스 값을 가지는 step 삭제
	 */
	public void trimStep(int recipe, int steps){
		Map<String, Object> m = new HashMap<>();
		m.put("recipe", recipe);
		m.put("steps", steps);
		sql.delete("recipe.trimStep", m);
	}

	/**
	 * <b>주의:</b> isDeleted 체크가 아니라 DB 내부 데이터 삭제. Fallback 옵션.
	 */
	public void deleteRecipeAndSteps(int recipeId){
		sql.delete("recipe.deleteRecipeAndSteps", recipeId);
	}

	public void markDeleted(int recipe){
		sql.update("recipe.markDeleted", recipe);
	}

	public void insertRecipeRating(int user, int recipe, double rating){
		Map<String, Object> m = new HashMap<>();
		m.put("user", user);
		m.put("recipe", recipe);
		m.put("rating", rating);
		sql.delete("recipe.insertRecipeRating", m);
	}

	public void deleteRecipeRating(int user, int recipe){
		Map<String, Object> m = new HashMap<>();
		m.put("user", user);
		m.put("recipe", recipe);
		sql.delete("recipe.deleteRecipeRating", m);
	}
}
