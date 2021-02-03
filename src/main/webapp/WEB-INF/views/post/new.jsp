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
$(function(){
	$('#img-attach').on('change', function(){
		var attach = this.files[0];
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
});
</script>
</head>
<body>
<div id="post">
	<form action="writePost" method="post" enctype="multipart/form-data">
		<div class="imgbox">
			<label>
				<input type="file" name="image" id="img-attach" />
				<img id="post-img" class="post-img" src="imgs/post.png" />
			</label>
		</div>
		
		<div class="postbox">
			<textarea name="text" title="내용" class="mandatory" placeholder="당신의 이야기를 적어보세요"></textarea>
		</div>
	</form>
	
	<div class="btnSet">
		<a class="btn-fill" onclick="if( necessary() ) { $('form').submit() }">저장</a>
		<a class="btn-empty" onclick="history.go(-1)">취소</a>
	</div>
</div>


</body>
</html>