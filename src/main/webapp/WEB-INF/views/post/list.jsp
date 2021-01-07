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
						$(".postList").append("<div class='postBox'><div class='userBox'><img class='profile-image' src='" + e.author.profileImage + "' /><span class='profile-name'>" + e.author.name + "</span></div>")
									  .append("<img class='post-image' src='" + e.image + "'>")
									  .append("</div>");
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