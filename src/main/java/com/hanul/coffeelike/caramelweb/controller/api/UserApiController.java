package com.hanul.coffeelike.caramelweb.controller.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.data.UserProfileData;
import com.hanul.coffeelike.caramelweb.data.UserSettingData;
import com.hanul.coffeelike.caramelweb.service.UserService;
import com.hanul.coffeelike.caramelweb.service.UserService.SettingResult;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserApiController extends BaseExceptionHandlingController{
	@Autowired
	private UserService service;

	/**
	 * 유저 설정<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * {
	 *   user: {
	 *     id: Integer
	 *     name: String
	 *     [ motd ]: String
	 *     [ profileImage ]: URL
	 *   }
	 *   isSocialAccount: Boolean
	 * }
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * not_logged_in : 로그인 상태가 아님<br>
	 */
	@RequestMapping(value = "/api/userSettings", produces = "application/json;charset=UTF-8")
	public String userSettings(HttpSession session){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		UserSettingData userSettingData = service.userSettings(loginUser.getUserId());

		return JsonHelper.GSON.toJson(userSettingData);
	}

	/**
	 * 유저의 프로필 요청<br>
	 * 프로필 화면에서 표시될 이름, motd, 프로필 사진을 가져옵니다.<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * {
	 *   id: Integer
	 *   name: String
	 *   [ motd ]: String
	 *   [ profileImage ]: URL
	 *   [ isFollowingYou ]: Boolean
	 *   [ isFollowedByYou ]: Boolean
	 * }
	 * }</pre>
	 * <b>에러: </b><br>
	 * no_user : 존재하지 않는 유저<br>
	 */
	@RequestMapping(value = "/api/profile", produces = "application/json;charset=UTF-8")
	public String profile(HttpSession session,
	                      @RequestParam int userId){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		UserProfileData userProfileData = service.profile(loginUser==null ? null : loginUser.getUserId(), userId);
		if(userProfileData==null) return JsonHelper.failure("no_user");

		return JsonHelper.GSON.toJson(userProfileData);
	}

	/**
	 * 닉네임 설정<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * 추가 데이터 없음
	 * }</pre>
	 * <b>에러: </b><br>
	 * bad_name : 유효하지 않은 name 인자<br>
	 * not_logged_in : 로그인 상태가 아님<br>
	 */
	@RequestMapping(value = "/api/setName", produces = "application/json;charset=UTF-8")
	public String setName(HttpSession session,
	                      @RequestParam String name){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		SettingResult result = service.setName(loginUser.getUserId(), name);

		return JsonHelper.GSON.toJson(result);
	}

	/**
	 * 상태메세지 설정<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * 추가 데이터 없음
	 * }</pre>
	 * <b>에러: </b><br>
	 * bad_motd : 유효하지 않은 motd 인자<br>
	 * not_logged_in : 로그인 상태가 아님<br>
	 */
	@RequestMapping(value = "/api/setMotd", produces = "application/json;charset=UTF-8")
	public String setMotd(HttpSession session,
	                      @RequestParam String motd){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		SettingResult result = service.setMotd(loginUser.getUserId(), motd);

		return JsonHelper.GSON.toJson(result);
	}

	/**
	 * 프로필 사진 설정<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * 추가 데이터 없음
	 * }</pre>
	 * <b>에러: </b><br>
	 * bad_image : 유효하지 않은 image 인자<br>
	 * not_logged_in : 로그인 상태가 아님<br>
	 * unexpected : 프로필이미지 등록 불가<br>
	 */
	@RequestMapping(value = "/api/setProfileImage", produces = "application/json;charset=UTF-8")
	public String setProfileImage(HttpSession session,
	                              @RequestParam MultipartFile profileImage){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		SettingResult result = service.setProfileImage(loginUser.getUserId(), profileImage);

		return JsonHelper.GSON.toJson(result);
	}

	/**
	 * 패스워드 변경<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * 추가 데이터 없음
	 * }</pre>
	 * <b>에러: </b><br>
	 * not_logged_in      : 로그인 상태가 아님<br>
	 * no_password        : password를 사용하지 않는 계정(aka 소셜 로그인 사용 중인 계정)<br>
	 * incorrect_password : 기존 암호와 일치하지 않는 password 인자<br>
	 * bad_new_password   : 유효하지 않은 newPassword 인자<br>
	 */
	@ResponseBody
	@RequestMapping(value = "/api/setPassword", produces = "application/json;charset=UTF-8")
	public String setPassword(HttpSession session,
	                          @RequestParam String password,
	                          @RequestParam String newPassword){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		SettingResult result = service.setPassword(loginUser.getUserId(), password, newPassword);

		return JsonHelper.GSON.toJson(result);
	}

	/**
	 * 팔로워 가져오기<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * {
	 *   users: [
	 *     {
	 *       id: Integer
	 *       name: String
	 *       [ profileImage ]: URL
	 *     }
	 *   ]
	 * }
	 * }</pre>
	 */
	@RequestMapping(value = "/api/getFollower", produces = "application/json;charset=UTF-8")
	public String getFollower(HttpSession session,
	                          @RequestParam int user){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);

		List<UserProfileData> users = service.getFollower(user, loginUser==null ? null : loginUser.getUserId());
		JsonElement e = JsonHelper.GSON.toJsonTree(users);

		JsonObject o = new JsonObject();
		o.add("users", e);

		return JsonHelper.GSON.toJson(o);
	}

	/**
	 * 팔로잉하는 유저 가져오기<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * {
	 *   users: [
	 *     {
	 *       id: Integer
	 *       name: String
	 *       [ profileImage ]: URL
	 *     }
	 *   ]
	 * }
	 * }</pre>
	 */
	@RequestMapping(value = "/api/getFollowing", produces = "application/json;charset=UTF-8")
	public String getFollowing(HttpSession session,
	                           @RequestParam int user){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);

		List<UserProfileData> users = service.getFollowing(user, loginUser==null ? null : loginUser.getUserId());
		JsonElement e = JsonHelper.GSON.toJsonTree(users);

		JsonObject o = new JsonObject();
		o.add("users", e);

		return JsonHelper.GSON.toJson(o);
	}

	/**
	 * 팔로잉 여부 설정<br>
	 * <br>
	 * <b>성공 시:</b>
	 * <pre>{@code
	 * 추가 데이터 없음
	 * }</pre>
	 * <b>에러: </b><br>
	 * not_logged_in<br>
	 * same_user<br>
	 * already_following<br>
	 * not_following<br>
	 */
	@RequestMapping(value = "/api/setFollowing", produces = "application/json;charset=UTF-8")
	public String setFollowing(HttpSession session,
	                           @RequestParam int followingId,
	                           @RequestParam boolean following){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		if(loginUser.getUserId()==followingId){
			return JsonHelper.failure("same_user");
		}

		boolean result = service.setFollowing(loginUser.getUserId(), followingId, following);
		if(!result){
			return JsonHelper.failure(following ? "already_following" : "not_following");
		}

		return "{}";
	}
	
	@RequestMapping(value = "/api/searchUserByName", produces="application/json;charset=UTF-8")
	public String searchUserByName(@RequestParam String name){
		List<UserProfileData> list = service.searchUserByName(name);
		
		JsonElement e = JsonHelper.GSON.toJsonTree(list);

		JsonObject o = new JsonObject();
		o.add("users", e);

		return JsonHelper.GSON.toJson(o);
	}
}
