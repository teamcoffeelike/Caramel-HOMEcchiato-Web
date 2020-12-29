package com.hanul.coffeelike.caramelweb.controller.api;

import com.google.gson.JsonObject;
import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.data.Post;
import com.hanul.coffeelike.caramelweb.service.PostService;
import com.hanul.coffeelike.caramelweb.service.PostService.PostResult;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;
import com.hanul.coffeelike.caramelweb.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RestController
public class PostApiController extends BaseExceptionHandlingController{
	@Autowired
	private PostService postService;

	/**
	 * 가장 최근 포스트 가져오기<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * {
	 *   posts: [ 20;
	 *     {
	 *       id: Integer # 포스트 ID
	 * 	     author: { # 포스트 작성자
	 *         id: Integer
	 *         name: String
	 *         [ profileImage ]: URL
	 *         [ isFollowingYou ]: Boolean
	 *         [ isFollowedByYou ]: Boolean
	 *       }
	 * 	     [ image ]: URL # 첨부된 이미지 URL
	 * 	     text: String # 포스트 내용
	 * 	     postDate: Date
	 * 	     [ lastEditDate ]: Date
	 * 	     likes: Integer # 숫자
	 * 	     reactions: Integer # 댓글 수??
	 * 	     [ likedByYou ]: Boolean # 로그인한 유저가 이 포스트에 좋아요를 눌렀는지 여부, 로그인 정보가 없을 시 존재하지 않음
	 *     }
	 *   ]
	 * }
	 * }</pre>
	 */
	@RequestMapping("/api/recentPosts")
	public String recentPosts(HttpSession session){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		return JsonHelper.GSON.toJson(postService.recentPosts(loginUser==null ? null : loginUser.getUserId()));
	}

	/**
	 * 포스트 가져오기<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * {
	 *   id: Integer # 포스트 ID
	 * 	 author: { # 포스트 작성자
	 *     id: Integer
	 *     name: String
	 *     [ profileImage ]: URL
	 *     [ isFollowingYou ]: Boolean
	 *     [ isFollowedByYou ]: Boolean
	 *   }
	 * 	 [ image ]: URL # 첨부된 이미지 URL
	 * 	 text: String # 포스트 내용
	 * 	 postDate: Date
	 * 	 [ lastEditDate ]: Date
	 * 	 likes: Integer # 숫자
	 * 	 reactions: Integer # 댓글 수??
	 * 	 [ likedByYou ]: Boolean # 로그인한 유저가 이 포스트에 좋아요를 눌렀는지 여부, 로그인 정보가 없을 시 존재하지 않음
	 * }
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * no_post : 포스트가 존재하지 않음<br>
	 */
	@RequestMapping("/api/post")
	public String post(HttpSession session,
	                   @RequestParam int id){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		Post post = postService.post(id, loginUser==null ? null : loginUser.getUserId());
		if(post==null) return JsonHelper.failure("no_post");

		return JsonHelper.GSON.toJson(post);
	}

	/**
	 * 포스트 작성<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * {
	 *   id: Integer # 작성된 포스트 ID
	 * }
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * not_logged_in : 로그인 상태가 아님<br>
	 * bad_text  : 유효하지 않은 text 인자<br>
	 */
	@RequestMapping("/api/writePost")
	public String writePost(HttpSession session,
	                        @RequestParam String text,
	                        @RequestParam MultipartFile image){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		text = text.trim();
		if(Validate.postText(text)) return JsonHelper.failure("bad_text");

		if(image.isEmpty()){
			return JsonHelper.failure("bad_image");
		}

		Integer postId = postService.writePost(loginUser.getUserId(), text, image);
		if(postId==null){ // 오류로 인해 포스트 생성 불가
			return JsonHelper.failure("unexpected");
		}

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("postId", postId);

		return JsonHelper.GSON.toJson(jsonObject);
	}

	/**
	 * 포스트 수정<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * 추가 데이터 없음
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * no_post       : 해당 ID의 포스트가 존재하지 않음<br>
	 * cannot_edit   : 해당 글을 수정할 수 없음 (비 로그인 상태 포함)<br>
	 * bad_text      : 유효하지 않은 text 인자<br>
	 */
	@RequestMapping("/api/editPost")
	public String editPost(HttpSession session,
	                       @RequestParam int post,
	                       @RequestParam String text){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("cannot_edit");

		text = text.trim();
		if(Validate.postText(text)) return JsonHelper.failure("bad_text");

		PostResult result = postService.editPost(loginUser.getUserId(), post, text);

		return JsonHelper.GSON.toJson(result);
	}

	/**
	 * 포스트 수정<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * 추가 데이터 없음
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * no_post : 해당 ID의 포스트가 존재하지 않음<br>
	 * cannot_delete : 해당 글을 삭제할 수 없음 (비 로그인 상태 포함)<br>
	 */
	@RequestMapping("/api/deletePost")
	public String deletePost(HttpSession session,
	                         @RequestParam int post){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("cannot_edit");

		PostResult result = postService.deletePost(loginUser.getUserId(), post);

		return JsonHelper.GSON.toJson(result);
	}

	/**
	 * 포스트 좋아요 추가/삭제<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * 추가 데이터 없음
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * no_post : 해당 ID의 포스트가 존재하지 않음<br>
	 * not_logged_in : 로그인 상태가 아님<br>
	 */
	@RequestMapping("/api/likePost")
	public String likePost(HttpSession session,
	                       @RequestParam int post,
	                       @RequestParam boolean like){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		PostResult result = postService.likePost(loginUser.getUserId(), post, like);

		return JsonHelper.GSON.toJson(result);
	}
}
