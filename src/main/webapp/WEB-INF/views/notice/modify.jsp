<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/noticeList.css">
<script type="text/javascript" src='js/mandatory_check.js'></script>
<style type="text/css">
table { margin: 20px auto; }
</style>
<body>
<div class="title">
	<h3>공지사항</h3>
	<span>Caramel HOMEcchiato 서비스의 오류, 장애, 기타 공지사항을 안내드립니다.</span>
</div>
<form method="post" action="update.no">
	<input type="hidden" name="id" value="${data.id}"/>
	<table>
	<tr>
		<th class="w-px200">제목</th>
		<td><input title="제목" class="mandatory" type="text" name="title" value="${data.title }"/></td>
	</tr>
	<tr>
		<th>내용</th>
		<td><textarea title="내용" class="mandatory" name="content">${data.content }</textarea></td>
	</tr>
	</table>
</form>
<div class="btnSet">
	<a class="btnSubmit" onclick="if( necessary() ) $('form').submit()">저장</a>
	<a class="btnCancel" href="javascript:history.go(-1)">취소</a>
</div>
</body>
</html>