<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/writeQna.css">
</head>
<body>
<h3>문의글 수정</h3>
<form method="post" action="update.qna" accept-charset="utf-8">
<input type="hidden" name="id" value="${data.id }" />
<table>
	<tr>
		<th class="w-px150">제목</th>
		<td><input type="text" name="title" value="${data.title }"/></td>
 	</tr>
 	<tr>
 		<th class="w-px150">작성자</th>
 		<td>${data.name }</td>
 	</tr>
 	<tr>
 		<th>내용</th>
 		<td><textarea name="content">${data.content}</textarea></td>
 	</tr>
</table>
<div class="btn">
	<a onclick="if(necessary()) {$('form').submit()}">저장</a>
	<a onclick="history.go(-1)">취소</a>
</div>
</form>
<script type="text/javascript" src="js/mandatory_check.js"></script>
</body>
</html>