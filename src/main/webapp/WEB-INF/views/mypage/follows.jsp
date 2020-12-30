<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/follows.css">
<script type="text/javascript" src="js/follows.js"></script>
</head>
<body>
<div class="container">
	<ul class="tabs">
		<li class="follow button on">팔로우</li>
		<li class="follow button">팔로잉</li>
	</ul>
	<div class="tab-content">
		<!-- 팔로우 리스트 -->
		<div class="follow on">
			<ul>
				<c:forEach var="u" items="${followers}">
					<li>
						<img src="${u.profileImage }">
						<li>${u.name }</li>
					</li>
				</c:forEach>
			</ul>
		</div>
		<!-- 팔로잉 리스트 -->
		<div class="follow">
			<ul>
				<c:forEach var="u" items="${followers}">
					<li>
						<img src="${u.profileImage }">
						<li>${u.name }</li>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>
</body>
</html>