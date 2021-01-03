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
	width: 60%;
	margin: 0px auto;
	border: 1px solid #bebebe;
	border-collapse: collapse;
}

table th, table td {
	border: 1px solid #bebebe;
	padding: 5px 10px;
}

h3 {
	text-align: center;
	margin: 20px auto;
}

a.btnNew {
	text-align: center;
	padding: 5px 15px;
	color: #e67461;
	border: 1px solid #e67461;
	border-radius: 5px;
}

.w-px200 { width: 200px; }
.w-px80 { width: 80px; }

</style>
</head>
<body>
<h3>공지사항</h3>
<div id="list-top">
<div>
	<form method="post" action="notice">
	<%-- 
		<input type="hidden" name="page" value="1" />
		<ul>
			<li><select name="search" class="w-px80">
					<option value="all" ${page.search eq 'all' ? 'selected' : '' }>전체</option>
				</select>
			</li>
		</ul>
		 --%>
		 <ul>
		 	<li><a href="new" class="btnNew">글쓰기</a></li>
		 </ul>
	</form>
	<table>
		<tr>
			<th class="w-px200">제목</th>
			<th class="w-px80">작성자</th>
			<th class="w-px80">작성일</th>
		</tr>
		<c:forEach items="${notices }" var="page">
		<tr>
			<td><a href="detail.no?id=${page.id }">${page.title }</a></td>
			<%-- <td>${page.writer }</td> --%>
			<td>${page.writeDate }</td>
		</tr>
		</c:forEach>
	</table>
</div>
</div>
</body>
</html>