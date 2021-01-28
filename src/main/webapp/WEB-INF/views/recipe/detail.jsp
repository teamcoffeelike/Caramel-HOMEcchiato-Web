<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="newLine" value="<%='\n'%>"/>
<link rel="stylesheet" href="css/userBox.css">
<link rel="stylesheet" href="css/recipeCommon.css">
<link rel="stylesheet" href="css/recipeDetail.css">
<script>
const PAGES = ["cover",
<c:forEach var="e" items="${recipe.steps}">
	"s${e.step}",
</c:forEach>
	"rate"];

var page;
var pageSize;

function calculateCurrentPage(reset){
	let yPoint = window.scrollY+window.screen.height*0.3;

	if(!reset&&pageSize&&yPoint>=pageSize.y&&yPoint<=pageSize.y+pageSize.height) return;

	let pageCache;
	let pageElement;
	let pageOffset;
	for(p of PAGES){
		pageCache = p;
		pageElement = document.getElementById(p);
		pageOffset = pageElement.offsetTop;
		if(pageOffset+pageElement.offsetHeight>yPoint) break;
	}

	if(reset||(page!=pageCache)){
		if(page) $(".toc."+page).removeClass("on");
		$(".toc."+pageCache).addClass("on");
		history.replaceState(undefined, undefined, "#"+pageCache);
		page = pageCache;
		pageSize = {
			y: pageOffset,
			height: pageElement.offsetHeight
		};
		// console.log("page set to "+pageCache);
	}
}

var rating = ${empty recipe.cover.yourRating ? "0" : recipe.cover.yourRating};
var submittedRating = ${empty recipe.cover.yourRating ? "undefined" : recipe.cover.yourRating};

function updateRatingBar(ratingBar){
	$(ratingBar).children(".star").each(function(index, star){
		if(rating>=index+1){
			star.src = "imgs/full_star.svg";
			return;
		}else if(rating>=index+0.5){
			star.src = "imgs/half_star.svg";
			return;
		}
		star.src = "imgs/no_star.svg";
	});
	if(submittedRating){
		$("#submitRating").hide();
		$("#removeRating").show();
		if(submittedRating==rating){
			$("#editRating").hide();
		}else $("#editRating").show();
	}else{
		$("#submitRating").show();
		$("#removeRating").hide();
		$("#editRating").hide();
	}
}

var currentRatingBar;

function calculateRatingInput(ratingBar, inputX){
	let stars = $(ratingBar).children(".star").toArray();

	for(let index = 0; index<stars.length; index++){
		let star = stars[index];

		let coords = star.getBoundingClientRect();
		let offsetLeft = window.pageXOffset + coords.left;

		if(inputX<=offsetLeft){
			return index;
		}else if(inputX<=offsetLeft+coords.width/2){
			return index+0.5;
		}
	}
	return 5;
}

function submitRating(){
	$.ajax({
		url: "api/rateRecipe",
		type: "post",
		data: {
			"recipe": "${recipe.cover.id}",
			"rating": rating
		},
		error: function(req, text){
			alert(text+":"+req.status);
		}
	});
	submittedRating = rating;
	updateRatingBar($(".rating-bar").first());
}

function removeRating(){
	$.ajax({
		url: "api/deleteRecipeRating",
		type: "post",
		data: { "recipe": "${recipe.cover.id}" },
		error: function(req, text){
			alert(text+":"+req.status);
		}
	});
	submittedRating = undefined;
	rating = 0;
	updateRatingBar($(".rating-bar").first());
}

