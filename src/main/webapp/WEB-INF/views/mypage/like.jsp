<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/userBox.css">
<style type="text/css">
.postList{ width: 600px; margin: 0 auto; }
.postDetail{
	width: 600px;
	margin-bottom: 20px;
	box-sizing: content-box;
	border: 1px solid #ccc;
}

.post-image { display: block; width: 600px; }

.contentBox{
	overflow: hidden;
	vertical-align: middle;
	padding: 10px;
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