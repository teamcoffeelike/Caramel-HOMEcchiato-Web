<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>소개</title>
<link rel="stylesheet" href="css/introduce.css">
<link rel="stylesheet" href="https://unpkg.com/splitting/dist/splitting.css" />
<link rel="stylesheet" href="https://unpkg.com/splitting/dist/splitting-cells.css" />
<script src="https://unpkg.com/splitting/dist/splitting.min.js"></script>
<script src="https://unpkg.com/scroll-out/dist/scroll-out.min.js"></script>
<script src="https://codepen.io/shshaw/pen/QmZYMG.js"></script>
</head>
<body>
<!-- Header -->
<section class="intro-section">
	<h1>Caramel HOMEcchiato</h1>
	<p><span>집</span>에서도 여유롭게 <span>카페</span> 분위기를 즐겨보세요</p>
</section>
 
<!-- Content  -->
<!-- Recipe -->
<!-- 이미지 -->
<section class="content-section" data-scroll>
	<figure class="figure">
		<img src="imgs/coffee4.jpg">
	</figure>
	
	<!-- 텍스트 -->
	<div class="content">
		<header class="header">
			<div class="subheading"></div>
			<div data-splitting>
				<h2 class="heading">Recipe</h2>
			</div>
		</header>
		<p class="paragraph">
			기본으로 제공하는 레시피를 참고하여 레시피를 만들 수 있습니다.<br/> 
			기존의 레시피를 커스텀하여 나만의 특별한 레시피도 만들어보세요!
		</p>
	</div>
</section>

<!-- Post -->
<!-- 이미지 -->
<section class="content-section" data-scroll>
	<figure class="figure">
		<img src="imgs/coffee.jpg">
	</figure>
	
	<!-- 텍스트 -->
	<div class="content">
		<header class="header">
			<div class="subheading"></div>
			<h2 class="heading">Post</h2>
		</header>
		<p class="paragraph">
			인기 포스트를 통해 다양한 레시피와 일상을 공유할 수 있습니다.<br/>
			마음에 드는 포스트가 있다면 좋아요를 누르고 댓글을 달아보세요!
		</p>
	</div>
</section>

<!-- Follow -->
<!-- 이미지 -->
<section class="content-section" data-scroll>
	<figure class="figure">
		<img src="imgs/coffee.jpg">
	</figure>
	
	<!-- 텍스트 -->
	<div class="content">
		<header class="header">
			<div class="subheading"></div>
			<h2 class="heading">Follow</h2>
		</header>
		<p class="paragraph">
			친구를 검색하고 팔로우 할 수 있습니다.<br/>
			팔로우한 친구와 이야기를 주고 받으며 소통해보세요!
		</p>
	</div>
</section>

<!-- Like -->
<!-- 이미지 -->
<section class="content-section" data-scroll>
	<figure class="figure">
		<img src="imgs/coffee.jpg">
	</figure>
	
	<!-- 텍스트 -->
	<div class="content">
		<header class="header">
			<div class="subheading"></div>
			<h2 class="heading">Like</h2>
		</header>
		<p class="paragraph">
			게시물에 좋아요를 누르고 보관할 수 있습니다.<br/>
			마음에 드는 게시물이 있다면 보관함에 담아보세요!
		</p>
	</div>
</section>

<!-- My Page -->
<!-- 이미지 -->
<section class="content-section" data-scroll>
	<figure class="figure">
		<img src="imgs/coffee.jpg">
	</figure>
	
	<!-- 텍스트 -->
	<div class="content">
		<header class="header">
			<div class="subheading"></div>
			<h2 class="heading">My Page</h2>
		</header>
		<p class="paragraph">
			내 프로필을 설정하고 자유롭게 글을 올릴 수 있습니다.<br/>
			나만의 특별한 레시피와 일상을 기록해보세요!
		</p>
	</div>
</section>
<script> Splitting(); </script>
<script>
console.clear();

ScrollOut({
  cssProps: {
    visibleY: true,
    viewportY: true
  }
});

Splitting({ target: '.heading' });
</script>
</body>
</html>