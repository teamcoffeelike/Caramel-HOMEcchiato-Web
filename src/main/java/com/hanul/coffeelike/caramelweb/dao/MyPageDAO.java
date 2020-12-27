package com.hanul.coffeelike.caramelweb.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanul.coffeelike.caramelweb.data.ProfileData;

@Repository
public class MyPageDAO {

	@Autowired private SqlSession sql; 
	
	public ProfileData myprofile(int userId) {
		return sql.selectOne("mypage.myprofile", userId);
	}
}
