package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.PostDAO;
import com.hanul.coffeelike.caramelweb.data.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PostService{
	@Autowired
	private PostDAO postDAO;
	@Autowired
	private FileService fileService;

	public List<Post> recentPosts(@Nullable Integer integer){
		return postDAO.recentPosts(integer);
	}

	@Nullable public Post post(int id, @Nullable Integer loginUser){
		return postDAO.findPost(id, loginUser);
	}

	@Nullable public Integer writePost(int loginUser, String text, MultipartFile image){
		int postId = postDAO.generatePostId();

		String imageId = fileService.savePostImage(postId, image);
		if(imageId==null) return null;

		if(!postDAO.writePost(postId, loginUser, text, imageId)){
			fileService.removePostImage(imageId);
			return null;
		}
		return postId;
	}

	public PostResult editPost(int loginUser, int post, String text){
		Post postData = postDAO.findPost(post, null);

		if(postData==null)
			return new PostResult("no_post");
		if(loginUser!=postData.getAuthor().getId())
			return new PostResult("cannot_edit");

		postDAO.editPost(post, text);
		return new PostResult();
	}

	public PostResult deletePost(int loginUser, int post){
		Post postData = postDAO.findPost(post, null);

		if(postData==null)
			return new PostResult("no_post");
		if(loginUser!=postData.getAuthor().getId())
			return new PostResult("cannot_delete");

		postDAO.deletePost(post);
		if(postData.getImage()!=null) fileService.removePostImage(postData.getImage());
		return new PostResult();
	}

	public PostResult likePost(int loginUser, int post, boolean like){
		Post postData = postDAO.findPost(post, null);

		if(postData==null)
			return new PostResult("no_post");

		if(like) postDAO.like(loginUser, post);
		else postDAO.removeLike(loginUser, post);
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
