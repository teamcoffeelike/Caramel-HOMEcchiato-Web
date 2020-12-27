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
<script type="text/javascript" src="js/joinCheck.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	/* 이메일 가입 팝업 */
	$("#joinEmail").on("click", function(){
		$(".popupEmail").show();
		$(".dim").show();
	});

	/* 핸드폰번호 가입 팝업 */
	$("#joinPhone").on("click", function(){
		$(".popupPhone").show();
		$(".dim").show();
	});
	
	$(".back").on("click", function(){
		$(".popupEmail").hide();
		$(".popupPhone").hide();
		$(".dim").hide();
	});
});
</script>
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
				<label for=user_name class="field"  id="name">닉네임</label>
				<input type="text" id="join_name" name="name" required>
				<div class="msg" id="name_msg"></div>
			</div>
			<div class="join">
				<label for=user_email class="field">이메일</label>
				<input type="text" id="join_email" name="email">
				<input type="button" id="check" value="Check">			
				<div class="msg" id="id_msg"></div>
				<div class="msg" id="email_msg"></div>
			</div>
			<div class="join">
				<label for=user_pw class="field">비밀번호</label>
				<input type="password" id="join_pw" name="password" required>
				<div class="msg" id="pw_msg"></div>
			</div>
			<div class="join">
				<label for=pw_check class="field">비밀번호 확인</label>
				<input type="password" id="pw_check" name="password_ck" required>
				<div class="msg" id="pwc_msg"></div>
			</div>
			<div class="join-btn">
				<a class="join" onclick="join_email()">가입</a>
				<a class="back">닫기</a>
		</div>
	</form>
</div>

<!-- 핸드폰번호 회원가입 팝업창 -->
<div class="popupPhone">
	<form method="post" action="joinWithPhone">
		<div><h1>JOIN</h1></div>
			<div class="join">
				<label for=user_name class="field"  id="name">닉네임</label>
				<input type="text" id="join_name" name="name" required>
				<div class="msg" id="name_msg"></div>
			</div>
			<div class="join">
				<label for=user_phone class="field">핸드폰번호</label>
				<input type="text" id="join_phone" name="phone">
				<input type="button" id="check" value="Check">			
				<div class="msg" id="id_msg"></div>
				<div class="msg" id="email_msg"></div>
			</div>
			<div class="join">
				<label for=user_pw class="field">비밀번호</label>
				<input type="password" id="join_pw" name="password" required>
				<div class="msg" id="pw_msg"></div>
			</div>
			<div class="join">
				<label for=pw_check class="field">비밀번호 확인</label>
				<input type="password" id="pw_check" name="password_ck" required>
				<div class="msg" id="pwc_msg"></div>
			</div>
			<div class="join-btn">
				<a class="join" onclick="join_phone()">가입</a>
				<a class="back">닫기</a>
		</div>
	</form>
</div>

<div class="dim"></div>

</body>
</html>