package com.hanul.coffeelike.caramelweb.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	//전체 포스트 리스트 화면 요청
	@RequestMapping("/allPostList")
	public String allPostList(HttpSession session) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser == null) return "loginRequired";
		return "post/list";
	}
	
	//포스트 신규화면 요청
	@RequestMapping("/writePostView")
	public String writePostView() {
		return "post/new";
	}
	
	//포스트 신규저장처리 요청
	@RequestMapping("/writePost")
	public String writePost(HttpSession session,
							Model model,
							@RequestParam String text,
							@RequestParam MultipartFile image) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser == null) return "loginRequired";
		
		PostWriteResult result = postService.writePost(loginUser.getUserId(), text, image);
		if(result.getError() != null) {
			String postError = result.getError().toString();
			switch (postError) {
			case "bad_text":
				model.addAttribute("msg", "텍스트 양식이 올바르지 않습니다!");
				break;
			case "bad_image":
				model.addAttribute("msg", "이미지 파일이 올바르지 않습니다!");
				break;
			default:
				model.addAttribute("msg", "포스트 등록 실패!");
				break;
			}
			return "post/postRequired";
		}
		
		return "redirect:profile?userId=" + loginUser.getUserId();
	}
	
	//포스트 상세화면 요청
	@RequestMapping("/post")
	public String post() {
		return "";
	}
}
