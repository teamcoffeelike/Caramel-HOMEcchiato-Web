<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/writeQna.css">
</head>
<body>
<div class="text">
	<h3>Q&A 게시판</h3>
    <span>게시판에 글을 남겨주시면 자세히 답변해 드리겠습니다. 문의 내용에 개인정보가 노출되지 않도록 유의해주세요!</span>
</div>
	<form method="post" action="insert.qna">
	<input type="hidden" name="writer" value="${loginUser.userId }" />
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
            <a class="cancel" href="list.qna">취소</a>
    </div>
<script type="text/javascript" src='js/mandatory_check.js'></script>
	</form>
</body>
</html>