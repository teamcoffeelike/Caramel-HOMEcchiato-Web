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
        <h3>Q&A 게시판</h3><br/>
        <span>게시판에 글을 남겨주시면 자세히 답변해 드리겠습니다.</span>
        <br/>
        <span>문의 내용에 개인정보가 노출되지 않도록 유의해주세요!</span>
    </div>
    <!-- 검색 -->
    <div class="qna">
    <form method="POST" action="list.qna">
        <ul>
            <li><a class="searchBtn">검색</a></li>
            <li><input type="text" name="keyword" value="" class="keyword" /></li>
            <li>
                <select name="search" class="option">
                    <option value="all">전체</option>
                    <option value="title">제목</option>
                    <option value="content">내용</option>
                    <option value="writer">작성자</option>
                </select>
            </li>
        </ul>
	<input type='hidden' name='id' />
    </form>

    </div>

    <!-- 글 목록 보기-->  
    <table>
    	<c:forEach items="${qnas }" var="page">
        <tr>
            <td><a onclick="go_detail(${page.id})" class="title">${page.title }</a></td>
            <td class="w-px130">${page.name }</td>
            <td class="w-px120">${page.writeDate }</td>
            <td class="w-px150"><a class="answer" style="cursor: default;">답변대기</a></td>
        </tr>
        </c:forEach>
    </table>
    <ul class="qna">
        <li><a class="writeBtn" href="new.qna">글쓰기</a></li>
    </ul>
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