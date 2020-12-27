/* 회원가입시 입력데이터 유호성 판단 */
var space = /\s/g;
var join = {
	common: {
		space: { code:"invalid", desc: "공백없이 입력하세요!" },
		max: { code:"invalid", desc: "최대 15자 이하로 입력하세요!" },
		min: { code:"invalid", desc: "최소 5자 이상 입력하세요!" }	
	},
	name: {
		empty: { code:"invalid", desc: "닉네임을 입력하세요!" },
		valid : { code:'valid', desc:'사용가능한 닉네임입니다!' },
		invalid : { code:'invalid', desc:'사용불가능한 닉네임입니다. 다시 입력해주세요!' }
	},
	email: {
		empty: { code:"invalid", desc: "이메일을 입력하세요!" },
		invalid: { code:"invalid", desc:"유효하지 않은 이메일입니다!" },
		valid: { code:'valid', desc: '중복확인을 해야합니다!' },
		usable: { code:'valid', desc:'사용 가능한 이메일입니다'},
		unusable: { code:'invalid', desc:'이미 사용중인 이메일입니다'}
	},
	phone: {
		empty: { code:"invalid", desc: "핸드폰번호를 입력하세요!" },
		invalid: { code:"invalid", desc:"유효하지 않은 번호입니다!" },
		valid: { code:'valid', desc: '중복확인을 해야합니다!' },
		usable: { code:'valid', desc:'사용 가능한 번호입니다'},
		unusable: { code:'invalid', desc:'이미 사용중인 번호입니다'}
	},
	pw : {
		empty: { code:"invalid", desc: "비밀번호를 입력하세요!" },
		invalid : { code:'invalid', desc: '영문 대/소문자, 숫자만 입력하세요' },
		valid : { code:'valid', desc: '사용가능한 비밀번호 입니다'},
		upperLack: { code:'invalid', desc: '영문 대문자를 포함해야 합니다' },
		lowerLack: { code:'invalid', desc: '영문 소문자를 포함해야 합니다' },
		digitLack: { code:'invalid', desc: '숫자를 포함해야 합니다' },
		equal: { code:'valid', desc:'비밀번호가 일치합니다' },
		notEqual: { code:'invalid', desc:'비밀번호가 일치하지 않습니다' } 
	},
	tag_status: function(tag) {
		var data=tag.val();
		tag = tag.attr("name");
		
		if(tag=="name"){
			return this.name_status(data);
		}else if(tag=="password") {
			return this.pw_status(data);
		}else if(tag=="password_ck") {
			return this.pw_ck_status(data);
		}else if(tag=="email") {
			return this.email_status(data);
		}else if(tag=="phone") {
			return this.phone_status(data);
		}
	},
	name_status: function(name) {
		if(name == "") return this.name.empty;
		else if(name.match(space)) return this.common.space;
		else if(name.length < 5) return this.common.min;
		else if(name.length > 15) return this.common.max;
		else return this.name.valid;
	},
	pw_status: function(password) {
		if(password == "") return this.pw.empty;
		else if(password.match(space)) return this.common.space;
		else if(password.length < 5) return this.common.min;
		else if(password.length > 15) return this.common.max;
		else return this.pw.valid;
	},
	pw_ck_status: function(password_ck) {
		if(password_ck == "") return this.pw.empty;
		else if(password_ck == $("#join_pw").val()) return this.pw.equal;
		else return this.pw.notEqual;
	}
	
	
	
	
}