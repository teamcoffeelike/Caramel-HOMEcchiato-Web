package com.hanul.coffeelike.caramelweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanul.coffeelike.caramelweb.dao.LoginDAO;

@Service
public class LoginService {
	@Autowired
	private LoginDAO dao;
}
