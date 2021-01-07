/* 이메일 회원가입 */
function joinWithEmail() {
	if($("#joinEmailName").val() == "") {
		alert("닉네임을 입력해주세요!");
		return;
	}else if($("#joinUserEmail").val() == "") {
		alert("이메일을 입력해주세요!");
		return;
	}else if($("#joinEmailPassword").val() == "") {
		alert("비밀번호를 입력해주세요!");
		return;
	}else if($("#joinEmailPasswordCheck").val() == "") {
		alert("비밀번호를 입력해주세요!");
		return;
	}else if(!$("#joinEmailName").hasClass("checked")) {
		alert("닉네임을 다시 입력해주세요!")
		return;
	}else if(!$("#joinUserEmail").hasClass("checked")) {
		alert("이메일 중복체크 후 회원가입이 가능합니다!");
		return;
	}else if(!$("#joinEmailPassword").hasClass("checked")) {
		alert("비밀번호를 다시 입력해주세요!");
		return;
	}else if(!$("#joinEmailPasswordCheck").hasClass("checked")) {
		alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요!");
		return;
	}else {
		$("#joinWithEmailForm").submit();
		alert($("#joinEmailName").val() + "님, 회원가입을 축하합니다!\n로그인 후 이용해주세요!");
	}
}	
	
/* 핸드폰번호 회원가입*/
function joinWithPhone() {
	if($("#joinPhoneName").val() == "") {
		alert("닉네임을 입력해주세요!");
		return;
	}else if($("#joinUserPhone").val() == "") {
		alert("이메일을 입력해주세요!");
		return;
	}else if($("#joinPhonePassword").val() == "") {
		alert("비밀번호를 입력해주세요!");
		return;
	}else if($("#joinPhonePasswordCheck").val() == "") {
		alert("비밀번호를 입력해주세요!");
		return;
	}else if(!$("#joinPhoneName").hasClass("checked")) {
		alert("닉네임을 다시 입력해주세요!")
		return;
	}else if(!$("#joinUserPhone").hasClass("checked")) {
		alert("핸드폰번호 중복체크 후 회원가입이 가능합니다!");
		return;
	}else if(!$("#joinPhonePassword").hasClass("checked")) {
		alert("비밀번호를 다시 입력해주세요!");
		return;
	}else if(!$("#joinPhonePasswordCheck").hasClass("checked")) {
		alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요!");
		return;
	}else {
		$("#joinWithPhoneForm").submit();
		alert($("#joinPhoneName").val() + "님, 회원가입을 축하합니다!\n로그인 후 이용해주세요!");
	}
}

