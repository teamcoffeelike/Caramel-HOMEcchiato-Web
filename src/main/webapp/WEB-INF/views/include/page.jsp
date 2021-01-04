<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="pageList">
	<!-- 처음/이전 -->
	<c:if test="${page.index }">
		<a class="page_first">처음</a>
		<a class="page_prev">이전</a>
	</c:if>
	
	<!-- 페이지블럭 -->
	

</div>

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
