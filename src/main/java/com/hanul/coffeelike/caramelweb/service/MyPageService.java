package com.hanul.coffeelike.caramelweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanul.coffeelike.caramelweb.dao.MyPageDAO;
import com.hanul.coffeelike.caramelweb.data.ProfileData;

@Service
public class MyPageService {
	
	@Autowired private MyPageDAO dao;
	
	public ProfileData myprofile(int userId) {
		return dao.myprofile(userId);
	}
	
}
