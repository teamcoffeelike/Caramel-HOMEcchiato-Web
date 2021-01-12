package com.hanul.coffeelike.caramelweb.dao;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.hanul.coffeelike.caramelweb.data.Post;

@Repository
public class PostDAO{
	@Autowired
	private SqlSession sql;

	public List<Post> recentPosts(@Nullable Integer loginUser, Date since, int pages){
		Map<String, Object> m = new HashMap<>();
		m.put("loginUser", loginUser);
		m.put("since", since);
		m.put("pages", pages);
		return sql.selectList("post.recentPosts", m);
	}

	@Nullable public Post findPost(int id,
	                               @Nullable Integer loginUser){
		Map<String, Integer> m = new HashMap<>();
		m.put("id", id);
		m.put("loginUser", loginUser);
		return sql.selectOne("post.findPost", m);
	}

	public int generatePostId(){
		return sql.selectOne("post.generatePostId");
	}

	public boolean writePost(int postId,
	                         int loginUser,
	                         String text,
	                         String imageId){
		Map<String, Object> m = new HashMap<>();
		m.put("postId", postId);
		m.put("loginUser", loginUser);
		m.put("text", text);
		m.put("image", imageId);
		return sql.insert("post.writePost", m)>0;
	}

	public void editPost(int post, String text){
		Map<String, Object> m = new HashMap<>();
		m.put("post", post);
		m.put("text", text);
		sql.update("post.editPost", m);
	}

	public boolean editPostImage(int post, String imageId) {
		Map<String, Object> m = new HashMap<>();
		m.put("post", post);
		m.put("image", imageId);
		return sql.update("post.editPostImage", m)>0;
	}

	public void deletePost(int post){
		sql.update("post.deletePost", post);
	}

	public int like(int loginUser, int post){
		Map<String, Object> m = new HashMap<>();
		m.put("loginUser", loginUser);
		m.put("post", post);
		return sql.insert("post.like", m);
	}

	public int removeLike(int loginUser, int post){
		Map<String, Object> m = new HashMap<>();
		m.put("loginUser", loginUser);
		m.put("post", post);
		return sql.delete("post.removeLike", m);
	}
}
