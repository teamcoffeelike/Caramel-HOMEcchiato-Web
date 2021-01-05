package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.JoinDAO;
import com.hanul.coffeelike.caramelweb.dao.LoginDAO;
import com.hanul.coffeelike.caramelweb.data.LoginResult;
import com.hanul.coffeelike.caramelweb.data.UserLoginData;
import com.hanul.coffeelike.caramelweb.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JoinService{
	@Autowired
	private JoinDAO joinDAO;
	@Autowired
	private LoginDAO loginDAO;
	@Autowired
	private UserAuthService authService;

	/**
	 * 이메일을 사용한 회원가입<br>
	 * <br>
	 * <b>에러: </b><br>
	 * bad_name     : 유효하지 않은 이름<br>
	 * bad_email    : 유효하지 않은 이메일<br>
	 * bad_password : 유효하지 않은 비밀번호<br>
	 * user_exists  : 동일한 이메일을 가진 유저가 이미 존재<br>
	 * join_failed  : 예상치 못한 예외로 인해 가입 불가<br>
	 */
	public LoginResult joinWithEmail(String name, String email, String password){
		name = name.trim();
		if(!Validate.name(name)) return new LoginResult("bad_name");

		email = email.trim();
		if(!Validate.email(email)) return new LoginResult("bad_name");

		if(!Validate.password(password)) return new LoginResult("bad_password");

		if(joinDAO.createUserWithEmail(name, email, password)==0) return new LoginResult("user_exists");
		UserLoginData user = loginDAO.findUserWithEmail(email);
		if(user==null) return new LoginResult("join_failed");
		return new LoginResult(user.getId(), authService.generateAuthToken(user.getId()));
	}

	/**
	 * 휴대폰 전화번호를 사용한 회원가입<br>
	 * <br>
	 * <b>에러: </b><br>
	 * bad_name         : 유효하지 않은 이름<br>
	 * bad_phone_number : 유효하지 않은 휴대폰 전화번호<br>
	 * bad_password     : 유효하지 않은 비밀번호<br>
	 * user_exists      : 동일한 전화번호를 가진 유저가 이미 존재<br>
	 * join_failed      : 예상치 못한 예외로 인해 가입 불가<br>
	 */
	public LoginResult joinWithPhoneNumber(String name, String phoneNumber, String password){
		name = name.trim();
		if(!Validate.name(name)) return new LoginResult("bad_name");

		phoneNumber = Validate.verifyAndTrimPhoneNumber(phoneNumber);
		if(phoneNumber==null) return new LoginResult("bad_phone_number");

		if(!Validate.password(password)) return new LoginResult("bad_password");

		if(joinDAO.createUserWithPhoneNumber(name, phoneNumber, password)==0) return new LoginResult("user_exists");
		UserLoginData user = loginDAO.findUserWithPhoneNumber(phoneNumber);
		if(user==null) return new LoginResult("join_failed");
		return new LoginResult(user.getId(), authService.generateAuthToken(user.getId()));
	}

	/**
	 * 카카오 계정 연동을 사용한 회원가입<br>
	 * <br>
	 * <b>에러: </b><br>
	 * bad_name    : 유효하지 않은 이름<br>
	 * user_exists : 동일한 카카오 계정 ID를 가진 유저가 이미 존재<br>
	 * join_failed : 예상치 못한 예외로 인해 가입 불가<br>
	 */
	public LoginResult joinWithKakaoAccount(String name, long kakaoUserId){
		name = name.trim();

		if(!Validate.name(name)) return new LoginResult("bad_name");

		if(joinDAO.createUserWithKakaoAccount(name, kakaoUserId)==0) return new LoginResult("user_exists");
		Integer user = loginDAO.findUserWithKakaoUserId(kakaoUserId);
		if(user==null) return new LoginResult("join_failed");
		return new LoginResult(user, authService.generateAuthToken(user));
	}

	public boolean emailExists(String email){
		return joinDAO.emailExists(email);
	}

	public boolean phoneNumberExists(String phoneNumber){
		return joinDAO.phoneNumberExists(phoneNumber);
	}
}
