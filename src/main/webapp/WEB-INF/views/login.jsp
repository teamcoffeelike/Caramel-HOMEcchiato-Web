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
			<label for="id">ID</label>
			<input type="text" name="user_id" id="user_id" />
		</div>
		<div class="int-area">
			<label for="pw">PASSWORD</label>
			<input type="password" name="user_pw" id="user_pw" />
		</div>
	<div class="btn-area">
		<input type="submit" id="btnLogin" value="LOGIN"> 
		<input type="button" id="btnJoin" value="JOIN" onclick="location.href = 'index.html'">
	</div>
	<div class="btn-kakao">
		<input type="button" id="btnKakao" value="카카오톡으로 로그인"> 
	</div>
</form>
</div>
</body>
</html>