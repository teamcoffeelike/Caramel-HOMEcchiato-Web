/* 로그인 처리 */
function go_login() {
	if($("#user_id").val() == "") {
		alert("이메일 또는 핸드폰번호를 입력하세요!");
		$("#user_id").focus();
		return;
	}else if($("#user_pw").val() == "") {
		alert("비밀번호를 입력하세요!");
		$("#user_pw").focus();
		return;
	}

	//user_id 값에 @가 포함된 경우(이메일)와 아닌 경우(핸드폰번호)
	if($("#user_id").val().indexOf("@") > -1) {
		$.ajax({

			type: "post",
			url: "api/loginWithEmail",
			data: { email:$("#user_id").val(), password:$("#user_pw").val() },
			success: function(response) {
				if(response.error) {
					alert("로그인 실패!");
				}else {
					alert("로그인 성공!");
					location.href="follows";
				}
			}, error: function(req, text) {
				alert(text + " : " + req.status);
			}
		});
	}else {
		$.ajax({

			type: "post",
			url: "api/loginWithPhoneNumber",
			data: { phoneNumber:$("#user_id").val(), password:$("#user_pw").val() },
			success: function(response) {
				if(response.error) {
					alert("로그인 실패!");
				}else {
					alert("로그인 성공!");
					location.href="follows";
				}
			}, error: function(req, text) {
				alert(text + " : " + req.status);
			}
		});
	}
	
}