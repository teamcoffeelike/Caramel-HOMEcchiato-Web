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

	@Nullable public RecipeCover getCover(int id){
		return sql.selectOne("recipe.getCover", id);
	}

	public List<RecipeStep> steps(int id){
		return sql.selectOne("recipe.steps", id);
	}

	public boolean checkIfRecipeExists(int id){
		return sql.selectOne("recipe.checkIfRecipeExists", id)!=null;
	}

	public int generateRecipeId(){
		return sql.selectOne("recipe.generateRecipeId");
	}

	public void insertRecipe(int author, String title, String coverImageId, RecipeCategory recipeCategory){
		Map<String, Object> m = new HashMap<>();
		m.put("author", author);
		m.put("title", title);
		m.put("coverImage", coverImageId);
		m.put("recipeCategory", recipeCategory);
		sql.insert("recipe.insertRecipe", m);
	}

	public void insertRecipeStep(int recipe, int index, @Nullable String image, String text){
		Map<String, Object> m = new HashMap<>();
		m.put("recipe", recipe);
		m.put("index", index);
		if(image!=null) m.put("image", image);
		m.put("text", text);
		sql.insert("recipe.insertRecipeStep", m);
	}
}
