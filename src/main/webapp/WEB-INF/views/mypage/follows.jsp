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
<script src="js/search_friend.js"></script>
</head>
<body>
<div class="searchContainer">
	<ul class="search">
		<li><input type="text" id="searchName" name="name" autocomplete="off" placeholder="닉네임을 입력하세요"></li>
		<li><div id="searchNameResult"></div></li>
	</ul>
</div>
<div class="container">
	<ul class="tabs">
		<li class="follow button on">팔로워</li>
		<li class="follow button">팔로잉</li>
	</ul>
	<div class="tab-content">
		<!-- 팔로우 리스트 -->
		<div class="follow on">
			<ul>
				<c:forEach var="u" items="${followers}">
					<li>
						<img src="${empty u.profileImage ? "imgs/profile.png" : u.profileImage }">
						<li class="followList-name">${u.name }</li>
						<a class="btnFollow" onclick="if(confirm('정말 삭제하시겠습니까?') ){ href='delete.no?id=${page.id}' }">삭제</a>
					</li>
				</c:forEach>
			</ul>
		</div>
		<!-- 팔로잉 리스트 -->
		<div class="follow">
			<ul>
				<c:forEach var="u" items="${followers}">
					<li>
						<img src="${empty u.profileImage ? "imgs/profile.png" : u.profileImage }">
						<li class="followList-name">${u.name }</li>
						<a class="btnFollow">팔로잉</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>
</body>
</html>