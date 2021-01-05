package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.LoginDAO;
import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.data.LoginResult;
import com.hanul.coffeelike.caramelweb.data.UserLoginData;
import com.hanul.coffeelike.caramelweb.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginService{
	@Autowired
	private LoginDAO dao;
	@Autowired
	private UserAuthService authService;

	/**
	 * 이메일을 사용한 로그인<br>
	 * <br>
	 * <b>에러: </b><br>
	 * bad_email : 유효하지 않은 이메일<br>
	 * login_failed : 로그인 실패<br>
	 */
	public LoginResult loginWithEmail(String email, String password){
		email = email.trim();
		if(!Validate.email(email)) return new LoginResult("bad_email");

		if(!Validate.password(password)) return new LoginResult("login_failed");

		UserLoginData user = dao.findUserWithEmail(email);
		if(user==null||!password.equals(user.getPassword())){
			return new LoginResult("login_failed");
		}

		return new LoginResult(user.getId(), authService.generateAuthToken(user.getId()));
	}

	/**
	 * 휴대폰 전화번호를 사용한 로그인<br>
	 * <br>
	 * <b>에러: </b><br>
	 * bad_phone_number : 유효하지 않은 휴대폰 전화번호<br>
	 * login_failed : 로그인 실패<br>
	 */
	public LoginResult loginWithPhoneNumber(String phoneNumber, String password){
		phoneNumber = Validate.verifyAndTrimPhoneNumber(phoneNumber);
		if(phoneNumber==null) return new LoginResult("bad_phone_number");

		if(!Validate.password(password)) return new LoginResult("login_failed");

		UserLoginData user = dao.findUserWithPhoneNumber(phoneNumber);
		if(user==null||!password.equals(user.getPassword())){
			return new LoginResult("login_failed");
		}
		return new LoginResult(user.getId(), authService.generateAuthToken(user.getId()));
	}

	public LoginResult loginWithKakao(long kakaoUserId){
		Integer userId = dao.findUserWithKakaoUserId(kakaoUserId);
		if(userId==null){
			return new LoginResult("login_failed");
		}
		return new LoginResult(userId, authService.generateAuthToken(userId));
	}

	public LoginResult loginWithAuthToken(UUID uuid){
		AuthToken token = authService.getAuthTokenInformation(uuid);
		if(token==null){
			return new LoginResult("login_failed");
		}
		authService.updateAuthToken(uuid);
		return new LoginResult(token.getUserId(), uuid);
	}
}
