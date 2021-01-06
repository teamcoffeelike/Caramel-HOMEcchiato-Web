<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<link rel="stylesheet" href="css/noticeList.css">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>
<div class="notice">
	<div class="title">
		<h3>공지사항</h3>
		<span>Caramel HOMEcchiato 서비스의 오류, 장애, 기타 공지사항을 안내드립니다.</span>
	</div>
	<div class="notice">
		<form method="get" action="notice">
			<input type="hidden" name="currentPage" value="1">
			<ul class="noticeSearch">
				<li><a onclick="$('form').submit()" class="btnSearch">검색</a></li>
				<li><input type="text" name="keyword" class="keyword" value="${page.keyword }"></li>
				<li>
					<select name="search" class="option">
						<option value="all" ${page.search eq 'all' ? 'selected' : '' }>전체</option>
						<option value="title" ${page.search eq 'title' ? 'selected' : '' }>제목</option>
						<option value="content" ${page.search eq 'content' ? 'selected' : '' }>내용</option>
					</select>
				</li>
			</ul>
		</form>
	</div>
	<table>
		<tr>
			<th class="w-px200">제목</th>
			<th class="w-px80">작성일</th>
		</tr>
		<c:forEach items="${notices }" var="page">
		<tr>
			<td><a href="detail.no?id=${page.id }">${page.title }</a></td>
			<td style="text-align: center;">${page.writeDate }</td>
		</tr>
		</c:forEach>
	</table>
	<c:if test="${!empty loginUser }">
	<ul class="notice">
		<li><a href="new" class="btnNew">글쓰기</a></li>
	</ul>
	</c:if>
</div>
<div style="margin:50px auto; text-align: center;">
	<jsp:include page="/WEB-INF/views/include/page.jsp"/>
</div>
<script>
function go_detail(id) {
	$("[name=id]").val(id);
	$("form").attr("action", "detail.no");
	$("form").submit();
}

$(".keyword").on({
    focus: function() {
        $(".keyword").css("border", "1px solid #FCD092");
        $(".btnSearch").css("border", "1px solid #FCD092");
        $(".btnSearch").css("border-left", "none");
        $(".btnSearch").css("background-color", "#FCD092");
    },
    blur: function() {
        $(this).css("border", "1px solid #bebebe");
        $(".btnSearch").css("border", "1px solid #bebebe");
        $(".btnSearch").css("border-left", "none");
        $(".btnSearch").css("background-color", "transparent");
    }
});
</script>
</body>
</html>