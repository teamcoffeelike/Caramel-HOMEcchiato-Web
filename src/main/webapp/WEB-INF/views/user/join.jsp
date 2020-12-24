<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/signup.css">
</head>
<body>
<div class="signup_form">
	<form method="post" name="register" id="register">
		<div><h1>SIGN UP</h1></div>
		<div class="int_area">
			<label for=user_name class="field"  id="name">Name</label>
			<input type="text" id="user_name" maxlength="5" required>
			<div class="msg" id="name_msg"></div>
		</div>
		<div class="int_area">
			<label for=user_id class="field">ID</label>
			<input type="text" id="user_id" maxlength="12" required>
			<input type="button" id="idck" value="Check">			
			<div class="msg" id="id_msg"></div>
		</div>
		<div class="int_area">
			<label for=user_pw class="field">Password</label>
			<input type="password" id="user_pw" maxlength="12" required>
			<div class="msg" id="pw_msg"></div>
		</div>
		<div class="int_area">
			<label for=pw_check class="field">Password check</label>
			<input type="password" id="pw_check" maxlength="12" required>
			<div class="msg" id="pwc_msg"></div>
		</div>
		<div class="int_area">
			<label for=user_email class="field">Email</label>
			<input type="text" id="user_email">
			<div class="msg" id="email_msg"></div>
		</div>
		<div class="int_area" id="phone">
			<label for=user_tel class="field">Phone number</label>
			<input type="text" id="user_tel" maxlength="11">
			<div class="msg" id="tel_msg"></div>
		</div>
		<div class="btn_area">
			<input type="submit" value="Join" class="btn1">
			<input type="button" value="Back" class="btn2" onclick="location.href = 'index.html'">
		</div>
	</form>
</div>
</body>
</html>