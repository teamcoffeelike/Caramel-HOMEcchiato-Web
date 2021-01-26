<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="css/userBox.css">
<link rel="stylesheet" href="css/recipeCommon.css">
<link rel="stylesheet" href="css/recipeDetail.css">
<body>
	<div class="recipe-toc">
		<input id="idk" type="checkbox" hidden>
		<div class="toc-list">
			<div class="inner">
				<p><a href="#cover">표지</a></p>
				<c:forEach var="e" items="${recipe.steps}">
					<p><a href="#s${e.step}">페이지 ${e.step+1}</a></p>
				</c:forEach>
				<p><a href="#rate">평가 & 다른 레시피</a></p>
			</div>
		</div>
		<label for="idk" class="toc-button close">접기</label>
		<label for="idk" class="toc-button open">열기</label>
	</div>
	<div class="main">
		

		<div class="recipe-contents">
			<div id="cover" class="cover page c-${recipe.cover.category.name}">
				<div class="image-box">
					<img class="image" src="${recipe.cover.coverImage}">
				</div>
				
				${recipe.cover.title}
				${recipe.cover.author.name}
			</div>
			<c:forEach var="e" items="${recipe.steps}">
				<div id="s${e.step}" class="step page">
					
				</div>
			</c:forEach>
			<div id="rate" class="rate page">
				
			</div>
		</div>
	</div>
</body>