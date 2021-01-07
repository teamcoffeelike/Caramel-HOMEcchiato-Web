package com.hanul.coffeelike.caramelweb.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanul.coffeelike.caramelweb.dao.PostDAO;
import com.hanul.coffeelike.caramelweb.data.Post;
import com.hanul.coffeelike.caramelweb.util.Validate;

@Service
public class PostService{
	@Autowired
	private PostDAO postDAO;
	@Autowired
	private FileService fileService;

	/**
	 * 포스트 작성<br>
	 * <br>
	 * <b>에러: </b><br>
	 * bad_pages : 유효하지 않은 image<br>
	 * bad_text   : 유효하지 않은 text<br>
	 * unexpected : 포스트 생성 불가<br>
	 */
	public PostListResult recentPosts(@Nullable Integer loginUser,
	                              @Nullable Date since,
	                              int pages){
		if(pages<1||pages>50) {
			return new PostListResult("bad_pages");
		}
		return new PostListResult(postDAO.recentPosts(loginUser, since, pages));
	}

	@Nullable public Post post(int id, @Nullable Integer loginUser){
		return postDAO.findPost(id, loginUser);
	}

	/**
	 * 포스트 작성<br>
	 * <br>
	 * <b>에러: </b><br>
	 * bad_text   : 유효하지 않은 text<br>
	 * bad_image  : 유효하지 않은 image<br>
	 * unexpected : 포스트 생성 불가<br>
	 */
	public PostWriteResult writePost(int loginUser, String text, MultipartFile image){
		text = text.trim();
		if(!Validate.postText(text)) return new PostWriteResult("bad_text");

		if(image.isEmpty()) return new PostWriteResult("bad_image");

		int postId = postDAO.generatePostId();

		String imageId = fileService.savePostImage(postId, image);
		if(imageId==null)
			return new PostWriteResult("unexpected");

		if(!postDAO.writePost(postId, loginUser, text, imageId)){
			fileService.removePostImage(imageId);
			return new PostWriteResult("unexpected");
		}
		return new PostWriteResult(postId);
	}

	/**
	 * 포스트 수정<br>
	 * <br>
	 * <b>에러: </b><br>
	 * bad_text    : 유효하지 않은 text<br>
	 * no_post     : 해당 ID의 포스트가 존재하지 않음<br>
	 * cannot_edit : 해당 글을 수정할 수 없음<br>
	 */
	public PostModifyResult editPost(int loginUser, int post, String text){
		text = text.trim();
		if(!Validate.postText(text)) return new PostModifyResult("bad_text");

		Post postData = postDAO.findPost(post, null);

		if(postData==null)
			return new PostModifyResult("no_post");
		if(loginUser!=postData.getAuthor().getId())
			return new PostModifyResult("cannot_edit");

		postDAO.editPost(post, text);
		return new PostModifyResult();
	}

	/**
	 * 포스트 삭제<br>
	 * <br>
	 * <b>에러: </b><br>
	 * no_post       : 해당 ID의 포스트가 존재하지 않음<br>
	 * cannot_delete : 해당 글을 삭제할 수 없음<br>
	 */
	public PostModifyResult deletePost(int loginUser, int post){
		Post postData = postDAO.findPost(post, null);

		if(postData==null)
			return new PostModifyResult("no_post");
		if(loginUser!=postData.getAuthor().getId())
			return new PostModifyResult("cannot_delete");

		postDAO.deletePost(post);
		if(postData.getImage()!=null) fileService.removePostImage(postData.getImage());
		return new PostModifyResult();
	}

	public PostModifyResult likePost(int loginUser, int post, boolean like){
		Post postData = postDAO.findPost(post, null);

		if(postData==null)
			return new PostModifyResult("no_post");

		if(like) postDAO.like(loginUser, post);
		else postDAO.removeLike(loginUser, post);
		return new PostModifyResult();
	}

	public static final class PostListResult{
		@Nullable private final List<Post> posts;
		@Nullable private final String error;
		
		public PostListResult(List<Post> posts) {
			this.posts = posts;
			this.error = null;
		}

		public PostListResult(String error){
			this.posts = null;
			this.error = error;
		}

		public List<Post> getPosts(){
			return posts;
		}
		public String getError(){
			return error;
		}
	}

	public static class PostWriteResult{
		@Nullable private final Integer postId;
		@Nullable private final String error;

		public PostWriteResult(Integer postId){
			this.postId = postId;
			this.error = null;
		}
		public PostWriteResult(String error){
			this.postId = null;
			this.error = error;
		}

		@Nullable public Integer getPostId(){
			return postId;
		}
		@Nullable public String getError(){
			return error;
		}
	}

	public static class PostModifyResult{
		@Nullable private final String error;

		public PostModifyResult(){
			this.error = null;
		}
		public PostModifyResult(String error){
			this.error = error;
		}

		@Nullable public String getError(){
			return error;
		}
	}
}
