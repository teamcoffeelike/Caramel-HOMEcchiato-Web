<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>	
<meta charset="UTF-8">
<title>Q&A 게시판</title>
<link rel="stylesheet" href="css/qnaList.css">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>
<div class="qna">
    <div class="text">
        <h3>Q&A 게시판</h3>
        <span>게시판에 글을 남겨주시면 자세히 답변해 드리겠습니다. 문의 내용에 개인정보가 노출되지 않도록 유의해주세요!</span>
    </div>
    <!-- 검색 -->
    <div class="qna">
    <form method="POST" action="list.qna">
        <ul>
            <li><a class="searchBtn" onclick="$('form').submit()">검색</a></li>
            <li><input type="text" name="keyword" value="${page.keyword }" class="keyword" /></li>
            <li>
                <select name="search" class="option">
                    <option value="all" ${page.search eq 'all' ? 'selected' : ''}>전체</option>
                    <option value="title" ${page.search eq 'title' ? 'selected' : ''}>제목</option>
                    <option value="content" ${page.search eq 'content' ? 'selected' : ''}>내용</option>
                    <option value="writer" ${page.search eq 'writer' ? 'selected' : ''}>작성자</option>
                </select>
            </li>
        </ul>
	<input type='hidden' name='id' />
	<input type='hidden' name='currentPage' value="1"/>
	
    </form>

    </div>

    <!-- 글 목록 보기-->  
    <table>
    	<tr>
    		<th>제목</th>
    		<th class="w-px150">작성자</th>
    		<th class="w-px120">작성일</th>
    		<th class="w-px150">답변상태</th>
    	</tr>
    	<c:forEach items="${qnas }" var="q">
        <tr>
            <td><a onclick="go_detail(${q.id})" class="title">${q.title }</a></td>
            <td class="w-px150">${q.name }</td>
            <td class="w-px120">${q.writeDate }</td>
            <td class="w-px150">
            	<c:if test="${q.response > 0 }">
            		<span class="answer">답변완료</span>            	
            	</c:if>
            	<c:if test="${q.response == 0 }">
            		<span class="answer">답변대기</span>            	
            	</c:if>
            </td>
        </tr>
        </c:forEach>
    </table>
    <c:if test="${!empty loginUser }">
	    <ul class="qna">
	        <li><a class="writeBtn" href="new.qna">글쓰기</a></li>
	    </ul>
    </c:if>
</div>
<div style="margin: 30px auto; text-align: center;">
	<jsp:include page="/WEB-INF/views/include/page.jsp" />
</div>
<script>
function go_detail(id) {
	$("[name=id]").val(id);
	$("form").attr("action", "detail.qna");
	$("form").submit();
}


$(".keyword").on({
    focus: function() {
        $(".keyword").css("border", "1px solid #FCD092");
        $(".searchBtn").css("border", "1px solid #FCD092");
        $(".searchBtn").css("border-left", "none");
        $(".searchBtn").css("background-color", "#FCD092");
    },
    blur: function() {
        $(this).css("border", "1px solid #bebebe");
        $(".searchBtn").css("border", "1px solid #bebebe");
        $(".searchBtn").css("border-left", "none");
        $(".searchBtn").css("background-color", "transparent");
    }
});

</script>
</body>
</html>