$(function(){
	// 페이지 인덱스
	$(window).on("scroll", function(){
		calculateCurrentPage();
	});

	// 레시피 목차 토글
	$("#idk").on("change", function(){
		if(this.checked) $(".recipe-toc").slideDown();
		else $(".recipe-toc").slideUp();
	});

	// 레이팅바 인풋
	$(".rating-bar").on("mousedown", function(event){
		currentRatingBar = this;
		event.preventDefault();
	});

	$(window).on("mousemove", function(event){
		if(currentRatingBar){
			let ratingCache = calculateRatingInput(currentRatingBar, event.screenX);
			if(rating!=ratingCache){
				rating = ratingCache;
				updateRatingBar(currentRatingBar);
			}
		}
	}).on("mouseup", function(){
		currentRatingBar = null;
	});

	// 현재 목차 설정
	calculateCurrentPage(true);

	// 레이팅바 초기화
	updateRatingBar($(".rating-bar").first());

	// 평가 버튼 작업
	$("#submitRating,#editRating").on("click", submitRating);
	$("#removeRating").on("click", removeRating);
});
</script>
<body>
	<input id="idk" type="checkbox" hidden>
	<div class="recipe-toc" style="display: none;">
		<div class="c-${recipe.cover.category.name}" style="width: 100%;">
			<div style="width: 100%; height: 70px; background-color: #00000020;"></div>
		</div>
		<div class="list">
			<p class="toc cover"><a href="#cover">표지</a></p>
			<c:forEach var="e" items="${recipe.steps}">
				<p class="toc s${e.step}"><a href="#s${e.step}">페이지 ${e.step+1}</a></p>
			</c:forEach>
			<p class="toc rate"><a href="#rate">평가 & 다른 레시피</a></p>
		</div>
		<div class="c-${recipe.cover.category.name}" style="width: 100%;">
			<div style="width: 100%; height: 20px; background-color: #00000020;"></div>
		</div>
	</div>
	<label for="idk" class="toc-button close c-${recipe.cover.category.name}">레시피 목차 접기</label>
	<label for="idk" class="toc-button open c-${recipe.cover.category.name}">레시피 목차 열기</label>

	<div class="main">
		<div class="recipe-contents">
			<div id="cover" class="cover page c-${recipe.cover.category.name}">
				<div class="image-box">
					<img class="image" src="${recipe.cover.coverImage}">
				</div>

				<div class="title-box">
					<h1 class="title">${recipe.cover.title}</h1>
					
					<div class="userBox"
						><img class="profile-image" src="${recipe.cover.author.profileImage ? recipe.cover.author.profileImage : "imgs/profile.png"}"/>
						<a class="profile-name" href="profile?userId=${recipe.cover.author.id}">${recipe.cover.author.name}</a
					></div>
				</div>
			</div>

			<c:forEach var="e" items="${recipe.steps}">
				<div id="s${e.step}" class="step page">
					<c:choose>
						<c:when test="${empty e.image}">
							<div class="color-box c-${recipe.cover.category.name}"></div>
						</c:when>
						<c:otherwise>
							<div class="image-box">
								<img class="image" src="${e.image}">
							</div>
						</c:otherwise>
					</c:choose>
					<div class="text-box">${fn:replace(e.text, newLine, "<br>")}</div>
				</div>
			</c:forEach>

			<div id="rate" class="rate page c-${recipe.cover.category.name}">
				<div class="rate-box">
					<div class="rating-bar" draggable="false"
						><img class="star" draggable="false" src="imgs/no_star.svg"
						><img class="star" draggable="false" src="imgs/no_star.svg"
						><img class="star" draggable="false" src="imgs/no_star.svg"
						><img class="star" draggable="false" src="imgs/no_star.svg"
						><img class="star" draggable="false" src="imgs/no_star.svg"
					></div>

					<button id="submitRating">평가하기</button>
					<button id="editRating">평가 수정</button>
					<button id="removeRating">평가 삭제</button>
				</div>

				<div class="suggestion-box">
					<div class="text">같은 유저의 레시피</div>
					<div class="userBox"
						><img class="profile-image" src="${recipe.cover.author.profileImage ? recipe.cover.author.profileImage : "imgs/profile.png"}"/>
						<a class="profile-name" href="profile?userId=${recipe.cover.author.id}">${recipe.cover.author.name}</a
					></div>
					<div class="recipe-list-box">
						<div class="recipe-list">
							<c:forEach var="e" items="${otherRecipes}">
								<a class="suggested-recipe" href="recipe?recipe=${e.id}">
									<img src="${e.coverImage}">
									<div class="title-container">
										<div class="circle c-${e.category.name}"></div>
										<img class="category" src="imgs/${e.category.name}_icon.png">
										<div class="title">${e.title}</div>
									</div>
								</a>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>