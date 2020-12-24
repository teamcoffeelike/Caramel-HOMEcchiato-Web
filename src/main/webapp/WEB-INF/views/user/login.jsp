<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" href="css/login.css">
<script type="text/javascript"></script>
</head>
<body>
<div class="login-form">
<h1>LOGIN</h1>
	<form action="#" method="post" name="member" id="member">
		<div class="int-area">
			<label for="id">Email/PhoneNumber</label>
			<input type="text" name="user_id" id="user_id" />
		</div>
		<div class="int-area">
			<label for="pw">Password</label>
			<input type="password" name="user_pw" id="user_pw" />
		</div>
	<div class="btn-area">
		<a onclick="go_login()" id="btnLogin">LOGIN</a>
		<a id="btnJoin">JOIN</a>
<!-- 		<input type="submit" id="btnLogin" value="LOGIN"> -->
<!-- 		<input type="button" id="btnJoin" value="JOIN" onclick="location.href = 'index.html'"> -->
	</div>
	<div class="btn-kakao">
		<a id="btnKakao">카카오로 로그인</a>
	</div>
</form>
</div>
</body>
</html>