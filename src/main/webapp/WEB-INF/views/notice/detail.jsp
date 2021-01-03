<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>공지사항</h3>
<table>
<tr><th>제목</th>
	<td>${page.title }</td>
</tr>
<%-- <tr><th>작성자</th>
	<td>${page.title }</td>
</tr> --%>
<tr><th>내용</th>
	<td>${page.content }</td>
</tr>
<tr><th>작성일</th>
	<td>${page.writeDate }</td>
</tr>
</table>
</body>
</html>