<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/writeQna.css">
</head>
<body>
<!-- 본인이 쓴 글인 경우 수정/삭제 가능 -->
<c:if test="${loginUser.userId eq data.writer }">
	<div class="btn">
		<a class="delete" onclick="if(confirm('정말 삭제하시겠습니까?')){location='delete.qna?id=${data.id }'}">삭제</a>
		<a class="modify" onclick="$('form').attr('action', 'modify.qna'); $('form').submit();">수정</a>
	</div>
</c:if>
<br/>
<table>
	<tr>
		<th class="w-px150">제목</th>
		<td>${data.title }</td>
 	</tr>
 	<tr>
 		<th class="w-px150">작성자</th>
 		<td>${data.name }</td>
 	</tr>
 	<tr>
 		<th>내용</th>
 		<td class="content">${fn:replace(data.content, crlf, '<br>')}</td>
 	</tr>
</table>
<!-- 댓글 입력창 -->
<div class="commentDiv">
	<div id="commentList" class="left">
		<jsp:include page="/WEB-INF/views/qna/comment/commentList.jsp" />
	</div>
	<div id="comment_regist">
		<textarea id="comment"></textarea>
		<span class="commentBtn"><a onclick="comment_regist()">댓글등록</a></span>
	</div>
	<div id="comment_list" class="left">
		
	</div>
</div>

<div class="btn">
	<a class="list" onclick="history.go(-1)">목록으로</a>
</div>




<form method="post" action='list.qna'>
<input type='hidden' name='id' value='${data.id}' />
<input type='hidden' name='id' value='${data.writeDate}' />

</form>
<script type="text/javascript">
function comment_regist() {
	if(${empty loginUser}) {
		alert("로그인 후 댓글 등록이 가능합니다!");
		return;
	}else if($("#comment").val().trim() == "") {
		alert("댓글 내용을 입력하세요!");
		$("#comment").val("");
		$("#comment").focus();
		return;
	}

	$.ajax({
		type: "post",
		url: "qna/comment/insert",
		data: { qnaId:${data.id}, content:$("#comment").val() },
		success: function(response) {
			console.log(response);
			if(response == 1) {
				alert("댓글이 등록되었습니다!");
				$("#comment").val("");
				commentList();
			}else if(reponse == 0) {
				alert("댓글 등록 실패!");
			}else {
				location = "list.qna";
			}
		}, error: function(req, text) {
			alert(text + " : " + req.status);
		}
	});
}

function commentList() {
	$.ajax({
		url: "qna/comment/${data.id}",
		success: function(response) {
			$("#commentList").html(response);
		}, error: function(req, text) {
			alert(text + " : " + req.status);
		}

	});
}
commentList();
</script>
</body>
</html>