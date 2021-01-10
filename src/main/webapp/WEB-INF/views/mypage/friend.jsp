<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>
<h3>친구찾기</h3>
<div class="container">
	<table>
		<tr>
			<td>친구아이디</td>
			<td><input type="text" id="searchName" name="name"></td>
		</tr>
		<tr>
			<td><a onclick="searchFriend()">검색</a></td>
		</tr>
	</table>
</div>
<div>
	<table id="result">
	
	</table>
</div>
</body>
<script type="text/javascript">


function searchFriend() {
	$.ajax({
		type: "post",
		url: "searchName",
		data: { name : $("#searchName").val() },
		success: function(response) {
			var result = JSON.parse(response);
			console.log(result.exists);
			if(result.exists == true) {
				alert("친구 찾기 성공!");
			}else {
				alert("친구 찾기 실패!");
			}
		}, error: function(req, text) {
			alert(text + " : " + req.status);
		}
	});
}
function getFriend(findName) {
	
}

</script>
</html>