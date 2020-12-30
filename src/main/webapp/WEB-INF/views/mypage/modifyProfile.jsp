<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/modifyProfile.css">
</head>
<body>
<div id="modifyProfile">
	<div>
		<ul id="tabs">
			<li class="active">프로필 편집</li>
			<li>비밀번호 변경</li>
		</ul>
	</div>
	<div id="tabContent">
		<form action="setProfile">
			<div class='active'>
				<div class="setProfile">
					<div class="imgBox">
						<label>
							<input type="file" name="file" id="img-attach" />
							<img id="post-img" class="post-img" src="imgs/profile.png" />
						</label>
					</div>
					<div class="profile">
						<label for="name">닉네임</label>
						<input type="text" name="user_name" id="user_name" />
					</div>
					<div class="profile">
						<label for="motd">상태메시지</label>
						<input type="text" name="user_motd" id="user_motd" />
					</div>
					<div class="btnSet">
						<a onclick="if( necessary() ) { $('form').submit() }" id="btnSubmit">저장</a>
						<a onclick="history.go(-1)" id="btnReset">취소</a>
					</div>
				</div>
			</div>
		</form>
		
		<form action="setPassword">	
			<div>
				<div class="password">
					
				</div>
			</div>
		</form>
	</div>
</div>

<div id="popup-background" onclick="$('#popup, #popup-background').css('display', 'none'); $('#img-attach').removeAttr('disabled');"></div>
<div id="popup">
	<div class="popup-title">
		<h3>프로필 사진 바꾸기</h3>
	</div>
	<div class="popup-btns">
		<a class="another-pic">다른 사진 선택하기</a>
		<a class="delete-pic">현재 사진 삭제하기</a>
		<a class="cancle">취소</a>
	</div>
</div>

<script type="text/javascript" src="js/modifyProfile.js"></script>
<script type="text/javascript" src="js/fileAttach.js"></script>
</body>
</html>