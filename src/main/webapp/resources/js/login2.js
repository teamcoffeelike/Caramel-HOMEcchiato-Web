$(document).ready(function() {
		
	$("#user_id").focus();
	document.member.onsubmit = function() {
		var id = document.getElementById("user_id");
		var pw = document.getElementById("user_pw");
		
		if(!id.value) {
			alert("아이디를 입력해주세요!");
			$("id").focus();
			return false;
		}//if-id
		
		if(!pw.value) {
			alert("비밀번호를 입력해주세요!");
			$("pw").focus();
			return false;
		}
		
		var idCheck = false;
		
		$.ajax({
			
			url : "loginCheck.json",
			async: false,
			success: function(result) {
				$(result).each(function(key, value) {
					
					if(id.value == value.id && pw.value == value.pw) {
						idCheck = true;
					}				
				});//each
			}//success
		});//ajax
		
		if(idCheck == true) {
			alert(id.value + "님, 환영합니다!");
			id.vlaue = "";
			pw.value = "";
			location.href = "index.html";
			return false;
		}else {
			alert("아이디나 비밀번호를 다시 확인해주세요!");
			id.value = "";
			pw.value = "";
			id.focus();
			return false;
		}
		
	};//onsubmit
	
	}); //ready