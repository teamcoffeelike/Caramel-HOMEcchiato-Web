package com.hanul.coffeelike.caramelweb.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.service.PostService;
import com.hanul.coffeelike.caramelweb.service.PostService.PostWriteResult;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;

@Controller
public class PostController {

	@Autowired PostService postService;
	
	//포스트 신규화면 요청
	@RequestMapping("/writePostView")
	public String writePostView() {
		return "post/new";
	}
	
	//포스트 신규저장처리 요청
	@RequestMapping("/writePost")
	public String writePost(HttpSession session,
							@RequestParam String text,
							@RequestParam MultipartFile image) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser == null) return "loginRequired";
		
		PostWriteResult result = postService.writePost(loginUser.getUserId(), text, image);
		
		return "redirect:profile?userId=" + loginUser.getUserId();
	}
	
	//포스트 상세화면 요청
	@RequestMapping("/post")
	public String post() {
		return "";
	}
}
