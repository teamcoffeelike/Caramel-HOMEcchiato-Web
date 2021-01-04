<%@page import="com.hanul.coffeelike.caramelweb.data.Page"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="pageList">
	<!-- 처음/이전 -->
	<a class="page_first" onclick="go_page(1)">처음</a>
	<a class="page_prev">이전</a>
	
	<!-- 페이지블럭 -->
	<%
	Page _page = (Page)request.getAttribute("page");
	int startingPage = _page.getCurrentPage()-2;
	int endPage = _page.getCurrentPage()+2;
	
	if(startingPage<1){
		startingPage = 1;
		endPage = 5;
	}
	
	if(endPage>_page.getMaximumPage(10)){
		endPage = _page.getMaximumPage(10);
	}
	
	pageContext.getAttribute("startingPage", startingPage);
	pageContext.getAttribute("endPage", endPage);
	%>
	
	<c:forEach var="no" begin="<%=startingPage%>" end="<%=endPage%>">
		<c:if test="${no eq page.currepntPage }">
			<span class="page_on">${no }</span>
		</c:if>
		<%-- <a href="qna?currentPage=${e}" class="page_on">${e}</a> --%>
		
	</c:forEach> 
	
	

	<!-- 다음/마지막 -->
	<a class="page_next">다음</a>
	<a class="page_last">마지막</a>
</div>

<script type="text/javascript">
function go_page(no) {
	$("[name=currentPage]").val(no);
	$("form").submit();
}
</script>

<style>
.page_on, .page_off, .page_next, .page_last, .page_first, .page_prev { 
	display:inline-block; line-height:30px;  margin:0;  
}
.page_on {
	border:1px solid gray;	color:#fff;	 font-weight:bold; background-color:gray;   
}
.page_on, .page_off {
	min-width:22px;	padding: 0 5px 2px;
}
.page_next, .page_last, .page_first, .page_prev {
	width:30px; border:1px solid #d0d0d0; text-indent:-9999999px 
}
.page_next { background:url("imgs/page_next.jpg") center no-repeat;}
.page_last { background:url("imgs/page_last.jpg") center no-repeat;}
.page_first { background: url("imgs/page_first.jpg") center no-repeat;}
.page_prev { background: url("imgs/page_prev.jpg") center no-repeat;}

</style>
