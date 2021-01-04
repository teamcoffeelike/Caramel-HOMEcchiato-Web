<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/noticeList.css">
</head>
<body>
<!-- 관리자가 로그인한 경우 수정 가능 -->
<c:if test="">
	<div class="btn">
		<a class="modify">수정</a>
		<a class="delete">삭제</a>
	</div>
</c:if>
<h3>공지사항</h3>
<table>
<tr>
	<th class="w-px200">제목</th>
	<td>${data.title }</td>
</tr>
<tr>
	<th class="w-px200">내용</th>
	<td>${data.content }</td>
</tr>
</table>
<div class="btn">
	<a class="btnCancel" href="notice">목록</a>
	<a class="btnSubmit" href="modify.no?id=${data.id }">수정</a>
	<a class="btnCancel" onclick="if( confirm('정말 삭제하시겠습니까?') ){href='delete.no?id=${data.id }'}">삭제</a>
</div>
</body>
</html>