package com.hanul.coffeelike.caramelweb.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.data.LoginResult;
import com.hanul.coffeelike.caramelweb.service.JoinService;
import com.hanul.coffeelike.caramelweb.service.LoginService;
import com.hanul.coffeelike.caramelweb.service.UserAuthService;
import com.hanul.coffeelike.caramelweb.util.HttpConnector;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import com.hanul.coffeelike.caramelweb.util.KakaoApiUtils;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

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
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return "redirect:login";
		return "redirect:allPostList";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpSession session){
		return "user/login";
	}

	//소개 화면 요청
	@RequestMapping("/list.in")
	public String introduce(HttpSession session){
		return "introduce/context";
	}

	//카카오 로그인
	@RequestMapping("/loginWithKakao")
	public String loginWithKakao(HttpSession session){
		String state = UUID.randomUUID().toString();
		session.setAttribute("state", state);


		String url = "https://kauth.kakao.com/oauth/authorize?response_type=code&&scope=profile"
				+"&client_id="+KakaoApiUtils.KAKAO_API_KEY
				+"&redirect_uri="+"http://localhost/caramelweb/loginWithKakaoCallback"
				+"&state="+state;

		return "redirect:"+url;
	}

	//카카오 callback URL 처리
	@RequestMapping("/loginWithKakaoCallback")
	public String loginWithKakaoCallback(HttpSession session,
	                                     @RequestParam String state,
	                                     @RequestParam(required = false) String code,
	                                     @RequestParam(required = false) String error,
	                                     @RequestParam @Nullable String name){
		if(!state.equals(session.getAttribute("state"))||error!=null)
			return "redirect:/";


		try{
			HttpConnector.Response<JsonObject> response = HttpConnector
					.create("https://kauth.kakao.com/oauth/token?grant_type=authorization_code"
							+"&client_id="+KakaoApiUtils.KAKAO_API_KEY
							+"&code="+code
							+"&state="+state)
					.setRequestProperty("Content-type", "application/json")
					.readAsJsonObject();
			JsonObject jsonObject = response.getResponse();
			String token = jsonObject.get("access_token").getAsString();
			String type = jsonObject.get("token_type").getAsString();

			response = HttpConnector.create("https://kapi.kakao.com/v2/user/me")
					.setRequestProperty("Content-type", "application/json")
					.setRequestProperty("Authorization", type+" "+token)
					.setRequestProperty("property_keys", "[\"properties.nickname\"]")
					.readAsJsonObject();
			if(!response.isSuccess()) return getResponseError(response);
			long kakaoUserId = response.getResponse().get("id").getAsLong();

			if(name==null){
				name = getName(response);
				if(name==null) return JsonHelper.failure("needs_agreement");
			}

			//카카오톡 회원가입
			LoginResult result = joinService.joinWithKakaoAccount(name, kakaoUserId);
			if(result.getError()==null){
				SessionAttributes.setLoginUser(session, result.createAuthToken());
			}

			//로그인
			result = loginService.loginWithKakao(kakaoUserId);
			if(result.getUserId()!=null){
				SessionAttributes.setLoginUser(session, result.createAuthToken());
			}

		}catch(IOException e){
			e.printStackTrace();
		}
		return "redirect:allPostList";
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

	private static String getResponseError(HttpConnector.Response<JsonObject> response){
		if(response.isSuccess()) throw new IllegalArgumentException("Response not failed");

		int errorCode = response.getResponse().get("code").getAsInt();
		switch(errorCode){
		case -1: // 카카오 사망
			return JsonHelper.failure("kakao_service_unavailable");
		case -2: // 몬가이상함
		case -401: // 만료됨
			return JsonHelper.failure("bad_kakao_login_token");
		default:
			// TODO 카카오 로그아웃?
			return JsonHelper.failure("unknown");
		}
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