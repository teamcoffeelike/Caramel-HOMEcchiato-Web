package com.hanul.coffeelike.caramelweb.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.data.UserProfileData;
import com.hanul.coffeelike.caramelweb.data.UserSettingData;
import com.hanul.coffeelike.caramelweb.service.FileService;
import com.hanul.coffeelike.caramelweb.service.UserService;
import com.hanul.coffeelike.caramelweb.service.UserService.SettingResult;
import com.hanul.coffeelike.caramelweb.util.AttachmentURLConverter;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private FileService fileService;
	
	// 팔로우/팔로잉 리스트
	@RequestMapping("/follows")
	public String follows(HttpSession session, Model model) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if (loginUser == null ) {
			return "loginRequired";
		}
		List<UserProfileData> users = userService.getFollower(loginUser.getUserId());
		model.addAttribute("followers", users);
		return "mypage/follows";
	}
	
	//프로필
	@RequestMapping("/profile")
	public String myprofile(HttpSession session, 
			Model model,
			int userId){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser == null) return "loginRequired";
		UserProfileData profile = userService.profile(loginUser.getUserId(), userId);
		model.addAttribute("data", profile);
		model.addAttribute("profileImage", profile.getProfileImage()==null ?
				"imgs/profile.png" : 
					AttachmentURLConverter.profileImageFromId(profile.getId()));
		return "user/profile";
	}
	
	//프로필 설정 화면 요청
	@RequestMapping("/settings")
	public String settings(HttpSession session, Model model) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser == null) return "loginRequired";
		UserSettingData data = userService.userSettings(loginUser.getUserId());
		model.addAttribute("data", data);
		return "mypage/settings";
	}
	
	//프로필 설정 > 프로필사진, 닉네임, 상태메시지
	@RequestMapping("/setProfile")
	public String setProfile(HttpSession session,
							 @RequestParam String name,
							 @RequestParam String motd,
							 @RequestParam MultipartFile profileImage) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser == null) return "loginRequired";
		userService.setName(loginUser.getUserId(), name);
		userService.setMotd(loginUser.getUserId(), motd);
		SettingResult r = userService.setProfileImage(loginUser.getUserId(), profileImage);
		System.out.println(r.getError());
		
		return "redirect:profile?userId=" + loginUser.getUserId();
	}
	
	//비밀번호 변경
	@RequestMapping("/setPassword")
	public String setPassword(HttpSession session,
            				  @RequestParam String password,
            				  @RequestParam String newPassword) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser == null) return "loginRequired";
		SettingResult result = userService.setPassword(loginUser.getUserId(), password, newPassword);
		
		return "";
	}
	
}
