package com.hanul.coffeelike.caramelweb.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.File;

@Controller
public class PostApiController{
	private final Gson GSON = new GsonBuilder().create();

	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String onException(MissingServletRequestParameterException ex) {
		return JsonHelper.failure("bad_parameter");
	}
	
	//인기포스트
	@ResponseBody
	@RequestMapping("/api/topPosts")
	public String topPosts(
			HttpSession session
	) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);
		
		return GSON.toJson(o);
	}
	
	//포스트 상세
	@ResponseBody
	@RequestMapping("/api/post")
	public String post(
			HttpSession session,
			@RequestParam int id
	) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);
		
		return GSON.toJson(o);
	}
	
	//포스트 작성
	@ResponseBody
	@RequestMapping("/api/writePost")
	public String writePost(
			HttpSession session,
			@RequestParam String text,
			@RequestParam File image
	) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);
		
		return GSON.toJson(o);
	}
	
	//포스트 수정
	@ResponseBody
	@RequestMapping("/api/editPost")
	public String editPost(
			HttpSession session,
			@RequestParam int post,
			@RequestParam String text
	) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);
		
		return GSON.toJson(o);
	}
	
	//포스트 삭제
	@ResponseBody
	@RequestMapping("/api/deletePost")
	public String deletePost(
			HttpSession session,
			@RequestParam int post
	) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);
		
		return GSON.toJson(o);
	}
	
	//포스트에 댓글
	@ResponseBody
	@RequestMapping("/api/writeReaction")
	public String writeReaction(
			HttpSession session,
			@RequestParam int post,
			@RequestParam String text
	) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);
		
		return GSON.toJson(o);
	}
	
	//댓글 수정
	@ResponseBody
	@RequestMapping("/api/editReaction")
	public String editReaction(
			HttpSession session,
			@RequestParam int reaction,
			@RequestParam String text
	) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);
		
		return GSON.toJson(o);
	}
	
	//댓글 삭제
	@ResponseBody
	@RequestMapping("/api/removeReaction")
	public String removeReaction(
			HttpSession session,
			@RequestParam int reaction
		) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);
		
		return GSON.toJson(o);
	}
	
	//포스트 좋아요
	@ResponseBody
	@RequestMapping("/api/likePost")
	public String likePost(
			HttpSession session,
			@RequestParam int post,
			@RequestParam boolean like
		) {
		JsonObject o = new JsonObject();
		o.addProperty("success", "true");
		o.addProperty("userId", 1231231323);
		
		return GSON.toJson(o);
	}
	
}
