package com.hanul.coffeelike.caramelweb.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanul.coffeelike.caramelweb.dao.LoginDAO;
import com.hanul.coffeelike.caramelweb.data.UserLoginData;

@Service
public class LoginService {
	@Autowired
	private LoginDAO dao;

	//이메일로 로그인 요청
	public UserLoginData login_email(HashMap<String, String> map) {
		return dao.findUserWithEmail(map);
	}
	
	//핸드폰번호로 로그인 요청
	public UserLoginData login_phone(HashMap<String, String> map) {
		return dao.findUserWithPhone(map);
	}
}
