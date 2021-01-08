/**
 * 페이지 스크롤 효과
 */
console.clear();

ScrollOut({
	cssProps: {
		visibleY: true,
		viewportY: true
	}
});

/**
 * 텍스트 효과
 */
Splitting({ target: '.heading' });

/**
 * 타이틀 불러오기 효과
 */
$(document).ready(function(){
	$(".intro-section").fadeIn(500);
})