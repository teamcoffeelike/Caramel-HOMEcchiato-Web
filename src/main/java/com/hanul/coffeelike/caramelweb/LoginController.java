package com.hanul.coffeelike.caramelweb;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanul.coffeelike.caramelweb.data.UserLoginData;
import com.hanul.coffeelike.caramelweb.service.LoginService;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	//로그인 화면 요청 -> 메인화면
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session)	{
		return "user/login";
	}
	
	//이메일로 로그인 요청
	@ResponseBody @RequestMapping("/loginWithEmail")
	public boolean loginWithEmail(String email, String password, HttpSession session) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("password", password);
		UserLoginData loginData = loginService.login_email(map);
		session.setAttribute("login_info", loginData);
		
		return loginData == null ? false : true;
	}
	
	//핸드폰번호로 로그인 요청
	@ResponseBody @RequestMapping("/loginWithPhone")
	public boolean loginWithPhone(String phoneNumber, String password, HttpSession session) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("phoneNumber", phoneNumber);
		map.put("password", password);
		UserLoginData loginData = loginService.login_phone(map);
		session.setAttribute("login_info", loginData);
		
		return loginData == null ? false : true;
	}
}
