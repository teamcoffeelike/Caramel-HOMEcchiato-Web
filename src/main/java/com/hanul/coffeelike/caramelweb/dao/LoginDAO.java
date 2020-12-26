package com.hanul.coffeelike.caramelweb.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanul.coffeelike.caramelweb.data.UserLoginData;

@Repository
public class LoginDAO {
	@Autowired
	private SqlSession sql;

	//이메일로 로그인 요청
	public UserLoginData findUserWithEmail(HashMap<String, String> map) {
		return sql.selectOne("login.findUserWithEmail", map);
	}

	//핸드폰번호로 로그인 요청
	public UserLoginData findUserWithPhone(HashMap<String, String> map) {
		return sql.selectOne("login.findWithPhoneNumber", map);
	}
}
