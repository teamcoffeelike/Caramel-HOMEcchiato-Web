/*다른 사진 선택하기*/
$('.another-pic').click(function(){
	$('#popup, #popup-background').css('display', 'none');
	$('#img-attach').removeAttr('disabled');
	$('#img-attach').val('');
	$('#img-attach').click();
});

/*현재 사진 삭제하기*/
$('.delete-pic').click(function(){
	$('#popup, #popup-background').css('display', 'none');
	$('#img-attach').removeAttr('disabled');
	$('#img-attach').val('');
	$('#post-img').attr('src', "imgs/profile.png");
});

/*팝업창 취소*/
$('.cancle').click(function(){
	$('#popup, #popup-background').css('display', 'none'); 
	$('#img-attach').removeAttr('disabled');
});

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

/*이름, 상태메시지 한번에 지우기*/
if( $('#name').val() != "" ){
	$('#delete-name').css('display', 'inline');
}
if( $('#motd').val() != "" ){
	$('#delete-motd').css('display', 'inline');
}

$('#delete-name').on('click', function(){
	$('#name').val('');
	$('#delete-name').css('display', 'none');
});
	
$('#delete-motd').on('click', function(){
	$('#motd').val('');
	$('#delete-motd').css('display', 'none');
});

/*유효성검사*/
$(function(){
	var name_regex = /^[^\t\n]{1,40}$/;
	var password_regex = /^\S{3,63}$/;
	
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

	/*새 비밀번호, 새 비밀번호 확인 일치 여부 & */
	$('#newPw, #newPwConfirm' ).on("input", function(){
		if($('#newPw').val() == "") {
	   		$('#newPw_msg').html("");
	   	}else if(!password_regex.test($('#newPw').val())) {
	    	$('#newPw_msg').html("입력양식에 맞지 않습니다!");
	    }else {
	    	$('#newPw_msg').html("사용가능한 비밀번호입니다!");
	    }

		if($('#newPwConfirm').val() == "") {
	    	$('#newPwConfirm_msg').html("");
	    }else if($('#newPw').val() == $('#newPwConfirm').val()) {
	    	$('#newPwConfirm_msg').html("비밀번호가 일치합니다!").css('color', '#58a785');
	   	}else {
	    	$('#newPwConfirm_msg').html("비밀번호가 일치하지 않습니다!").css('color', 'red');
	   	}
	});
	
});