package com.hanul.coffeelike.caramelweb.dao;

import com.hanul.coffeelike.caramelweb.data.PostImageData;
import com.hanul.coffeelike.caramelweb.data.ProfileImageData;
import com.hanul.coffeelike.caramelweb.data.RecipeCoverImageData;
import com.hanul.coffeelike.caramelweb.data.RecipeStepImageData;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class FileDAO{
	@Autowired
	private SqlSession sql;

	@Nullable public ProfileImageData findProfileImage(int user){
		return sql.selectOne("file.findProfileImage", user);
	}

	public boolean setUserProfileImage(int user, @Nullable String profileImage){
		Map<String, Object> m = new HashMap<>();
		m.put("user", user);
		m.put("profileImage", profileImage);
		return sql.update("file.setUserProfileImage", m)>0;
	}

	public void removeProfileImage(int userId){
		sql.update("file.removeProfileImage", userId);
	}

	@Nullable public PostImageData findPostImage(int postId){
		return sql.selectOne("file.findPostImage", postId);
	}


	@Nullable public RecipeCoverImageData findRecipeCoverImage(int recipeId){
		return sql.selectOne("file.findRecipeCoverImage", recipeId);
	}

	@Nullable public RecipeStepImageData findRecipeStepImage(int recipe, int step){
		Map<String, Object> m = new HashMap<>();
		m.put("recipe", recipe);
		m.put("step", step);
		return sql.selectOne("file.findRecipeStepImage", m);
	}
}
