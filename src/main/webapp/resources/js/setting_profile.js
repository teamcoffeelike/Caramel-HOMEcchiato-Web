var profileImageChanged = false;
var nameChanged = false;
var motdChanged = false;

$(function(){
	/*다른 사진 선택하기*/
	$('.another-pic').click(function(){
		$('#popup, #popup-background').css('display', 'none');
		$('#img-attach').click();
	});

	/*현재 사진 삭제하기*/
	$('.delete-pic').click(function(){
		profileImageChanged = true;
		$('#popup, #popup-background').css('display', 'none');
		$('#img-attach').val('');
		$('#profile-img').attr('src', 'imgs/profile.png');
	});

	/*팝업창 취소*/
	$('.cancle,#popup-background').click(function(){
		$('#popup, #popup-background').css('display', 'none'); 
	});

	/*사진 미리보기 & 이미지 파일 체크*/
	$('#img-attach').on('change', function(){
		var attach = this.files[0];
		profileImageChanged = true;
		if (attach){
			var reader = new FileReader();
			reader.onload = function(e) {
				$('#profile-img').attr('src', e.target.result);
			}
			reader.readAsDataURL(attach);
		}
	});
	
	/*사진이 있는 경우 팝업창 띄우기*/
	$("#profile-img").on('click', function(){
		$('#popup,#popup-background').show();
	});

	$('#name').on("input", function(){
		nameChanged = true;
		$('#delete-name').css('display', this.value ? 'inline' : 'none');
	});
	$('#motd').on("input", function(){
		motdChanged = true;
		$('#delete-motd').css('display', this.value ? 'inline' : 'none');
	});

	$('#delete-name').on('click', function(){
		$('#name').val('');
		$('#delete-name').css('display', 'none');
	});
		
	$('#delete-motd').on('click', function(){
		$('#motd').val('');
		$('#delete-motd').css('display', 'none');
	});

	$("form[name=setProfile]").on('submit', function(){
		$("#profileImageChanged").val(profileImageChanged);
		if(!profileImageChanged){
			$("#img-attach").val("");
		}
		
		$("#nameChanged").val(nameChanged);
		if(!nameChanged){
			$("#name").val("");
		}
		
		$("#motdChanged").val(motdChanged);
		if(!motdChanged){
			$("#motd").val("");
		}
	});

	/*유효성검사*/
	var name_regex = /^[^\t\n]{1,40}$/;
	/*닉네임 유효성 검사*/
	$('#name').on("input", function(){
		if($('#name').val() == "") {
			$('#name_msg').html("");
		}else if(!name_regex.test($('#name').val())) {
			$('#name_msg').html("입력양식에 맞지 않습니다!");
		}else {
			$('#name_msg').html("");
		}
	});
	
});