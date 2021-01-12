<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/writePost.css">
<script type="text/javascript" src="js/mandatory_check.js"></script>
<script type="text/javascript">
/*업로드 될 파일이 이미지 파일인지 체크 함수*/
function isImage( filename ){
	var ext = filename.substring(filename.lastIndexOf('.')+1).toLowerCase();
	var imgs = ['jpg', 'png', 'bmp', 'jpeg', 'gif']; // TODO
	if( imgs.indexOf(ext) > -1 ) return true;
	else false;
}
var textChanged = false;
var postImageChanged = false;
$(function(){
	$('#img-attach').on('change', function(){
		var attach = this.files[0];
		postImageChanged = true;
		if (attach){
			if(isImage(attach.name)){
				var reader = new FileReader();
				reader.onload = function(e) {
					$('#post-img').attr('src', e.target.result);
				}
				reader.readAsDataURL(attach);
			}else {
				alert('이미지 파일만 등록해주세요!');
			}
		}
	});

	$('#text').on("input", function(){
		textChanged = true;
	});

	$("form").on('submit', function(){
		$("#postImageChanged").val(postImageChanged);
		if(!postImageChanged){
			$("#img-attach").val("");
		}
		
		$("#textChanged").val(textChanged);
		if(!textChanged){
			$("#text").val("");
		}
	});
	
});
</script>
</head>
<body>
	<div id="post">
	<form action="editPost" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${post.id }" />
		<div class="imgbox">
			<input type="hidden" name="postImageChanged" id="postImageChanged" value="false">
			<label>
				<input type="file" name="image" id="img-attach" />
				<img id="post-img" class="post-img" src="${postImage }" />
			</label>
		</div>
		
		<div class="postbox">
			<input type="hidden" name="textChanged" id="textChanged" value="false">
			<textarea id="text" name="text" title="내용" class="mandatory">${post.text }</textarea>
		</div>
	</form>
	
	<div class="btnSet">
		<a class="btn-fill" onclick="if( necessary() ) { $('form').submit() }">저장</a>
		<a class="btn-empty" onclick="history.go(-1)">취소</a>
	</div>
</div>
</body>
</html>