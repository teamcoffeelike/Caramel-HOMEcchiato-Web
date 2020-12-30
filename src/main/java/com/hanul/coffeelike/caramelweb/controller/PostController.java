package com.hanul.coffeelike.caramelweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanul.coffeelike.caramelweb.service.PostService;

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
	public String writePost() {
		return "redirect:profile";
	}
	
}
