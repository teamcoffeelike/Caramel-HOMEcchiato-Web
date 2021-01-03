<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/settings.css">
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
		<form name="setProfile" action="setProfile" method="post" enctype="multipart/form-data">
			<div class='active'>
				<div class="setProfile">
					<div class="imgBox">
						<label>
							<input type="file" name="file" id="img-attach" />
							<img id="post-img" class="post-img" src="imgs/profile.png" />
							<!-- src > db에 유저프로필 사진없으면 기본프로필 있으면 유저정보 -->
						</label>
					</div>
					<div class="profile">
						<label for="name">닉네임</label>
						<input type="text" name="name" id="name" value="${data.name}"/><a id='delete-name'>X</a>
						<div class="msg" id="name_msg"></div>
					</div>
					<div class="profile">
						<label for="motd">상태메시지</label>
						<input type="text" name="motd" id="motd" value="${!empty data.motd ? data.motd : '' }"/><a id='delete-motd'>X</a>
					</div>
					<div class="btnSet">
						<a class="btn-fill" onclick="$('[name=setProfile]').submit()" id="btnSubmit">저장</a>
						<a class="btn-empty" onclick="history.go(-1)" class="back">취소</a>
					</div>
				</div>
			</div>
		</form>
		
		<form name="setPassword" action="setPassword" method="post">	
			<div>
				<div class="setPassword" style="display:none">
					<div class="password">
						<label for="originaPw">현재 비밀번호</label>
						<input type="password" name="originaPw" id="originaPw" />
						<div class="msg" id="originaPw_msg"></div>
					</div>
					<div class="password">
						<label for="newPw">새 비밀번호</label>
						<input type="password" name="newPw" id="newPw" />
						<div class="msg" id="newPw_msg"></div>
					</div>
					<div class="password">
						<label for="newPwConfirm">새 비밀번호 확인</label>
						<input type="password" name="newPwConfirm" id="newPwConfirm" />
						<div class="msg" id="newPwConfirm_msg"></div>
					</div>
					<div class="btnSet">
						<a class="btn-fill" onclick="$('[name=setPassword]').submit()" id="btnSubmit">저장</a>
						<a class="btn-empty" onclick="history.go(-1)" class="back">취소</a>
					</div>
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

<script type="text/javascript" src="js/settings.js"></script>
<script type="text/javascript" src="js/fileAttach.js"></script>

</body>
</html>