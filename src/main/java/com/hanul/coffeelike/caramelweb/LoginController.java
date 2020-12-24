package com.hanul.coffeelike.caramelweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hanul.coffeelike.caramelweb.service.LoginService;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
}
