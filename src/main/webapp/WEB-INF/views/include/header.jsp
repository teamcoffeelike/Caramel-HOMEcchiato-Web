<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header style="border-bottom: 1px solid #ccc; padding: 25px 0;">
	<div class="category" style="margin-left: 50px;">
		<a href="<c:url value='/allPostList'/>"><img src="" alt="홈으로"/></a>
	</div>
	<div style="position: absolute; right: 0; top: 25px; margin-right: 100px;">
		<ul id="mainMenu">
			<li><a href="list.in">소개</a></li>
			<li><a href="list.re">레시피</a></li>
			
			<!-- 로그인 안한 상태 -->
			<c:if test="${empty loginUser }">
				<li><a href="login">로그인</a></li>
			</c:if>
			
			<!-- 로그인한 상태 -->
			<c:if test="${!empty loginUser}">
			<li class="subMenu"><a>마이페이지</a>
				<ul class="hide">
					<li><a href="profile?userId=${loginUser.userId}">내 프로필</a></li>
					<li><a href="follows">팔로워/팔로잉</a></li>
					<li><a href="likePost">좋아요</a></li>
					<li><a href="myrecipe">내 레시피</a></li>
					<li><a href="search">친구찾기</a></li>
					<li><a href="settings">설정</a></li>
					<li><a href="logout">로그아웃</a></li>
				</ul>
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