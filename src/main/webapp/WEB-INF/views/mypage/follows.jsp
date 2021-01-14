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
<script src="js/follow_button.js"></script>
<script type="text/javascript">
$(function(){
	<c:forEach var="e" items="${userMap}">
	registerFollowButton($(".id${e.key}").toArray(),
			             ${e.key},
			             ${e.value.followedByYou ? 'true' : 'false'});
	</c:forEach>
});
</script>
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
		<li class="follow-tab button on">팔로워</li>
		<li class="follow-tab button">팔로우</li>
	</ul>
	<div class="tab-content">
		<!-- 팔로워 리스트 -->
		<div class="follow-tab on">
			<ul>
				<c:forEach var="u" items="${followers}">
					<li class="follow-content">
						<img src="${empty u.profileImage ? "imgs/profile.png" : u.profileImage }">
						<span class="followList-name">${u.name }</span>
						<a class="btnFollow id${u.id}"><span class="text"></span></a>
					</li>
				</c:forEach>
			</ul>
		</div>
		<!-- 팔로우 리스트 -->
		<div class="follow-tab">
			<ul>
				<c:forEach var="u" items="${following}">
					<li class="follow-content">
						<img src="${empty u.profileImage ? "imgs/profile.png" : u.profileImage }">
						<span class="followList-name">${u.name }</span>
						<a class="btnFollow id${u.id}"><span class="text"></span></a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>
</body>
</html>