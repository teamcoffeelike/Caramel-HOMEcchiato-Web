package com.hanul.coffeelike.caramelweb.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanul.coffeelike.caramelweb.data.JoinData;

@Repository
public class JoinDAO {
	@Autowired private SqlSession sql;

	//이메일 회원가입 처리
	public int join_email(HashMap<String, String> map) {
		return sql.insert("join.joinWithEmail", map);
	}

	//핸드폰번호
	public int join_phone(HashMap<String, String> map) {
		return sql.insert("join.joinWithPhone", map);
	}
}