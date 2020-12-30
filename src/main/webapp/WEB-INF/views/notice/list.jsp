<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
table {
	width: 80%;
	margin: 0 auto;
	border: 1px solid #bebebe;
}

table th, table td {
	border: 1px solid #bebebe;
	padding: 5px 10px;
}

h3 {
	text-align: center;
}

.w-px120 { width: 120px; }
.w-px100 { width: 100px; }

</style>
</head>
<body>
<h3>공지사항</h3>
<div id="list-top">
<div>
	<table>
		<tr>
			<th class="w-px120">제목</th>
			<th class="w-px100">작성일</th>
		</tr>
		<c:forEach items="${notices }" var="page">
		<tr>
			<td>${page.title }</td>
			<td>${page.writeDate }</td>
		</tr>
		</c:forEach>
	</table>
</div>
</div>
</body>
</html>