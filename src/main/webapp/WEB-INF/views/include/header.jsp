<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header style="border-bottom: 1px solid #ccc; padding: 25px 0;">
	<div class="category" style="margin-left: 50px;">
		<a href="<c:url value='/'/>"><img src="" alt="홈으로"/></a>
	</div>
	<div style="position: absolute; right: 0; top: 25px; margin-right: 100px;">
		<ul id="mainMenu">
			<li><a href="list.in">소개</a></li>
			<li><a href="list.re">레시피</a></li>
			
			<li class="subMenu"><a href="#">마이페이지</a>
				<ul class="hide">
					<li><a href="profile<%-- ?userId=${loginUser.userId} --%>">내 프로필</a></li>
					<li><a href="myfollows">팔로워/팔로잉</a></li>
					<li><a href="mylike">좋아요</a></li>
					<li><a href="myrecipe">내 레시피</a></li>
					<li><a href="search">친구찾기</a></li>
					<li><a href="settings">설정</a></li>
					<li><a href="logout">로그아웃</a></li>
				</ul>
			</li>
		</ul>
	</div>
</header>
<style>
header div.category { font-size: 18px; }
header div.category a:hover, header div.category a.active { font-weight: bold; }
#mainMenu {margin: 0px auto; padding: 0px;  list-style: none;}
#mainMenu>li { float: left;}
#mainMenu>li a {display:inline-block; width:150px; padding:10px 5px; } 
#mainMenu>li a:hover, #mainMenu>li a:active { font-weight: bold; color: #e67461;}
.hide {
padding: 8px; display: none; width: 150px; 
background: #f4eae1; list-style: none;
border-radius: 10px;}
.hide:after {
border-top: 0 solid transparent;
border-left: 10px solid transparent;
border-right: 10px solid transparent; 
border-bottom: 10px solid #f4eae1;
content: "";
position: absolute;
top: 32px; left: 330px;
}
</style>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    $(".subMenu>a").click(function(){
        var submenu = $(this).next("ul");

        if( submenu.is(":visible") ){
            submenu.slideUp();
        }else{
            submenu.slideDown();
        }
    });
});
</script>