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
<div class="text">
	<h3>Q&A 게시판</h3>
    <span>게시판에 글을 남겨주시면 자세히 답변해 드리겠습니다. 문의 내용에 개인정보가 노출되지 않도록 유의해주세요!</span>
</div>
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
	<a onclick="if(necessary()) {$('form').submit()}" class="save">저장</a>
	<a onclick="history.go(-1)" class="cancel">취소</a>
</div>
</form>
<script type="text/javascript" src="js/mandatory_check.js"></script>
</body>
</html>