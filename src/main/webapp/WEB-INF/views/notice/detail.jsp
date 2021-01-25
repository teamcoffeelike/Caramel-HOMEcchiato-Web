<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/noticeList.css">
</head>
<body>
<div class="title">
	<h3>공지사항</h3>
	<span>Caramel HOMEcchiato 서비스의 오류, 장애, 기타 공지사항을 안내드립니다.</span>
</div>
<div class="btnModifyDelete">
	<c:if test="${loginUser.getUserId() eq '1' }">
		<a class="btnModify" href="modify.no?id=${data.id}">수정</a>
		<a class="btnDelete" onclick="if( confirm('정말 삭제하시겠습니까?') ){href='delete.no?id=${data.id }'}">삭제</a>
	</c:if>
</div>
<table style="width: 80%;">
<tr>
	<td class="w-px200" style="font-weight: bold;">${data.title }</td>
</tr>
<tr>
	<td class="w-px200" style="padding: 50px 0;">${fn:replace(data.content, crlf, '<br>')}</td>
</tr>
</table>
<div class="btnSet">
	<a class="btnCancel" href="noticeBack?from=${data.id}
			<c:if test="${!empty search}">&search=${search}</c:if>
			<c:if test="${!empty keyword}">&keyword=${keyword}</c:if>"
	>목록으로</a>
</div>
</body>
</html>