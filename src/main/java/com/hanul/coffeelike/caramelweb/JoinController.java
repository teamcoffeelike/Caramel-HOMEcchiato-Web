package com.hanul.coffeelike.caramelweb;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanul.coffeelike.caramelweb.data.JoinData;
import com.hanul.coffeelike.caramelweb.data.UserLoginData;
import com.hanul.coffeelike.caramelweb.service.JoinService;

@Controller
public class JoinController {
	@Autowired private JoinService service;
	
	
	//이메일 회원가입 처리
	@ResponseBody @RequestMapping(value="/joinWithEmail", produces="text/html; charset=utf-8")
	public String joinWithEmail(HttpServletRequest request, HttpSession session,
								String name, String email, String password) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("email", email);
		map.put("password", password);
		
		System.out.println(name);
		System.out.println(email);
		System.out.println(password);
		
		String msg = "<script>";
		if(service.join_email(map) == 1) {
			msg += "alert('회원가입을 축하합니다!'); location='" + request.getContextPath() + "'";
		}else {
			msg += "alert('회원가입 실패!');";
		}
		msg += "</script>";
		return msg;
	}
	
	//핸드폰번호 회원가입 처리
	@ResponseBody @RequestMapping(value="/joinWithPhone", produces="text/html; charset=utf-8")
	public String joinWithPhone(HttpServletRequest request, HttpSession session,
								String name, String phone, String password) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("phone", phone);
		map.put("password", password);
		
		System.out.println(name);
		System.out.println(phone);
		System.out.println(password);
		
		String msg = "<script>";
		if(service.join_phone(map) == 1) {
			msg += "alert('회원가입을 축하합니다!'); location='" + request.getContextPath() + "'";
		}else {
			msg += "alert('회원가입 실패!');";
		}
		msg += "</script>";
		return msg;
	}
}
