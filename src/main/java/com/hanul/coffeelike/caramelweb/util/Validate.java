package com.hanul.coffeelike.caramelweb.util;

import org.springframework.lang.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Validate{
	private Validate(){}

	public static final int MAX_RECIPE_STEPS = 30;

	private static final Pattern NAME_REGEX = Pattern.compile("[^\t\n]{1,40}");
	private static final Pattern EMAIL_REGEX = Pattern.compile("[^@]+@[^@]+");
	private static final Pattern PHONE_NUMBER_REGEX = Pattern.compile("(0\\d\\d)[ -]?(\\d{4})[ -]?(\\d{4})");
	private static final Pattern PASSWORD_REGEX = Pattern.compile(".{3,63}");
	private static final Pattern POST_TEXT_REGEX = Pattern.compile("(?:\\S|\\s){0,1000}");
	private static final Pattern MOTD_REGEX = Pattern.compile(".{0,100}");
	private static final Pattern RECIPE_TITLE_REGEX = Pattern.compile(".{0,100}");
	private static final Pattern RECIPE_STEP_REGEX = Pattern.compile("(?:\\s|\\S){0,1000}");

	public static boolean name(String name){
		return NAME_REGEX.matcher(name).matches();
	}
	public static boolean email(String email){
		return EMAIL_REGEX.matcher(email).matches();
	}
	public static boolean phoneNumber(String phoneNumber){
		return PHONE_NUMBER_REGEX.matcher(phoneNumber).matches();
	}
	public static boolean password(String password){
		return PASSWORD_REGEX.matcher(password).matches();
	}
	public static boolean postText(String text){
		return POST_TEXT_REGEX.matcher(text).matches();
	}
	public static boolean motd(String motd){
		return MOTD_REGEX.matcher(motd).matches();
	}
	public static boolean recipeTitle(String motd){
		return RECIPE_TITLE_REGEX.matcher(motd).matches();
	}
	public static boolean recipeStep(String motd){
		return RECIPE_STEP_REGEX.matcher(motd).matches();
	}

	/**
	 * 전화번호를 유효 검사 후 데이터베이스 포맷으로 변환시킵니다.
	 *
	 * @return 데이터베이스에 저장되는 포맷의 전화번호. 유효하지 않은 전화번호의 경우 {@code null}.
	 */
	@Nullable public static String verifyAndTrimPhoneNumber(String phoneNumber){
		Matcher matcher = PHONE_NUMBER_REGEX.matcher(phoneNumber);
		if(!matcher.matches()) return null;
		return matcher.group(1)+"-"+matcher.group(2)+"-"+matcher.group(3);
	}
}
