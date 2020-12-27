<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/profile.css">
</head>
<body>
	
	<div id="header">
		<div><img class="profileImg" src="" />프로필 사진</div>
		<section>
			<div>${data.name }</div>
			<div>${data.motd }</div>
		</section>
	</div>
	
	<!-- 포스트 -->
	<div id="post_grid">
		<ul>
			<c:forEach items="">
				<li onclick=""></li>
			</c:forEach>	
		</ul>
	</div>
</body>
</html>