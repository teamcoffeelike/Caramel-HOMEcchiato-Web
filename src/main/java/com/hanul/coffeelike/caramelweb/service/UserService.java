package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.UserDAO;
import com.hanul.coffeelike.caramelweb.data.UserProfileData;
import com.hanul.coffeelike.caramelweb.data.UserSettingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{
	@Autowired
	private UserDAO dao;

	public UserSettingData userSettings(int loginUser){
		return dao.userSettings(loginUser);
	}

	public UserProfileData profile(@Nullable Integer loginUser, int userId){
		return dao.profile(loginUser, userId);
	}

	public void setName(int id, String name){
		dao.setName(id, name);
	}

	public SetPasswordResult setPassword(int userId, String password, String newPassword){
		String initPw = dao.getUserPasswordById(userId);
		if(initPw==null){
			return new SetPasswordResult("no_password");
		}
		if(!initPw.equals(password)){
			return new SetPasswordResult("incorrect_password");
		}

		dao.setPassword(userId, newPassword);

		return new SetPasswordResult(null);
	}

	public List<UserProfileData> getFollower(int user){
		return dao.getFollower(user);
	}
	public List<UserProfileData> getFollowing(int user){
		return dao.getFollowing(user);
	}

	public boolean setFollowing(int loginUser, int followingId, boolean following){
		int result = following ? dao.follow(loginUser, followingId) : dao.unfollow(loginUser, followingId);
		return result!=0;
	}

	public boolean checkIfUserExists(int author){
		return dao.checkIfUserExists(author);
	}


	public static class SetPasswordResult{
		@Nullable private String error;

		public SetPasswordResult(@Nullable String error){
			this.error = error;
		}

		public String getError(){
			return error;
		}

		public void setError(String error){
			this.error = error;
		}
	}
}
