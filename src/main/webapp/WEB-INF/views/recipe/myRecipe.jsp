<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="css/userBox.css">
<link rel="stylesheet" href="css/recipeList.css">
<link rel="stylesheet" href="css/recipeCommon.css">
<script src="js/recipe_list.js"></script>
<script>
function scrolled(y){
	let scrollBottom = $(window).scrollTop()+$(window).height();
	const MARGIN = 500;

	return scrollBottom+MARGIN >= y;
}

$(function(){
	$(window).on("scroll", function(){
		if(scrolled($(".recipe-end").offset().top)) fetchPost(undefined, ${author});
	});
	fetchPost(undefined, ${author});
});
</script>
<body>
	<div class="recipe-list-content">
		<div class="recipe-list"></div>
		<span class="recipe-end"></span>
	</div>
</body>