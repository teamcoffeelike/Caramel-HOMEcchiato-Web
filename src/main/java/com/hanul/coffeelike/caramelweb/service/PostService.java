package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.PostDAO;
import com.hanul.coffeelike.caramelweb.data.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService{

	@Autowired private PostDAO dao;

	public List<Post> recentPosts(@Nullable Integer integer){
		return dao.recentPosts(integer);
	}

	@Nullable public Post post(int id, @Nullable Integer loginUser){
		return dao.post(id, loginUser);
	}

	public int writePost(int loginUser, String text){
		int postId = dao.generatePostId();
		dao.writePost(postId, loginUser, text);
		return postId;
	}

	public PostResult editPost(int loginUser, int post, String text){
		Post postData = dao.findPostData(post);

		if(postData==null)
			return new PostResult("no_post");
		if(loginUser!=postData.getAuthor())
			return new PostResult("cannot_edit");

		dao.editPost(post, text);
		return new PostResult();
	}

	public PostResult deletePost(int loginUser, int post){
		Post postData = dao.findPostData(post);

		if(postData==null)
			return new PostResult("no_post");
		if(loginUser!=postData.getAuthor())
			return new PostResult("cannot_delete");

		dao.deletePost(post);
		return new PostResult();
	}

	public PostResult likePost(int loginUser, int post, boolean like){
		Post postData = dao.findPostData(post);

		if(postData==null)
			return new PostResult("no_post");

		if(like) dao.likePost(loginUser, post);
		else dao.cancleLikePost(loginUser, post);
		return new PostResult();
	}

	public static class PostResult{
		@Nullable private final String error;

		public PostResult(){
			this.error = null;
		}
		public PostResult(String error){
			this.error = error;
		}

		@Nullable public String getError(){
			return error;
		}
	}
}
