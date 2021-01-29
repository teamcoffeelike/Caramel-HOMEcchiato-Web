<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/postDetail.css">
<link rel="stylesheet" href="css/userBox.css">
</head>
<body>
	<div class="postDetail">
		<div class="top">
			<div class="userBox">
				<img class="profile-image" src="${post.author.profileImage}" />
				<a class='profile-name' href='profile?userId=${post.author.id}'>${post.author.name}</a>
			</div>
			<c:if test="${loginUser.userId eq post.author.id}">
				<div class="btnSet1">
					<a class="btn-empty" onclick="$('form').submit();">수정</a>
					<a class="btn-empty" onclick="if(confirm('정말 삭제하시겠습니까?')){location='deletePost?id=${post.id }'}">삭제</a>
				</div>
			</c:if>
		</div>

		<img class='post-image' src='${post.image}'>

		<div class='contentBox'>
			<a class='btnLike${post.likedByYou ? " liked" : ""}'><i class="${post.likedByYou ? "fas" : "far"} fa-heart"></i>
				<span class='likeCount'>${post.likes}</span
			></a>
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

<script>
$(function(){
	let likeCount = $(".likeCount");

	$(".btnLike").on("click", function(){
		let like;
		
		if(!$(this).hasClass("liked")){
			/* 좋아요 누를 때 */
			$(this).addClass("liked");
			likeCount.text(parseInt(likeCount.text())+1);
			$(".fa-heart").attr("data-prefix", "fas");
			like = true;
		}else{
			/* 좋아요 취소할 때 */
			$(this).removeClass("liked");
			likeCount.text(parseInt(likeCount.text())-1);
			$(".fa-heart").attr("data-prefix", "far");
			like = false;
		}

		$.ajax({
			url: "api/likePost",
			type: "post",
			data: { "post": ${post.id}, "like": like },
			success: function(data){
				if(data.error){
					alert("Error: "+data.error);
				}
			}, error: function(req, text){
				alert(text+':'+req.status);
			}
		});
	});
});
</script>
</body>
</html>