<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/postList.css">
<script type="text/javascript">

function scrolled(y){
	let scrollBottom = $(window).scrollTop()+$(window).height();
	const MARGIN = 500;

	return scrollBottom+MARGIN >= y;
}

var ajaxSent = false;
var oldest;

function fetchPost(){
	if(!ajaxSent){
		ajaxSent = true;
		$.ajax({
			url: "api/recentPosts",
			type: "get",
			data: { "since": oldest },
			dataType: "json",
			success: function(data){
				console.log("Received "+data);
				ajaxSent = false;
				if(data.error){
					alert("Error: "+data.error);
					return;
				}
				if(data.posts&&data.posts.length){
					for(e of data.posts){
						$(".postList").append("<div class='userBox'><img class='profile-image' src='" + e.author.profileImage + "' /><a class='profile-name' href='profile?userId=" + e.author.id + "'>" + e.author.name + "</a></div>")
									  .append("<a href='post?id=" + e.id + "'><img class='post-image' src='" + e.image + "'></a>")
									  .append("<div class='contentBox'><div class='content'>" + e.text + "</div><span class='moreContent'>더보기..</span>" //TODO 더보기 처리
									  +"<div class='postDate'>" + e.postDate + "</div><span class='reaction'>댓글보기</span></div>"); //TODO 댓글보기 toggle로 처리?, 포스트 날짜 변환
					}
					let lastData = data.posts[data.posts.length-1];
					oldest = lastData.postDate;
				}
			}, error: function(req, text){
				ajaxSent = false;
				alert(text+':'+req.status);
			}
		});
	}
}

$(function(){
	$(window).on("scroll", function(){
		if(scrolled($("postEnd").offset().top)) fetchPost();
	});
	fetchPost();
});


</script>
</head>
<body>
	<div class="postList"></div>
	<span class="postEnd"></span>
</body>
</html>