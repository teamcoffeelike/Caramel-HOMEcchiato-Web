<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/profile.css">
<script type="text/javascript" src="js/user_postlist.js"></script>
<script src="js/follow_button.js"></script>
<script type="text/javascript">
userId = ${data.id};

$(function(){
	registerFollowButton($(".btnFollow").toArray(),
            ${data.id},
			${data.followedByYou ? 'true' : 'false'});
});
</script>
</head>
<body>
	
<div id="mypage">	
	<div id="profile">
		<div class="imgBox">
			<label>
				<img class="profileImg" src="${profileImage}" />
			</label>
		</div>
		<section>
			<div>${data.name }</div>
			<div>${!empty data.motd ? data.motd : '' }</div>
			<c:if test="${loginUser.userId eq data.id}">
				<div class="btnSet">
					<a id="btnWrite" class="btn-empty" href="writePostView">글쓰기</a>
					<a id="btnProfile" class="btn-empty" href="settings">프로필 편집</a>
				</div>
			</c:if>
			<c:if test="${loginUser.userId ne data.id }">
				<div class="btnSet">
					<a id="btnFollow" class="btnFollow"><span class="text"></span></a>
				</div>
			</c:if>
		</section>
	</div>
	<!-- 포스트 -->
	<div id="post_grid">
		<div class="postList"></div>
		<span class="postEnd"></span>
	</div>
</div>
</body>
</html>