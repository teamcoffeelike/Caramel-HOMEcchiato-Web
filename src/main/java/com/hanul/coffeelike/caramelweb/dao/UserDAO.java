package com.hanul.coffeelike.caramelweb.dao;

import com.hanul.coffeelike.caramelweb.data.UserProfileData;
import com.hanul.coffeelike.caramelweb.data.UserSettingData;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDAO{

	@Autowired private SqlSession sql;

	public UserSettingData userSettings(int loginUser){
		return sql.selectOne("user.userSettings", loginUser);
	}

	public UserProfileData profile(Integer loginUser, int userId){
		Map<String, Object> m = new HashMap<>();
		m.put("loginUser", loginUser);
		m.put("userId", userId);
		return sql.selectOne("user.selectProfile", m);
	}

	public void setName(int userId, String name){
		Map<String, Object> m = new HashMap<>();
		m.put("userId", userId);
		m.put("name", name);
		sql.update("user.setName", m);
	}
	
	public void setMotd(int userId, @Nullable String motd) {
		Map<String, Object> m = new HashMap<>();
		m.put("userId", userId);
		m.put("motd", motd);
		sql.update("user.setMotd", m);
	}
	
	public String getUserPasswordById(int userId){
		return sql.selectOne("user.getUserPasswordById", userId);
	}

	public void setPassword(int userId, String newPassword){
		Map<String, Object> m = new HashMap<>();
		m.put("userId", userId);
		m.put("newPassword", newPassword);
		sql.update("user.setPassword", m);
	}

	public List<UserProfileData> getFollower(int user){
		return sql.selectList("user.getFollower", user);
	}
	public List<UserProfileData> getFollowing(int user){
		return sql.selectList("user.getFollowing", user);
	}

	public int follow(int loginUser, int followingId){
		Map<String, Object> m = new HashMap<>();
		m.put("loginUser", loginUser);
		m.put("followingId", followingId);
		return sql.insert("user.follow", m);
	}

	public int unfollow(int loginUser, int followingId){
		Map<String, Object> m = new HashMap<>();
		m.put("loginUser", loginUser);
		m.put("followingId", followingId);
		return sql.delete("user.unfollow", m);
	}

	public boolean checkIfUserExists(int author){
		return sql.selectOne("user.checkIfUserExists", author)!=null;
	}

	public boolean nameExists(String name) {
		return sql.<Integer>selectOne("user.nameExists", name) > 0;
	}
}
