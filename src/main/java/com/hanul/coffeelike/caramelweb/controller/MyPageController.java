package com.hanul.coffeelike.caramelweb.controller;

import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.service.UserService;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MyPageController{
	@Autowired
	private UserService userService;

	@RequestMapping("/profile.my")
	public String myprofile(HttpSession session, Model model){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) ; // TODO
		int userId = loginUser.getUserId();
		model.addAttribute("data", userService.profile(userId, userId));
		return "mypage/profile";
	}

}
