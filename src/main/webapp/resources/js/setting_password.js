$(function(){
	var password_regex = /^\S{3,63}$/;
	/*새 비밀번호, 새 비밀번호 확인 일치 여부 & */
	$('#newPassword, #newPasswordConfirm' ).on("input", function(){
		if($('#newPassword').val() == "") {
			$('#newPassword_msg').html("");
		}else if(!password_regex.test($('#newPassword').val())) {
			$('#newPassword_msg').html("입력양식에 맞지 않습니다!");
		}else {
			$('#newPassword_msg').html("사용가능한 비밀번호입니다!");
		}

		if($('#newPasswordConfirm').val() == "") {
			$('#newPasswordConfirm_msg').html("");
		}else if($('#newPassword').val() == $('#newPasswordConfirm').val()) {
			$('#newPasswordConfirm_msg').html("비밀번호가 일치합니다!").css('color', '#67432a');
		}else {
			$('#newPasswordConfirm_msg').html("비밀번호가 일치하지 않습니다!").css('color', 'red');
		}
	});
});