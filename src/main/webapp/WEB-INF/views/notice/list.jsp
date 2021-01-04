<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/noticeList.css">
</head>
<body>
<h3>공지사항</h3>
<div id="list-top">
<div>
<%-- 	<form method="post" action="notice">
		<input type="hidden" name="page" value="${page.currentPage}" />
		<ul>
			<li>
				<select name="search" class="w-px80">
					<option value="all" ${page.search eq 'all' ? 'selected' : '' }>전체</option>
					<option value="title" ${page.search eq 'title' ? 'selected' : '' }>제목</option>
					<option value="content" ${page.search eq 'content' ? 'selected' : '' }>내용</option>
				</select>
			</li>
			<li><input type="text" name="keyword" class="w-px300" value="${page.keyword }"></li>
			<li><a onclick="$('form').submit()" class="btn">검색</a></li>
		</ul>
	</form> --%>
	<table>
		<tr>
			<th class="w-px200">제목</th>
			<th class="w-px80">작성일</th>
		</tr>
		<c:forEach items="${notices }" var="page">
		<tr>
			<td><a href="detail.no?id=${page.id }">${page.title }</a></td>
			<td>${page.writeDate }</td>
		</tr>
		</c:forEach>
	</table>
<div>
	<a href="new" class="btnNew">글쓰기</a>	
</div>
</div>
</div>
</body>
</html>