<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/writePost.css">
</head>
<body>
<div id="write-post">
	<form action="writePost" method="post" enctype="multipart/form-data">
		<div class="imgbox">
			<label>
				<input type="file" name="file" id="img-attach" />
				<img id="post-img" class="post-img" src="http://placehold.it/550x400" />
			</label>
		</div>
		
		<div class="postbox">
			<textarea name="content" title="내용" class="mandatory" placeholder="당신의 이야기를 적어보세요"></textarea>
		</div>
	</form>
	
	<div class="btnSet">
		<a class="btn-fill" onclick="if( necessary() ) { $('form').submit() }">저장</a>
		<a class="btn-empty" onclick="history.go(-1)">취소</a>
	</div>
</div>

<div id="popup-background" onclick="$('#popup, #popup-background').css('display', 'none'); $('#img-attach').removeAttr('disabled');"></div>
<div id="popup">
	<div class="popup-title">
		<h3>포스트 사진 바꾸기</h3>
	</div>
	<div class="popup-btns">
		<a class="another-pic">다른 사진 선택하기</a>
		<a class="delete-pic">현재 사진 삭제하기</a>
		<a class="cancle">취소</a>
	</div>
</div>

<script type="text/javascript" src="js/mandatory_check.js"></script>
<script type="text/javascript" src="js/writePost.js"></script>
<script type="text/javascript" src="js/fileAttach.js"></script>

</body>
</html>