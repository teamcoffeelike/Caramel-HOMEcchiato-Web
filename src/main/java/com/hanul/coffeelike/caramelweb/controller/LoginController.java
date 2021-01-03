package com.hanul.coffeelike.caramelweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.data.LoginResult;
import com.hanul.coffeelike.caramelweb.data.UserLoginData;
import com.hanul.coffeelike.caramelweb.service.JoinService;
import com.hanul.coffeelike.caramelweb.service.LoginService;
import com.hanul.coffeelike.caramelweb.service.UserAuthService;
import com.hanul.coffeelike.caramelweb.util.HttpConnector;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import com.hanul.coffeelike.caramelweb.util.KakaoApiUtils;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController{
	@Autowired
	private JoinService joinService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private UserAuthService authService;
	
	//로그인 화면 요청 -> 메인화면
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session){
		return "redirect:login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpSession session){
		return "user/login";
	}
	
	//소개 화면 요청
	@RequestMapping("/list.in")
	public String Introduce(HttpSession session) {
		return "introduce/context";
	}
	
	//카카오 로그인
	@RequestMapping("loginWithKakao")
	public String loginWithKakao(HttpSession session) {
		String state = UUID.randomUUID().toString();
		session.setAttribute("state", state);
		
		String url = "https://kauth.kakao.com/oauth/authorize?response_type=code"
		+ "&client_id=" + KakaoApiUtils.KAKAO_API_KEY
		+ "&redirect_uri=" + "http://localhost/caramelweb/loginWithKakaoCallback"
		+ "&state=" + state;
		
		return "redirect:" + url;
	}
	
	//카카오 callback URL 처리
	@RequestMapping("/loginWithKakaoCallback")
	public String joinWithKakao(HttpSession session,
								@RequestParam String state,
								@RequestParam(required = false) String code,
								@RequestParam(required = false) String error) {
		if(!state.equals((String)session.getAttribute("state")) || error != null)
			return "redirect:/";
		
		
		try {
			//토큰 발급
			HttpConnector.Response<JsonObject> response = HttpConnector
					.create("https://kauth.kakao.com/oauth/token?grant_type=authorization_code"
								+ "&client_id=" + KakaoApiUtils.KAKAO_API_KEY
								+ "&code=" + code + "&state=" + state)
					.setRequestProperty("Content-type", "application/json")
					.readAsJsonObject();
			JsonObject jsonObject = response.getResponse();
			String token = jsonObject.get("access_token").getAsString();
			String type = jsonObject.get("token_type").getAsString();

			response = HttpConnector.create("https://kapi.kakao.com/v2/user/me")
					.setRequestProperty("Content-type", "application/json")
					.setRequestProperty("Authorization", type + " " + token)
					.readAsJsonObject();
			
			long kakaoUserId = response.getResponse().get("id").getAsLong();
			LoginResult result = loginService.loginWithKakao(kakaoUserId);
			if(result.getUserId() != null) {
				SessionAttributes.setLoginUser(session, result.createAuthToken());
				return "redirect:/list.in";
			}
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return "redirect:/list.in";
	}
	

	@Nullable private String getName(HttpConnector.Response<JsonObject> response){
		JsonElement kakaoAccount = response.getResponse().get("kakao_account");
		if(kakaoAccount==null) return null;
		JsonElement profile = kakaoAccount.getAsJsonObject().get("profile");
		if(profile==null) return null;
		JsonElement nickname = profile.getAsJsonObject().get("nickname");
		if(nickname==null) return null;

		return nickname.getAsString();
	}
	
	//로그아웃 처리
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		SessionAttributes.setLoginUser(session, null);
		authService.removeAuthToken(loginUser.getAuthToken());
		
		return "redirect:/";
	}

}