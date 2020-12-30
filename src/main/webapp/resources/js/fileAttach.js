/*업로드 될 파일이 이미지 파일인지 체크 함수*/
function isImage( filename ){
	var ext = filename.substring(filename.lastIndexOf('.')+1).toLowerCase();
	var imgs = ['jpg', 'png', 'bmp', 'jpeg', 'gif'];
	if( imgs.indexOf(ext) > -1 ) return true;
	else false;
}

/*사진 미리보기 & 이미지 파일 체크*/
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
/*사진이 있는 경우 팝업창 띄우기*/
}).on('click', function(){
	var attach = this.files[0];
	if(attach){
		$('#popup').show();
		$('#popup-background').show();
		$('#img-attach').attr('disabled', 'disabled');
	}
});
