<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/writeQna.css">
</head>
<body>
<!-- 본인이 쓴 글인 경우 수정/삭제 가능 -->
<c:if test="${loginUser.userId eq data.writer }">
	<div class="btn">
		<a class="delete" onclick="if(confirm('정말 삭제하시겠습니까?')){location='delete.qna?id=${data.id }'}">삭제</a>
		<a class="modify" onclick="$('form').attr('action', 'modify.qna'); $('form').submit();">수정</a>
	</div>
</c:if>
<br/>
<table>
	<tr>
		<th class="w-px150">제목</th>
		<td>${data.title }</td>
 	</tr>
 	<tr>
 		<th class="w-px150">작성자</th>
 		<td>${data.name }</td>
 	</tr>
 	<tr>
 		<th>내용</th>
 		<td>${fn:replace(data.content, crlf, '<br>')}</td>
 	</tr>
</table>
<div class="btn">
	<a class="list" href="qna">목록으로</a>
</div>




<form method="post" action='qna'>
<input type='hidden' name='id' value='${data.id}' />
</form>
</body>
</html>