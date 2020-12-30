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
	
<div id="mypage">	
	<div id="profile">
		<div class="imgBox">
			<label>
				<img class="profileImg" src="imgs/profile.png" />
			</label>
		</div>
		<section>
			<div>${data.name }닉네임</div>
			<div>${data.motd }상태메세지</div>
			<c:if test="${loginUser.userId eq data.id}">
				<div class="btnSet">
					<a id="btnWrite" class="btn-empty" href="writePostView">글쓰기</a>
					<a id="btnProfile" class="btn-empty" href="mymodify">프로필 편집</a>
				</div>
			</c:if>
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
</div>
</body>
</html>