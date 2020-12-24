<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header style="border-bottom: 1px solid #ccc; padding: 15px 0;">
	<div class="category" style="margin-left: 50px;">
		<ul>
			<li><a href="<c:url value='/'/>"><img src="" alt="홈으로"/></a></li>
		</ul>
	</div>
	<div style="position: absolute; right: 0; top: 25px; margin-right: 100px;">
		<ul>
			<li><a href="list.in">소개</a></li>
			<li><a href="list.re">레시피</a></li>
			
			<li><a href="mypage">마이페이지</a></li>
			<li><a href="logout">로그아웃</a></li>
		</ul>
	</div>
</header>
<style>
header ul, header ul li { margin: 0px; padding: 0px; display: inline; }
header div.category { font-size: 18px; }
header div.category li:not(:first-child) { padding-left: 30px; }
header div.category li a:hover, header div.category li a.active {
	font-weight: bold;	color:  }
</style>