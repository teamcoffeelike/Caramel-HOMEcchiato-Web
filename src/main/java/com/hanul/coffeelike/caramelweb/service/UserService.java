package com.hanul.coffeelike.caramelweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanul.coffeelike.caramelweb.dao.UserDAO;
import com.hanul.coffeelike.caramelweb.data.UserProfileData;
import com.hanul.coffeelike.caramelweb.data.UserSettingData;

@Service
public class UserService{
	@Autowired
	private UserDAO dao;
	@Autowired
	private FileService fileService;

	public UserSettingData userSettings(int loginUser){
		return dao.userSettings(loginUser);
	}

	public UserProfileData profile(@Nullable Integer loginUser, int userId){
		return dao.profile(loginUser, userId);
	}

	public void setName(int userId, String name){
		dao.setName(userId, name);
	}
	
	public void setMotd(int userId, String motd) {
		dao.setMotd(userId, motd);
	}

	public SettingResult setProfileImage(int userId, MultipartFile profileImage) {
		
		if(profileImage.isEmpty()) {
			return new SettingResult("bad_image");
		}
		if(!fileService.setProfileImage(userId, profileImage)) {
			return new SettingResult("unexpected");
		}
		
		return new SettingResult(null);
	}
	
	public SettingResult setPassword(int userId, String password, String newPassword){
		String initPw = dao.getUserPasswordById(userId);
		if(initPw==null){
			return new SettingResult("no_password");
		}
		if(!initPw.equals(password)){
			return new SettingResult("incorrect_password");
		}

		dao.setPassword(userId, newPassword);

		return new SettingResult(null);
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
	
	public static class SettingResult{
		@Nullable private String error;
		
		public SettingResult(){
			this.error = null;
		}

		public SettingResult(@Nullable String error){
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
