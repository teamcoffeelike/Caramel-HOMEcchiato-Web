<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

	$(function(){
		fetchPost();
	});

	var postId;
	
	function fetchPost(){
		$.ajax({
			url: "api/post",
			type: "get",
			data: { "id":  },
			dataType: "json",
			success: function(data){
				
			}, error: function(req, text){
				alert(text+':'+req.status);
			}
		});
	}
</script>
</head>
<body>
	<div class="postDetail">
	</div>
</body>
</html>