<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" href="css/writeQna.css">
</head>
<body>
<h3>문의글 쓰기</h3>
	<form method="post" action="insert.qna">
	<input type="hidden" name="id" value="${loginUser.userId }" />
		<table>
			<tr>
				<th class="w-px150">제목</th>
				<td><input type="text" name="title" title="제목" class="mandatory"/></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="content" title="내용" class="mandatory"></textarea></td>
			</tr>		
		</table>

	<div class="btn">
            <a class="save" onclick="if( necessary() ){ $('form').submit() }">저장</a>
            <a href="qna">취소</a>
    </div>
<script type="text/javascript" src='js/mandatory_check.js'></script>
	</form>
</body>
</html>