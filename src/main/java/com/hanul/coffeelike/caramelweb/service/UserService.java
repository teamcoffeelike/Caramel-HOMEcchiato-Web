package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.UserDAO;
import com.hanul.coffeelike.caramelweb.data.UserProfileData;
import com.hanul.coffeelike.caramelweb.data.UserSettingData;
import com.hanul.coffeelike.caramelweb.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

	/**
	 * 이름 설정<br>
	 * <br>
	 * <b>에러: </b><br>
	 * bad_name : 유효하지 않은 이름<br>
	 */
	public SettingResult setName(int userId, String name){
		name = name.trim();
		if(!Validate.name(name)) return new SettingResult("bad_name");

		dao.setName(userId, name);

		return new SettingResult();
	}

	/**
	 * 소개글 설정<br>
	 * <br>
	 * <b>에러: </b><br>
	 * bad_motd : 유효하지 않은 소개글<br>
	 */
	public SettingResult setMotd(int userId, String motd){
		motd = motd.trim();
		if(!Validate.motd(motd)) return new SettingResult("bad_motd");

		dao.setMotd(userId, motd);

		return new SettingResult();
	}
	
	/*프로필사진 설정*/
	public void removeProfileImage(int userId) {
		fileService.removeProfileImageFromUser(userId);
	}
	
	public SettingResult setProfileImage(int userId, MultipartFile profileImage) {
		if(profileImage.isEmpty()){
			return new SettingResult("bad_image");
		}
		if(!fileService.setProfileImage(userId, profileImage)){
			return new SettingResult("unexpected");
		}

		return new SettingResult();
	}

	/*비밀번호 설정*/
	public SettingResult setPassword(int userId, String originalPw, String newPassword){
		if(!Validate.password(newPassword)) return new SettingResult("bad_new_password");
		if(originalPw.equals(newPassword)) return new SettingResult("bad_new_password");

		String initPw = dao.getUserPasswordById(userId);
		if(initPw==null) return new SettingResult("no_password");
		if(!initPw.equals(originalPw))
			return new SettingResult("incorrect_password");

		dao.setPassword(userId, newPassword);

		return new SettingResult();
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
		@Nullable private final String error;

		public SettingResult(){
			this.error = null;
		}

		public SettingResult(@Nullable String error){
			this.error = error;
		}

		public String getError(){
			return error;
		}
	}
}
