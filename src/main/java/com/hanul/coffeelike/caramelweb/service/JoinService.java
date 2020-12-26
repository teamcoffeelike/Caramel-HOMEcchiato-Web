package com.hanul.coffeelike.caramelweb.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanul.coffeelike.caramelweb.dao.JoinDAO;
import com.hanul.coffeelike.caramelweb.data.JoinData;

@Service
public class JoinService {
	@Autowired private JoinDAO dao;
	
	//이메일 
	public int join_email(HashMap<String, String> map) {
		return dao.join_email(map);
	}

	//핸드폰번호
	public int join_phone(HashMap<String, String> map) {
		return dao.join_phone(map);
	}
	
}