/* 회원가입 처리*/
$(function(){
	var name_regex = /^[^\t\n]{1,40}$/;
	var email_regex = /^[^@]+@[^@]+$/;
	var phone_number_regex = /^\d{3}[ -]?\d{4}[ -]?\d{4}$/;
	var password_regex = /^\S{3,63}$/;
	
	/* 이메일 가입 팝업 */
	$("#joinEmail").on("click", function(){
		$(".popupEmail").show();
		$(".dim").show();
		$("#joinEmailName").focus();
	});

	/* 핸드폰번호 가입 팝업 */
	$("#joinPhone").on("click", function(){
		$(".popupPhone").show();
		$(".dim").show();
		$("#joinPhoneName").focus();
	});
	
	registerNameValidation("joinEmail");
	registerNameValidation("joinPhone");

	function registerNameValidation(form){
	    //닉네임 유효성 검사
	    $("#"+form+"Name").on("input", function(){
	    	if($("#"+form+"Name").val() == "") {
	    		$("#"+form+"Name_msg").html("");
	    	}else if(!name_regex.test($("#"+form+"Name").val())) {
	    		$("#"+form+"Name_msg").html("입력양식에 맞지 않습니다!");
				$("#"+form+"Name").removeClass("checked");
	    	}else {
				$("#"+form+"Name").addClass("checked");
	    		$("#"+form+"Name_msg").html("");
	    	}
	    });
		
		//이메일 유효성 검사
		$("#joinUserEmail").on("input", function() {
			if($("#joinUserEmail").val() == "") {
	    		$("#"+form+"Check_msg").html("");
	    	}else if(!email_regex.test($("#joinUserEmail").val())) {
	    		$("#"+form+"Check_msg").html("입력양식에 맞지 않습니다!");
	    	}else {
				$("#joinUserEmail").removeClass("checked");
	    		$("#"+form+"Check_msg").html("중복확인 버튼을 눌러주세요!");
	    	}
		});
		
		//핸드폰번호 유효성 검사
		$("#joinUserPhone").on("input", function() {
			if($("#joinUserPhone").val() == "") {
	    		$("#"+form+"Check_msg").html("");
	    	}else if(!phone_number_regex.test($("#joinUserPhone").val())) {
	    		$("#"+form+"Check_msg").html("입력양식에 맞지 않습니다!");
				$("#joinUserPhone").removeClass("checked");
	    	}else {
	    		$("#"+form+"Check_msg").html("중복확인 버튼을 눌러주세요!");
	    	}
		});

		//비밀번호 및 비밀번호 확인 유효성 검사
		$("#" + form + "Password, #" + form+ "PasswordCheck").on("input", function(){
			if($("#"+form+"Password").val() == "") {
	    		$("#"+form+"Password_msg").html("");
	    	}else if(!password_regex.test($("#"+form+"Password").val())) {
	    		$("#"+form+"Password_msg").html("입력양식에 맞지 않습니다!");
	    		$("#"+form+"Password").removeClass("checked");
	    	}else {
	    		$("#"+form+"Password_msg").html("사용가능한 비밀번호입니다!");
				$("#"+form+"Password").addClass("checked");
	    	}

			if($("#"+form+"PasswordCheck").val() == "") {
	    		$("#"+form+"PasswordCheck_msg").html("");
	    	}else if($("#"+form+"Password").val() == $("#"+form+"PasswordCheck").val()) {
	    		$("#"+form+"PasswordCheck_msg").html("비밀번호가 일치합니다!");
				$("#"+form+"PasswordCheck").addClass("checked");
	    	}else {
	    		$("#"+form+"PasswordCheck_msg").html("비밀번호가 일치하지 않습니다!");
				$("#"+form+"PasswordCheck").removeClass("checked");
	    	}
		});
		
		//닫기 누르면 내용 삭제
		$(".back").on("click", function(){
			$(".popupEmail").hide();
			$(".popupPhone").hide();
			$("#" + form + "Name").val("");
			$("#joinUserEmail").val("");
			$("#joinUserPhone").val("");
			$("#" + form + "Password").val("");
			$("#"+form+"PasswordCheck").val("");
			$("#"+form+"Check_msg").empty();
			$("#"+form+"Password_msg").empty();
			$("#"+form+"PasswordCheck_msg").empty();
			$(".dim").hide();
		});
	}
	
	//이메일 체크 버튼 눌렀을때
	$("#joinEmailcheck").on("click", function(){
		emailCheck();
	});

	function emailCheck() {
		if($("#joinUserEmail").hasClass("joinUserEmailCheked")) return;
		if(!email_regex.test($("#joinUserEmail").val())) {
			$("#joinEmailCheck_msg").html("양식에 맞게 작성 후 체크해주세요!");
			$("#joinUserEmail").focus();
			return;
		}
		
		$.ajax({
			type: "post",
			url: "api/emailExists",
			data: { email:$("#joinUserEmail").val() },
			success:function(response) {
				if(response.exists) {
					$("#joinEmailCheck_msg").html("이미 사용중인 이메일입니다!");
				}else {
					$("#joinEmailCheck_msg").html("사용가능한 이메일입니다!");
					$("#joinUserEmail").addClass("checked");
				}
			}, error:function(req, text) {
				alert(req + " : " + text);
			}
		});
	}



	//핸드폰번호 체크 버튼 눌렀을때
	$("#joinPhoneCheck").on("click", function(){
		phoneNumberExists();
	});

	//핸드폰 중복확인
	function phoneNumberExists() {
		if(!phone_number_regex.test($("#joinUserPhone").val())) {
			$("#joinPhoneCheck_msg").html("양식에 맞게 작성 후 체크해주세요!");
			$("#joinUserPhone").focus();
			return;
		}
		
		$.ajax({
			type: "post",
			url: "api/phoneNumberExists",
			data: { phoneNumber:$("#joinUserPhone").val() },
			success:function(response) {
				if(response.exists) {
					$("#joinPhoneCheck_msg").html("이미 사용중인 번호입니다!");
				}else {
					$("#joinPhoneCheck_msg").html("사용가능한 번호입니다!");
					$("#joinUserPhone").addClass("checked");
				}

			}, error:function(req, text) {
				alert(req + " : " + text);
			}
		});
	}
});


