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
		return "redirect:login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpSession session){
		return "user/login";
	}
	
	//소개 화면 요청
	@RequestMapping("/list.in")
	public String Introduce(HttpSession session) {
		return "introduce/context";
	}
}
