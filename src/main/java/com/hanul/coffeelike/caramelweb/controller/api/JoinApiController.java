package com.hanul.coffeelike.caramelweb.controller.api;

import com.google.gson.JsonObject;
import com.hanul.coffeelike.caramelweb.data.LoginResult;
import com.hanul.coffeelike.caramelweb.service.JoinService;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;
import com.hanul.coffeelike.caramelweb.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class JoinApiController{
	@Autowired
	private JoinService joinService;

	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String onException(MissingServletRequestParameterException ex){
		return JsonHelper.failure("bad_parameter");
	}

	/**
	 * 이메일을 사용한 회원가입<br>
	 * <br>
	 * <b>성공 시:</b>
	 *
	 * <pre>
	 * <code> {
	 *   userId: Integer
	 * }</code>
	 * </pre>
	 *
	 * <b>에러: </b><br>
	 * bad_name     : 유효하지 않은 이름<br>
	 * bad_email    : 유효하지 않은 이메일<br>
	 * bad_password : 유효하지 않은 비밀번호<br>
	 * user_exists  : 동일한 이메일을 가진 유저가 이미 존재
	 */
	@ResponseBody
	@RequestMapping("/api/joinWithEmail")
	public String joinWithEmail(
			HttpSession session,
			@RequestParam String name,
			@RequestParam String email,
			@RequestParam String password){
		if(!Validate.name(name)) return JsonHelper.failure("bad_email");
		if(!Validate.email(email)) return JsonHelper.failure("bad_email");
		if(!Validate.password(password)) return JsonHelper.failure("bad_password");

		LoginResult result = joinService.joinWithEmail(name, email, password);
		if(result.getError()==null){
			SessionAttributes.setLoginUser(session, result.createAuthToken());
		}
		return JsonHelper.GSON.toJson(result);
	}

	/**
	 * 휴대폰 전화번호를 사용한 회원가입<br>
	 * <br>
	 * <b>성공 시:</b>
	 *
	 * <pre>
	 * <code> {
	 *   userId: Integer
	 * }</code>
	 * </pre>
	 *
	 * <b>에러: </b><br>
	 * bad_name         : 유효하지 않은 이름<br>
	 * bad_phone_number : 유효하지 않은 휴대폰 전화번호<br>
	 * bad_password     : 유효하지 않은 비밀번호<br>
	 * user_exists      : 동일한 이메일을 가진 유저가 이미 존재
	 */
	@ResponseBody
	@RequestMapping("/api/joinWithPhoneNumber")
	public String joinWithPhoneNumber(
			HttpSession session,
			@RequestParam String name,
			@RequestParam String phoneNumber,
			@RequestParam String password){
		if(!Validate.name(name)) return JsonHelper.failure("bad_email");
		if(!Validate.phoneNumber(phoneNumber)) return JsonHelper.failure("bad_email");
		if(!Validate.password(password)) return JsonHelper.failure("bad_password");

		LoginResult result = joinService.joinWithPhoneNumber(name, phoneNumber, password);
		if(result.getError()==null){
			SessionAttributes.setLoginUser(session, result.createAuthToken());
		}
		return JsonHelper.GSON.toJson(result);
	}
	
	@ResponseBody
	@RequestMapping("/api/emailExists")
	public String emailExists(@RequestParam String email) {
		boolean exists = joinService.emailExists(email);
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("exists", exists);
		return JsonHelper.GSON.toJson(jsonObject);
	}
	
	@ResponseBody
	@RequestMapping("/api/phoneNumberExists")
	public String phoneNumberExists(@RequestParam String phoneNumber) {
		boolean exists = joinService.phoneNumberExists(phoneNumber);
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("exists", exists);
		return JsonHelper.GSON.toJson(jsonObject);
	}
}
