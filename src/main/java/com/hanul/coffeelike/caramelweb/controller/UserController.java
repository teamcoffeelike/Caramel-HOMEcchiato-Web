package com.hanul.coffeelike.caramelweb.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.data.UserProfileData;
import com.hanul.coffeelike.caramelweb.service.UserService;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;

@Controller
public class UserController {
	@Autowired
	private UserService service;
	// 팔로우/팔로잉 리스트
	@RequestMapping("/follows")
	public String follows(HttpSession session, Model model) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if (loginUser == null ) {
			return "loginRequired";
		}
		List<UserProfileData> users = service.getFollower(loginUser.getUserId());
		model.addAttribute("followers", users);
		return "mypage/follows";
	}
	
	//프로필
	@RequestMapping("/profile")
	public String myprofile(HttpSession session, 
			Model model){
		//AuthToken loginUser = SessionAttributes.getLoginUser(session);
		//if(loginUser == null) return "redirect:/"; // TODO
		//int loginId = loginUser.getUserId();
		//model.addAttribute("data", userService.profile(loginId, userId));
		return "mypage/profile";
	}
	
	
	@RequestMapping("/mymodify")
	public String modify() {
		return "mypage/modifyProfile";
	}
	
	//프로필 설정 > 프로필사진, 닉네임, 상태메시지
	@RequestMapping("/setProfile")
	public String setProfile() {
		return "";
	}
	
	//비밀번호 변경
	@RequestMapping("/setPassword")
	public String setPassword() {
		return "";
	}
	
}
