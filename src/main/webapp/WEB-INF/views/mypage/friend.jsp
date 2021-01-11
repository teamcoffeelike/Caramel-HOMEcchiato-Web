<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="js/search_friend.js"></script>
</head>
<body>
<h3>친구찾기</h3>
<div class="container">
	<table>
		<tr>
			<td>친구아이디</td>
			<td>
				<input type="text" id="searchName" name="name">
				<div id="searchNameResult"></div>
			</td>
		</tr>
	</table>
</div>
</body>

</html>