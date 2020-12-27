/* 회원가입 처리*/

var regEmail = /^[a-z]\w{4,11}@[a-z]{2,10}[\.][a-z]{2,3}[\.]?[a-z]{0,2}$/;
var regPhone = /^(010|011|016|017|018|019)-\d{4}-\d{4}$/;

/* 이메일 회원가입 */
function join_email() {
	join_check();
	//$("form").submit();
}

/* 핸드폰번호 회원가입*/
function join_phone() {
	join_check();
	//$("form").submit();
}

/* 회원가입 검사 */
function join_check() {
	if(!item_check($("#join_name"))) return;
	if(!item_check($("#join_pw"))) return;
	if(!item_check($("#join_pw_ck"))) return;
	
}

function item_check(tag) {
	var data=join.tag_status(tag);
	if(data.code != "valid") {
		alert("회원가입 불가!\n" + data.desc);
		tag.focus();
		return false;
	}else return true;
}