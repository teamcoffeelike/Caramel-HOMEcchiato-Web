/* 회원가입 처리*/
$(function(){
	var space = /\s/g;
	var name_regex = /^[^\t\n]{1,40}$/;
	var email_regex = /^[^@]+@[^@]$/;
	var phone_number_regex = /^\d{3}[ -]?\d{4}[ -]?\d{4}$/;
	var password_regex = /^\S{3,63}$/;
	
	//테스트용
	var regName = /^[가-힣]{2,5}$/;
	var regPw = /^[a-z]\w{4,11}$/;

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
	
	$(".back").on("click", function(){
		$(".popupEmail").hide();
		$(".popupPhone").hide();
		$(".dim").hide();
	});

	/* 이메일 회원가입 */
	function joinWithEmail() {
		//$("form").submit();
	}
	
	/* 핸드폰번호 회원가입*/
	function joinWithPhone() {
		//$("form").submit();
	}
	
	registerNameValidation("joinEmail");
	registerNameValidation("joinPhone");

	function registerNameValidation(form){
	    //닉네임 유효성 검사
	    $("#"+form+"Name").on("input", function(){
	    	if($("#"+form+"Name").val() == "") {
	    		$("#"+form+"Name_msg").html("");
	    	}else if(!regName.test($("#"+form+"Name").val())) {
	    		$("#"+form+"Name_msg").html("입력양식에 맞지 않습니다!");
	    	}else {
	    		$("#"+form+"Name_msg").html("");
	    	}
	    });
		
		
		
		
		//비밀번호 및 비밀번호 확인 유효성 검사
		$("#" + form + "Password, #" + form+ "PasswordCheck").on("input", function(){
			if($("#"+form+"Password").val() == "") {
	    		$("#"+form+"Password_msg").html("");
	    	}else if(!regPw.test($("#"+form+"Password").val())) {
	    		$("#"+form+"Password_msg").html("입력양식에 맞지 않습니다!");
	    	}else {
	    		$("#"+form+"Password_msg").html("사용가능한 비밀번호입니다!");
	    	}

			if($("#"+form+"PasswordCheck").val() == "") {
	    		$("#"+form+"PasswordCheck_msg").html("");
	    	}else if($("#"+form+"Password").val() == $("#"+form+"PasswordCheck").val()) {
	    		$("#"+form+"PasswordCheck_msg").html("비밀번호가 일치합니다!");
	    	}else {
	    		$("#"+form+"PasswordCheck_msg").html("비밀번호가 일치하지 않습니다!");
	    	}
		});
		
	}
	
	//이메일 유효성 검사
	$("#joinEmailcheck").on("click", function(){
		emailExists();
	});

	function emailExists() {
		$.ajax({
			type: "post",
			url: "api/emailExists",
			data: { email:$("#joinEmail").val() },
			success:function(response) {
				console.log(response);
			}, error:function(req, text) {
				alert(req + " : " + text);
			}
		});
	}

});


