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
<h3>���Ǳ� ����</h3>
	<form method="post" action="insert.qna">
	<input type="hidden" name="id" value="${loginUser.userId }" />
		<table>
			<tr>
				<th class="w-px150">����</th>
				<td><input type="text" name="title" title="����" class="mandatory"/></td>
			</tr>
			<tr>
				<th>����</th>
				<td><textarea name="content" title="����" class="mandatory"></textarea></td>
			</tr>		
		</table>

	<div class="btn">
            <a class="save" onclick="if( necessary() ){ $('form').submit() }">����</a>
            <a href="qna">���</a>
    </div>
<script type="text/javascript" src='js/mandatory_check.js'></script>
	</form>
</body>
</html>