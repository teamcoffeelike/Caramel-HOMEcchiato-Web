<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/postDetail.css">
<script type="text/javascript">
	

</script>
</head>
<body>
	<div class="postDetail">
		<div class="userBox">
			<img class="profile-image" src="${profileImage}" />
			<a class='profile-name' href='profile?userId=${post.author.id}'>${post.author.name}</a>
			<c:if test="${loginUser.userId eq post.author.id}">
				<div class="btnSet1">
					<a class="btn-empty" onclick="$('form').submit();">수정</a>
					<a class="btn-empty" onclick="if(confirm('정말 삭제하시겠습니까?')){location='deletePost?id=${post.id }'}">삭제</a>
				</div>
			</c:if>
		</div>

		<img class='post-image' src='${postImage}'>

		<div class='contentBox'>
			<div class='content'>${fn:replace(post.text, crlf, '<br>')}</div>
			<div class='postDate'>${post.postDate}</div>
		</div>
	</div>

	<div class="btnSet2">
		<a class="btn-fill" href="allPostList">목록보기</a>
	</div>
	
	
<form method="post" action='modifyPost'>
	<input type='hidden' name='id' value='${post.id}' />
</form>
</body>
</html>