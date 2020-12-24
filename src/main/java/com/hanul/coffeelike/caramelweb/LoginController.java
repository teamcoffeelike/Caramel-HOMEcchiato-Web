package com.hanul.coffeelike.caramelweb;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hanul.coffeelike.caramelweb.service.LoginService;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session)	{
		return "user/login";
	}
}
