<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src='js/mandatory_check.js'></script>
<style type="text/css">
table {
	width: 60%;
	margin: 0px auto;
	border: 1px solid #bebebe;
	border-collapse: collapse;
}

table th, table td {
	border: 1px solid #bebebe;
	padding: 5px 10px;
}

h3 { text-align: center; margin: 20px auto; }

input { height:22px; padding:3px 5px; font-size:15px; }
input[name=title] { width:calc(100% - 14px); }
textarea[name=content] { width:calc(100% - 6px); height:150px; resize:none; }
select { height: 32px; }
textarea { resize: none; }

.btnSubmit, .btnCancel {
	padding: 5px 15px;
	color: #e67461;
	border: 1px solid #e67461;
	border-radius: 5px;
	margin-top: 20px;
}

.w-px200 { width: 200px; }
.w-px80 { width: 80px; }
</style>
</head>
<body>
<h3>공지글쓰기</h3>
<form method="post" action="insert">
<table>
<tr><th class="w-px200">제목</th>
	<td><input title="제목" class="mandatory" type="text" name="title"/></td>
</tr>
<tr><th>작성자</th>
	<td>${page.writer }</td>
</tr>
<tr><th>내용</th>
	<td><textarea title="내용" class="mandatory" name="content"></textarea></td>
</tr>
</table>
</form>
<div>
<a class="btnSubmit" onclick="if( necessary() ) $('form').submit()">저장</a>
<a class="btnCancel" href="notice">취소</a>
</div>
</body>
</html>