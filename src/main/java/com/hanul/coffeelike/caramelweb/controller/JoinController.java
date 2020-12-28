package com.hanul.coffeelike.caramelweb.controller;

import com.hanul.coffeelike.caramelweb.service.JoinService;
import com.hanul.coffeelike.caramelweb.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class JoinController{
	@Autowired
	private JoinService service;

	//이메일 회원가입 처리
	@ResponseBody @RequestMapping(value = "/joinWithEmail", produces = "text/html; charset=utf-8")
	public String joinWithEmail(HttpServletRequest request,
	                            @RequestParam String name,
	                            @RequestParam String email,
	                            @RequestParam String password){
		StringBuilder stb = new StringBuilder();
		stb.append("<script>");
		if(Validate.name(name)&&
				Validate.email(email)&&
				Validate.password(password)&&
				service.joinWithEmail(name, email, password).getError()==null){
			stb.append("alert('회원가입을 축하합니다!'); location='").append(request.getContextPath()).append("'");
		}else{
			stb.append("alert('회원가입 실패!');");
		}
		return stb.append("</script>").toString();
	}

	//핸드폰번호 회원가입 처리
	@ResponseBody @RequestMapping(value = "/joinWithPhone", produces = "text/html; charset=utf-8")
	public String joinWithPhone(HttpServletRequest request, HttpSession session,
	                            String name, String phone, String password){
		StringBuilder stb = new StringBuilder();
		stb.append("<script>");
		if(Validate.name(name)&&
				Validate.phoneNumber(phone)&&
				Validate.password(password)&&service.joinWithPhoneNumber(name, phone, password).getError()==null){
			stb.append("alert('회원가입을 축하합니다!'); location='").append(request.getContextPath()).append("'");
		}else{
			stb.append("alert('회원가입 실패!');");
		}
		return stb.append("</script>").toString();
	}
}
