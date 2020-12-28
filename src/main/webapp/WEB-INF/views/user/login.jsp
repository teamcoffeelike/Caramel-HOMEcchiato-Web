<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" href="css/login.css">
<link rel="stylesheet" href="css/signup.css">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="js/join.js"></script>
<script type="text/javascript" src="js/login.js"></script>
</head>
<body>
<!-- 로그인 폼 -->
<div class="login-form">
<h1>LOGIN</h1>
<div class="content">
	<div class="login">
		<label for="id">이메일 / 핸드폰번호</label>
		<input type="text" name="user_id" id="user_id" />
	</div>
	<div class="login">
		<label for="pw">비밀번호</label>
		<input type="password" name="user_pw" id="user_pw" />
	</div>
	<div class="login-btn">
		<a onclick="go_login()" id="btnLogin">로그인</a>
		<a onclick="#" id="btnKakao">카카오계정으로 로그인</a>
	</div>
	<div class="join-area">
		<a class="joinBtn" id="joinEmail">이메일</a> 또는 
		<a class="joinBtn" id="joinPhone">핸드폰번호</a>로 회원가입
	</div>
</div>
</div>

<!-- 이메일 회원가입 팝업창 -->
<div class="popupEmail">
	<form method="post" action="joinWithEmail">
		<div><h1>JOIN</h1></div>
			<div class="join">
				<label for=name class="field">닉네임</label>
				<input type="text" id="joinEmailName" name="name" required>
				<div class="msg" id="joinEmailName_msg"></div>
			</div>
			<div class="join">
				<label for=email class="field">이메일</label>
				<input type="text" id="joinEmail" name="email">
				<input type="button" id="joinEmailcheck" value="Check">			
				<div class="msg" id="joinEmailCheck_msg"></div>
				<div class="msg" id="joinEmail_msg"></div>
			</div>
			<div class="join">
				<label for=password class="field">비밀번호</label>
				<input type="password" id="joinEmailPassword" name="password" required>
				<div class="msg" id="joinEmailPassword_msg"></div>
			</div>
			<div class="join">
				<label for=pw_check class="field">비밀번호 확인</label>
				<input type="password" id="joinEmailPasswordCheck" name="pw_check" required>
				<div class="msg" id="joinEmailPasswordCheck_msg"></div>
			</div>
			<div class="join-btn">
				<a class="join" onclick="joinWithEmail()">가입</a>
				<a class="back">닫기</a>
		</div>
	</form>
</div>

<!-- 핸드폰번호 회원가입 팝업창 -->
<div class="popupPhone">
	<form method="post" action="joinWithPhone">
		<div><h1>JOIN</h1></div>
			<div class="join">
				<label for=name class="field">닉네임</label>
				<input type="text" id="joinPhoneName" name="name" required>
				<div class="msg" id="joinPhoneName_msg"></div>
			</div>
			<div class="join">
				<label for=phone class="field">핸드폰번호</label>
				<input type="text" id="joinPhone" name="phone">
				<input type="button" id="joinPhoneCheck" value="Check">			
				<div class="msg" id="joinPhoneCheck_msg"></div>
				<div class="msg" id="joinPhone_msg"></div>
			</div>
			<div class="join">
				<label for=password class="field">비밀번호</label>
				<input type="password" id="joinPhonePassword" name="password" required>
				<div class="msg" id="joinPhonePassword_msg"></div>
			</div>
			<div class="join">
				<label for=pw_check class="field">비밀번호 확인</label>
				<input type="password" id="joinPhonePasswordCheck" name="pw_check" required>
				<div class="msg" id="joinPhonePasswordCheck_msg"></div>
			</div>
			<div class="join-btn">
				<a class="join" onclick="joinWithPhone">가입</a>
				<a class="back">닫기</a>
		</div>
	</form>
</div>

<div class="dim"></div>

</body>
</html>