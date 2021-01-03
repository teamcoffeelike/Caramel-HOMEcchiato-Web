<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>소개</title>
<link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script> AOS.init(); </script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(".header-info").fadeIn(500);
});

$(".scrollTop").click(function(){
	$("html, body").animate( { scrollTop : 0 }, 400);
	return false;
});
</script>
<style type="text/css">
.title {
	color: #E67461; font-size: 20px; position: relative;
	transform: translate(50%, 20px);
}

.header-info {
	display: none; margin: 20px;
}

.header-info span { font-size: 18px; font-weight: bold; }

#contents {
	margin-top: 100px;
}

.contents-title1, .contents-title2, .contents-title3 {
	font-size: 50px; font-weight: bold;
	margin-top: 50px;
}

.contents-title1 span {
	box-shadow: inset 0 -15px 0 #f0dc51;
}

.contents-title2 span {
	box-shadow: inset 0 -15px 0 #e67461;
}

.contents-title3 span {
	box-shadow: inset 0 -15px 0 #8eba92;	
}

.img-latte {
	width: 600px; height: 450px;
	position: absolute;
}

.img-lemonade {
	width: 550px; height: 700px;
	position: relative;	top: 0px; left: 1000px;
}

.img-couple {
	width: 500px; height: 350px;
	position: relative; top: 100px;	right: 400px;
}

.section-recipe img {
	width: 550px; height: 700px;
}

.section-post img, .section-myPage img {
	width: 550px; height: 700px;
}

.scrollTop {
	position: fixed;
	width: 45px;
	height: 40px;
	right: 2%;
	bottom: 20px;
	font-size: 20px;
	background-color: #e67461;
	color: #ffffff;
	text-align: center;
	padding-top: 5px;
}

</style>
</head>
<body>
<div class="wrap">
	<div id="header">
		<div class="title">
			<h3>Caramel<br/>HOMEcchiato</h3>
		</div>
 		<div class="header-info">
			<p><span class="point1">집</span>에서도 여유롭게</p>
			<p><span class="point2">휴식</span>을 취하며</p>
			<p><span class="point3">카페</span> 분위기를 즐겨보세요</p>
		</div>
		<div data-aos="fade-up" data-aos-duration="1000">
			<div class="header-imgs">
				<img class="img-latte" src="imgs/coffee.jpg" alt="사진"/>
				<img class="img-lemonade" src="imgs/ade2.jpg" alt="사진"/>
				<img class="img-couple" src="imgs/couple.jpg" alt="사진"/>
			</div>
		</div>
	</div>
	<div id="contents">
		<div class="section-recipe">
			<div class="inner">
				<div data-aos="fade-right" data-aos-duration="2000">
					<h1 class="contents-title1" onclick="location='list.re'"><span>Recipe</span></h1>
				</div>
				<p class="contents-text1">기본 레시피를 보며<br/>레시피를 만들어보세요!</p>
				<div data-aos="fade-up" data-aos-duration="3000">
					<img src="imgs/smoothie.jpg" alt="사진"/>
					<img src="imgs/coffee4.jpg" alt="사진"/>
					<img src="" alt="사진"/>
				</div>
			</div>
		</div>
		<div class="section-post">
			<div class="inner">
				<div data-aos="fade-right" data-aos-duration="2000">
					<p class="contents-title2"><span>Post</span></p>
				</div>
				<p class="contents-text2">친구들의 포스트를 보고<br/>이야기를 나누어보세요!</p>
				<div data-aos="fade-up" data-aos-duration="3000">
					<img src="imgs/coffee6.jpg" alt="사진"/>
				</div>
			</div>
		</div>
		<div class="section-myPage">
			<div class="inner">
				<div data-aos="fade-right" data-aos-duration="2000">
					<p class="contents-title3"><span>My Page</span></p>
				</div>
				<p class="contents-text3">나만의 특별한 레시피와<br/>일상을 기록해보세요!</p>
				<div data-aos="fade-up" data-aos-duration="3000">
					<img src="imgs/ade.jpg" alt="사진"/>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">
		<a class="scrollTop" href="#">↑</a>
	</div>
</div>
</body>
</html>