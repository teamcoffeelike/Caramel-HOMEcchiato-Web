<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/settings.css">
<script type="text/javascript" src="js/setting_profile.js"></script>
<script type="text/javascript" src="js/setting_password.js"></script>
<script type="text/javascript">
/*탭변경*/
$(document).on('click', '#tabs li', function(){
	$('#tabs li').removeClass();
	$(this).addClass('active');

	var idx = $('#tabs li.active').index();
	$('#tabContent > div').removeClass();
	$('#tabContent > div:eq('+ idx +')').addClass('active');
	
	if( idx==0 ) {
		$('.setProfile').css('display', 'block');
		$('.setPassword').css('display', 'none');
	}else {
		$('.setProfile').css('display', 'none');
		$('.setPassword').css('display', 'block');
	}
});
</script>
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
						<input type="hidden" name="profileImageChanged" id="profileImageChanged" value="false">
						<input type="file" name="profileImage" id="img-attach" accept=".png,.jpg,.jpeg,.gif"/>
						<label>
							<img id="profile-img" class="profile-img" src="${profileImage}" />
							<!-- src > db에 유저프로필 사진없으면 기본프로필 있으면 유저정보 -->
						</label>
					</div>
					<div class="profile">
						<input type="hidden" name="nameChanged" id="nameChanged" value="false">
						<label for="name">닉네임</label>
						<input type="text" name="name" id="name" value="${data.user.name}"/>
						<a id='delete-name' style="display: inline;">X</a>
						<div class="msg" id="name_msg"></div>
					</div>
					<div class="profile">
						<input type="hidden" name="motdChanged" id="motdChanged" value="false">
						<label for="motd">상태메시지</label>
						<input type="text" name="motd" id="motd" value="${data.user.motd}"/>
						<a id='delete-motd' style="display: ${empty data.user.motd ? 'none' : 'inline'};">X</a>
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
						<label for="originalPw">현재 비밀번호</label>
						<input type="password" name="originalPw" id="originalPw" />
					</div>
					<div class="password">
						<label for="newPassword">새 비밀번호</label>
						<input type="password" name="newPassword" id="newPassword" />
						<div class="msg" id="newPassword_msg"></div>
					</div>
					<div class="password">
						<label for="newPasswordConfirm">새 비밀번호 확인</label>
						<input type="password" name="newPasswordConfirm" id="newPasswordConfirm" />
						<div class="msg" id="newPasswordConfirm_msg"></div>
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

<div id="popup-background" onclick=" $('#img-attach').removeAttr('disabled');"></div>
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
</body>
</html>