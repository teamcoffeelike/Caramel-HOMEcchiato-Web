<%@page import="org.apache.ibatis.reflection.SystemMetaObject"%>
<%@page import="com.hanul.coffeelike.caramelweb.data.Page"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="pageList">
	<!-- 처음/이전 -->
	<c:if test="${page.maximumPage>1}">
		<c:if test="${page.currentPage>1}">
			<a class="page_first" onclick="go_page(1)">처음</a>
			<a class="page_prev" onclick="go_page(${page.currentPage-1})">이전</a>
		</c:if>
		
		<c:forEach var="no" begin="${page.startingPage}" end="${page.endPage}">
			<c:if test="${no == page.currentPage}">
				<span class="page_on">${no}</span>
			</c:if>
			<c:if test="${no != page.currentPage}">
				<span onclick="go_page(${no})" class="page_off">${no}</span>
			</c:if>
			<%-- <span ${no == page.currentPage ?
							'class="page_on"' :
							 'onclick="go_page('+no+')" class="page_off"'}>${no}</span> --%>
		</c:forEach> 
		
		<!-- 다음/마지막 -->
		<c:if test="${page.currentPage<page.maximumPage}">
			<a class="page_next" onclick="go_page(${page.currentPage+1})">다음</a>
			<a class="page_last" onclick="go_page(${page.maximumPage})">마지막</a>
		</c:if>
	</c:if>
</div>

<script type="text/javascript">
function go_page(no) {
	$("[name=currentPage]").val(no);
	$("form").submit();
}
</script>

<style>
.page_on, .page_off, .page_next, .page_last, .page_first, .page_prev { 
	display:inline-block; line-height:30px;  margin:0;  cursor: pointer;
}
.page_on {
	color:#865449;	 font-weight:bold; background-color:#FCD092;   
}
.page_on, .page_off {
	min-width:22px;	padding: 1px 11px 1px;
}
.page_next, .page_last, .page_first, .page_prev {
	width:30px; border:1px solid #d0d0d0; text-indent:-9999999px 
}
.page_next { background:url("imgs/page_next.jpg") center no-repeat;}
.page_last { background:url("imgs/page_last.jpg") center no-repeat;}
.page_first { background: url("imgs/page_first.jpg") center no-repeat;}
.page_prev { background: url("imgs/page_prev.jpg") center no-repeat;}

</style>
