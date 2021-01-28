<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.hanul.coffeelike.caramelweb.data.RecipeCategory" %>
<link rel="stylesheet" href="css/userBox.css">
<link rel="stylesheet" href="css/recipeList.css">
<link rel="stylesheet" href="css/recipeGrid.css"> <%-- 주석처리하면 1열 레이아웃으로 돌아감 --%>
<link rel="stylesheet" href="css/recipeCommon.css">
<script src="js/recipe_list.js"></script>
<script>
const CATEGORY = "${category.name}";

function scrolled(y){
	let scrollBottom = $(window).scrollTop()+$(window).height();
	const MARGIN = 500;

	return scrollBottom+MARGIN >= y;
}

$(function(){
	$(window).on("scroll", function(){
		if(scrolled($(".recipe-end").offset().top)) fetchPost(CATEGORY);
	});
	fetchPost(CATEGORY);
});
</script>
<body>
	<div class="recipe-category-container">
		<c:forEach items="<%= RecipeCategory.values() %>" var="c">
			<c:choose>
				<c:when test="${c==category}">
			<a class="selected recipe-category c-${c.name}">
				</c:when>
				<c:otherwise>
			<a class="recipe-category c-${c.name}" href="?category=${c.name}">
				</c:otherwise>
			</c:choose>
				<img class="recipe-category-icon" src="imgs/${c.name}_icon.png">
				<div class="text">${c.readableName}</div>
			</a>
		</c:forEach>
	</div>

	<div class="recipe-list-content">
		<div class="recipe-list"></div>
		<span class="recipe-end"></span>
	</div>
</body>