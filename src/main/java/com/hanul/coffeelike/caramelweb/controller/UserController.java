package com.hanul.coffeelike.caramelweb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.data.UserProfileData;
import com.hanul.coffeelike.caramelweb.data.UserSettingData;
import com.hanul.coffeelike.caramelweb.service.UserService;
import com.hanul.coffeelike.caramelweb.service.UserService.SettingResult;
import com.hanul.coffeelike.caramelweb.util.AttachmentFileResolver;
import com.hanul.coffeelike.caramelweb.util.AttachmentURLConverter;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	// 팔로우/팔로잉 리스트
	@RequestMapping("/follows")
	public String follows(HttpSession session, Model model) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if (loginUser == null ) {
			return "loginRequired";
		}
		List<UserProfileData> followers = userService.getFollower(loginUser.getUserId(), loginUser.getUserId());
		List<UserProfileData> following = userService.getFollowing(loginUser.getUserId(), loginUser.getUserId());
		
		Map<Integer, UserProfileData> users = new HashMap<>();
		
		for(UserProfileData u : followers) {
			users.put(u.getId(), u);
		}
		for(UserProfileData u : following) {
			users.put(u.getId(), u);
		}
		
		model.addAttribute("followers", followers);
		model.addAttribute("following", following);
		model.addAttribute("userMap", users);
		return "mypage/follows";
	}
	
	// 친구찾기 화면
	@RequestMapping("/search")
	public String searchFriend(Model model) {
		return "mypage/friend";
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
		if(AttachmentFileResolver.doesProfileImageExists(profile.getProfileImage()))
			model.addAttribute("profileImage", AttachmentURLConverter.profileImageFromId(userId));
		else
			model.addAttribute("profileImage", "imgs/profile.png");
		return "user/profile";
	}
	
	//프로필 설정 화면 요청
	@RequestMapping("/settings")
	public String settings(HttpSession session, Model model) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser == null) return "loginRequired";
		UserSettingData data = userService.userSettings(loginUser.getUserId());
		model.addAttribute("data", data);
		model.addAttribute("profileImage", data.getUser().getProfileImage()==null ?
				"imgs/profile.png" : 
					AttachmentURLConverter.profileImageFromId(data.getUser().getId()));
		return "mypage/settings";
	}
	
	//프로필 설정 > 프로필사진, 닉네임, 상태메시지
	@RequestMapping(value = "/setProfile", produces = "application/json;charset=UTF-8")
	public String setProfile(HttpSession session,
							 Model model,
							 @RequestParam String name,
							 @RequestParam String motd,
							 @RequestParam MultipartFile profileImage,
							 @RequestParam boolean nameChanged,
							 @RequestParam boolean motdChanged,
							 @RequestParam boolean profileImageChanged) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser == null) return "loginRequired";
		
		if(nameChanged) userService.setName(loginUser.getUserId(), name);
		if(motdChanged) userService.setMotd(loginUser.getUserId(), motd);
		if(profileImageChanged) {
			if(profileImage.isEmpty()) userService.removeProfileImage(loginUser.getUserId());
			else {
				SettingResult result = userService.setProfileImage(loginUser.getUserId(), profileImage);
				if(result.getError() != null) {
					String imageError = result.getError().toString();
					switch (imageError) {
					case "bad_image" :
						model.addAttribute("msg", "이미지가 존재하지 않습니다!");
						break;
					default:
						model.addAttribute("msg", "이미지 등록 실패!");
						break;
					}
					return "mypage/settingRequired";
				}
			}
		}
		
		return "redirect:profile?userId=" + loginUser.getUserId();
	}
	
	//비밀번호 변경
	@RequestMapping("/setPassword")
	public String setPassword(HttpSession session,
							  Model model,
            				  @RequestParam String originalPw,
            				  @RequestParam String newPassword) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser == null) return "loginRequired";
		SettingResult result = userService.setPassword(loginUser.getUserId(), originalPw, newPassword);
		if(result.getError() != null) {
			String passwordError = result.getError().toString();
			switch (passwordError) {
			case "no_password":
				model.addAttribute("msg", "비밀번호를 사용하지 않는 계정입니다!");
				break;
			case "incorrect_password":
				model.addAttribute("msg", "현재 비밀번호가 일치하지 않습니다!");
				break;
			case "bad_new_password":
				model.addAttribute("msg", "새 비밀번호가 현재 비밀번호와 일치하거나 비밀번호 양식이 올바르지 않습니다!");
				break;
			default:
				model.addAttribute("msg", "비밀번호 변경 실패!");
				break;
			}
			return "mypage/settingRequired";
		}
		return "redirect:profile?userId=" + loginUser.getUserId();
	}
	
}
