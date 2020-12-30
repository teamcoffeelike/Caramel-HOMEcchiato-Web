package com.hanul.coffeelike.caramelweb.controller;

import com.hanul.coffeelike.caramelweb.data.LoginResult;
import com.hanul.coffeelike.caramelweb.service.JoinService;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;
import com.hanul.coffeelike.caramelweb.util.Validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class JoinController{
	private final Logger logger = LoggerFactory.getLogger(JoinController.class);

	@Autowired
	private JoinService joinService;

	//이메일 회원가입 처리
	@ResponseBody
	@RequestMapping("/joinWithEmail")
	public String joinWithEmail(
			HttpServletRequest request,
			HttpSession session,
			@RequestParam String name,
			@RequestParam String email,
			@RequestParam String password){
		LoginResult result = joinService.joinWithEmail(name, email, password);
		if(result.getError()==null){
			SessionAttributes.setLoginUser(session, result.createAuthToken());
		}

		StringBuilder stb = new StringBuilder();
		stb.append("<script>");
		if(result.getError()==null){
			stb.append("location='").append(request.getContextPath()).append("'");
			logger.debug("contextPath = "+request.getContextPath());
		}else {
			logger.error("error = "+result.getError());
		}

		return stb.append("</script>").toString();
	}

	//핸드폰번호 회원가입 처리
	@ResponseBody
	@RequestMapping("/joinWithPhoneNumber")
	public String joinWithPhoneNumber(
			HttpServletRequest request,
			HttpSession session,
			@RequestParam String name,
			@RequestParam String phoneNumber,
			@RequestParam String password){
		LoginResult result = joinService.joinWithPhoneNumber(name, phoneNumber, password);
		if(result.getError()==null){
			SessionAttributes.setLoginUser(session, result.createAuthToken());
		}

		StringBuilder stb = new StringBuilder();
		stb.append("<script>");
		if(result.getError()==null){
			stb.append("location='").append(request.getContextPath()).append("'");
		}else {
			logger.error("error = "+result.getError());
		}

		return stb.append("</script>").toString();
	}
}
