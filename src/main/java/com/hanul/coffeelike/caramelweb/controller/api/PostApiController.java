package com.hanul.coffeelike.caramelweb.controller.api;

import java.io.Serial;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.data.Post;
import com.hanul.coffeelike.caramelweb.service.PostService;
import com.hanul.coffeelike.caramelweb.service.PostService.PostResult;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;

@Controller
public class PostApiController {
			
	@Autowired private PostService postService;
	
	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String onException(MissingServletRequestParameterException ex) {
		return JsonHelper.failure("bad_parameter");
	}
	
	/**
	 * post
	 * -> id : <Integer>
     * 
	 * # 성공시
	 * {
	 * 	id :                     # 포스트 ID
	 * 	userId : <Integer>       # 포스트 작성자 ID
	 * 	images : [ <URL> ]       # 첨부된 이미지 URL
	 * 	text : <String>          # 포스트 내용
	 * 	likes : <Integer>        # 숫자
	 * 	reactions : <Integer>    # 댓글 수??
	 * 	[likedByYou : <Boolean>] # 로그인한 유저가 이 포스트에 좋아요를 눌렀는지 여부, 로그인 정보가 없을 시 존재하지 않음
	 * }
	 */	
	//포스트 상세
	@ResponseBody
	@RequestMapping("/post")
	public String post(
			HttpSession session,
			@RequestParam int id
	) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		Post post = postService.post(id, loginUser==null ? null : loginUser.getUserId());

		return JsonHelper.GSON.toJson(post);
	}
	
	/**
	 * writePost
	 * -> text : <String>                    # 글 내용
	 * -> image0, image1, image2... : <File> # 첨부사진
	 *	
	 * # 성공 시
	 * {
	 * 	id : <Integer> # 작성된 포스트 ID
	 * }
     * 
	 * # 에러
	 * not_logged_in : 로그인 상태가 아님
	 * bad_text  : 유효하지 않은 text 인자
	 * bad_image : 유효하지 않은 imageN 인자
	 * no_image : 존재하지 않는 imageN 인자
	 */
	//포스트 작성
	@ResponseBody
	@RequestMapping("/writePost")
	public String writePost(
			HttpSession session,
			@RequestParam String text
			//@RequestParam File image
	) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if (loginUser == null) return JsonHelper.failure("not_logged_in");
		if (text.isEmpty()) return JsonHelper.failure("bad_text");
		//if () return JsonHelper.failure("bad_image");
		//if (!image.exists()) return JsonHelper.failure("no_image");
		
		int postId = postService.writePost(loginUser.getUserId(), text);
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("postId", postId);
		
		return JsonHelper.GSON.toJson(jsonObject);
	}
	
	/**
	 * editPost
	 * -> post : <Integer>  # 기존 포스트 ID
	 * -> text : <String> # 내용
     * 
	 * # 성공 시
	 * {}
     * 
	 * # 에러
	 * no_post       : 해당 ID의 포스트가 존재하지 않음
	 * cannot_edit   : 해당 글을 수정할 수 없음 (비 로그인 상태 포함)
	 * bad_text      : 유효하지 않은 text 인자
	 */
	//포스트 수정
	@ResponseBody
	@RequestMapping("/editPost")
	public String editPost(
			HttpSession session,
			@RequestParam int post,
			@RequestParam String text
	) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if (loginUser == null) return JsonHelper.failure("cannot_edit");
		if (text.isBlank()) return JsonHelper.failure("bad_text");
		
		PostResult result = postService.editPost(loginUser.getUserId(), post, text); 
		
		return JsonHelper.GSON.toJson(result);
	}
	
	/**
	 * deletePost
	 * -> post : <Integer>
     * 
	 * # 성공 시
	 * {}
     * 
	 * # 에러
	 * no_post : 해당 ID의 포스트가 존재하지 않음
	 * cannot_delete : 해당 글을 삭제할 수 없음 (비 로그인 상태 포함)
	 */
	//포스트 삭제
	@ResponseBody
	@RequestMapping("/deletePost")
	public String deletePost(
			HttpSession session,
			@RequestParam int post
	) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if (loginUser == null) return JsonHelper.failure("cannot_edit");
		
		PostResult result = postService.deletePost(loginUser.getUserId(), post);
		
		return JsonHelper.GSON.toJson(result);
	}
	
	/**
	 * likePost
	 * -> post : <Integer> # 포스트 ID
	 * -> like : <Boolean> # 변경할 좋아요 여부
     * 
	 * # 에러
	 * no_post : 해당 ID의 포스트가 존재하지 않음
	 * not_logged_in : 로그인 상태가 아님
	 */
	//포스트 좋아요
	@ResponseBody
	@RequestMapping("/likePost")
	public String likePost(
			HttpSession session,
			@RequestParam int post,
			@RequestParam boolean like
		) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if (loginUser == null) return JsonHelper.failure("not_logged_in");
		PostResult result = postService.likePost(loginUser.getUserId(), post, like);
		
		return JsonHelper.GSON.toJson(result);
	}

}
