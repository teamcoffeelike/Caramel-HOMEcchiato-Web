package com.hanul.coffeelike.caramelweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController{
	//로그인 화면 요청 -> 메인화면
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session){
		return "user/login";
	}
}
