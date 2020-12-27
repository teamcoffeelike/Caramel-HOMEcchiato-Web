package com.hanul.coffeelike.caramelweb;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanul.coffeelike.caramelweb.data.UserLoginData;
import com.hanul.coffeelike.caramelweb.service.MyPageService;

@Controller
public class MyPageController {

	@Autowired private MyPageService service;
	
	@RequestMapping("/profile.my")
	public String myprofile(HttpSession session, Model model) {
		UserLoginData loginData = (UserLoginData) session.getAttribute("login_info");
		int userId = loginData.getId();
		model.addAttribute("data",service.myprofile(userId));
		return "mypage/profile";
	}
	
}
