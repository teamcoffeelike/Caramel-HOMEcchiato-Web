<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header class="headerJsp">
	<div class="mainTitle">
		<img src="imgs/logo.svg" alt="홈으로" class="header-logo"/>
		<a href="<c:url value='/'/>" class="header-title">
			<span class="header-caramel">Caramel</span>
			<span class="header-homecchiato"><span class="header-home">HOME</span>cchiato</span>
		</a>
	</div>
	<div class="header-category">
		<ul class="header-mainMenu">
			<li><a href="list.in">소개</a></li>
			<li><a href="notice">공지사항</a></li>
			<li><a href="recipeList">레시피</a></li>
			
			<!-- 로그인 안한 상태 -->
			<c:if test="${empty loginUser }">
				<li><a href="login">로그인</a></li>
			</c:if>
			
			<!-- 로그인한 상태 -->
			<c:if test="${!empty loginUser}">
			<li class="subMenu">
				<a>마이페이지</a>
				<ul class="hide">
					<li><a href="profile?userId=${loginUser.userId}">내 프로필</a></li>
					<li><a href="follows">팔로워/팔로잉</a></li>
					<li><a href="likePost">좋아요</a></li>
					<li><a href="myRecipe?author=${loginUser.userId}">내 레시피</a></li>
					<li><a href="settings">설정</a></li>
					<li><a href="list.qna">문의</a></li>
					<li><a href="logout">로그아웃</a></li>
				</ul>
			</li>
			</c:if>
		</ul>
	</div>
</header>
<link rel="stylesheet" href="css/header.css">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript">
$(function(){
    $(".subMenu>a").on('click', function(){
        var submenu = $(this).next("ul");

        if( submenu.is(":visible") ){
            submenu.slideUp();
        }else{
            submenu.slideDown();
        }
    });
});
</script>