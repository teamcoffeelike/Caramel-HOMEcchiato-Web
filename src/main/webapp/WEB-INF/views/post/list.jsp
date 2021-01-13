<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.postList{ width: 600px; margin: 0 auto; }
.post-image { display: block; width: 600px; }

.userBox { 
	height: 70px; line-height: 70px;
	vertical-align: middle; padding: 10px;
	overflow: hidden;
	border: 1px solid #ccc;
}

.profile-image{
	width: 50px; height: 50px; 
	border-radius: 55px; margin-right: 10px;
	float: left;
}

.profile-name{
	width: 50px; height: 50px; 
	line-height: 50px; float: left;
}

.contentBox{
	border: 1px solid #ccc; 
	margin-bottom: 20px; padding: 10px;
	overflow: hidden; vertical-align: middle;
}

.content{
	width:580px; margin-top: 10px;
	overflow: hidden; float: left;
}

.moreContent, .reaction { color: #ccc; font-size: 0.8em; cursor: pointer;}
.postDate { color: rgb(117, 117, 117); font-size: 0.8em; }
#btnLike { cursor: pointer; font-size: 20px; }
</style>
<script type="text/javascript" src="js/readmore.min.js"></script>
<script type="text/javascript" src="js/post_list.js"></script>
</head>
<body>
	<div class="postList"></div>
	<span class="postEnd"></span>
</body>
</html>