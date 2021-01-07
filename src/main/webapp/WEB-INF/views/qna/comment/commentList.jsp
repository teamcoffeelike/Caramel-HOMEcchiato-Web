<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:forEach items="${list }" var="data">
<div data-id=${data.id}>
	<span class="name">${data.name }</span>[${data.writeDate }]
	<span class="commentBtn">
		<a class="modify-saveBtn">수정</a>
		<a class="delete-cancelBtn">삭제</a>
	</span>
	<div class="originalComment">${fn:replace(fn:replace(data.content, lf, "<br>"), crlf, "<br>") }</div>
	<div class="modifyComment"></div>
</div>
<hr/>
</c:forEach>

<style>
hr {
	margin-bottom: 10px;
}

.name {
	font-weight: bold;
	font-size: 18px;
	padding: 10px 10px 5px 10px;
}

.originalComment {
	padding: 0px 10px 10px 15px;
}

.commentBtn {
	float: right;
}

.commentModify {
	display: none;
	margin-top: 6px;

}
</style>


<script>
$(".modify-saveBtn").on("click", function() {
	var $div = $(this).closest("div");

	//저장처리
	if($(this).text() == "저장") {
		var comment = { id:$div.data("id"), content:$div.children(".modifyComment").find("textarea").val() };
		$.ajax({
			type: "post",
			url: "qna/comment/update",
			data: JSON.stringify(comment),
			contentType: "application/json",
			success: function(response) {
				//console.log($(".originalComment").text());
				//console.log(comment.content);
				if(comment.content != $(".originalComment").text()) {
					alert("댓글 수정" + response);
					commentList();
				}else {
					commentList();
				}
			}, error: function(req, text) {
				alert(text + " : " + req.status);
			}
			
		});
	}else {
		//수정화면
		$div.children(".modify").css("height", $div.children(".originalComment").height());
		var tag = "<textarea style='width:97%; height:90%; margin: 0px 10px 10px 15px; resize:none;'>" 
			+ $div.children(".originalComment").html().replace(/<br>/g, '\n')
			+ "</textarea>";
		$div.children(".modifyComment").html(tag);
		display("m", $div);
	}
});

$(".delete-cancelBtn").on("click", function() {
	var $div = $(this).closest("div");

	//취소
	if($(this).text() == "취소") {
		display("d", $div);
	}else {
		if(confirm("정말 삭제하시겠습니까?")) {
			$.ajax({
				url: "qna/comment/delete/" + $div.data("id"),
				success: function() {
					commentList();
				}, error: function(req, text) {
					alert(text + " : " + req.status);
				}

			});

		}
	}
});



function display(mode, div) {
	//수정 : modify 보이게, original 안보이게, 수정버튼->저장버튼, 삭제버튼 -> 취소버튼
	div.children(".modifyComment").css("display", mode=="m" ? "block" : "none");
	div.children(".originalComment").css("display", mode=="m" ? "none" : "block");

	div.find(".modify-saveBtn").text(mode == "m" ? "저장" : "수정");
	div.find(".delete-cancelBtn").text(mode == "m" ? "취소" : "삭제");
}

</script>