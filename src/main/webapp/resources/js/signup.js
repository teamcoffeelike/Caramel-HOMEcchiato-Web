$(document).ready(function(){
	//유효성검사식
	var regName = /^[가-힣]{2,5}$/;
	var regId = /^[a-z]\w{4,11}$/;
	var regPw = /^[a-z]\w{4,11}$/;
	var regEmail = /^[a-z]\w{4,11}@[a-z]{2,10}[\.][a-z]{2,3}[\.]?[a-z]{0,2}$/;
	var regTel = /^(010|011|016|017|018|019)\d{4}\d{4}$/;
	var idch = false;
	var name = $("#user_name");
	var id = $("#user_id");
	var pw1 = $("#user_pw");
	var pw2 = $("#pw_check");
	var email = $("#user_email");
	var tel = $("#user_tel");
	
	name.focus();	
			
	$("#user_name").keyup(function(){		
		if(name.val() == "") {
			$("#name_msg").html("");
		}else if(!regName.test(name.val())) {
			$("#name_msg").html("한글 2-5자리를 입력하세요!");
		}else {
			$("#name_msg").html("");
		}	
	});//name-keyup

	$("#user_id").keyup(function(){
		var idc= false;
		
		$.ajax({
			url: "loginCheck.json",
			async: false,
			success:function(data) {
				$(data).each(function(key, value) {
					if(id.val() == value.id) {
						idc = true;
					}
				});//each					
				}//success
		});//ajax
			
			if(id.val() == "") {
				$("#id_msg").html("");
			}else if(idc == true) {
				$("#id_msg").html("중복된 아이디입니다!");
			}else if(!regId.test(id.val())) {
				$("#id_msg").html("소문자로 시작해야 합니다!");
			}else {
				$("#id_msg").html("사용가능한 아이디입니다!");
			}	
		});//id-keyup

		$("#idck").click(function(){
			var idc= false;
			$.ajax({
				url: "loginCheck.json",
				async: false,
				success:function(data) {
					$(data).each(function(key, value) {
						if(id.val() == value.id) {
							idc = true;
						}
					});//each				
				}//success
			});//ajax
					
			if(id.val() == "") {
				alert("아이디를 입력해주세요!");
			}else if(idc == true) {
				alert("중복된 아이디입니다!");
			}else if(!regId.test(id.val())) {
				alert("소문자로 시작해야 합니다!");
			}else {
				alert("사용가능한 아이디입니다!");
				idch = true;
			}
		});//id_click
		
		$("#user_pw, #pw_check").on("keyup", function(){
			if(pw1.val() == "") {
				$("#pw_msg").html("");
			}else if(!regPw.test(pw1.val())) {
				$("#pw_msg").html("소문자로 시작해야 합니다!");
			}else {
				$("#pw_msg").html("사용가능한 비밀번호입니다!");
			}
						
			if(pw2.val() == "") {
				$("#pwc_msg").html("");
			}else if(!regPw.test(pw2.val())) {
				$("#pwc_msg").html("비밀번호가 일치하지 않습니다!");
			}else if(pw1.val() == pw2.val()) {
				$("#pwc_msg").html("비밀번호가 일치합니다!");
			}else {
				$("#pwc_msg").html("비밀번호가 일치하지 않습니다!");
			}
		});
		
		$("#user_email").keyup(function(){
			if(email.val() == "") {
				$("#email_msg").html("");
			}else if(!regEmail.test(email.val())) {
				$("#email_msg").html("이메일 입력 형식이 잘못되었습니다!");
			}else {
				$("#email_msg").html("");
			}
		});//on
			
		$("#user_tel").keyup(function() {		
			if(tel.val() == "") {
				$("#tel_msg").html("");
			}else if(!regTel.test(tel.val())) {
				$("#tel_msg").html("' - ' 생략한 11자리 숫자");
			}else {
				$("#tel_msg").html("");
			}		
		});//tell
		
		$("#register").submit(function() {
			if(!regName.test(name.val())) {
				alert("이름 입력 양식에 맞게 작성해주세요!");
				name.focus();
				return false;
			}else if(!regId.test(id.val())) {
				alert("아이디 입력 양식에 맞게 작성해주세요!");
				id.focus();
				return false;
			}else if(idch != true) {
				alert("Check 버튼을 눌러 중복을 확인하세요!");
				return false;					
			}else if(!regPw.test(pw1.val())) {
				alert("비밀번호 입력 양식에 맞게 작성해주세요!");
				pw1.val("");
				pw2.val("");
				pw1.focus();
				return false;
			}else if(pw1.val() != pw2.val()) {
				alert("비밀번호가 일치하지 않습니다!");
				pw2.val("");
				pw2.focus();
				return false;
			}else if(!regEmail.test(email.val())) {
				alert("이메일 입력 양식에 맞게 작성해주세요!");
				email.focus();
				return false;
			}else if(!regTel.test(tel.val())) {
				alert("번호 입력 양식에 맞게 작성해주세요!");
				tel.focus();
				return false;
			}else {
				alert(name.val() + "님, 회원가입을 환영합니다!");
				location.href = "index.html";
				return false;
			}
		});//submit

});//ready
