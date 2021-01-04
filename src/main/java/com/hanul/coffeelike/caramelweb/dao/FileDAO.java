package com.hanul.coffeelike.caramelweb.dao;

import com.hanul.coffeelike.caramelweb.data.ProfileImageData;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class FileDAO{
	@Autowired
	private SqlSession sql;

	@Nullable public ProfileImageData findProfileImage(int user){
		return sql.selectOne("file.findProfileImage", user);
	}

	public boolean setUserProfileImage(int user, @Nullable String profileImage){
		Map<String, Object> m = new HashMap<>();
		m.put("user", user);
		m.put("profileImage", profileImage);
		return sql.update("file.setUserProfileImage", m)>0;
	}
}
