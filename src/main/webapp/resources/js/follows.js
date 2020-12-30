/* 팔로우/팔로잉 탭 클릭시 리스트 보여주기 */
$(function() {
	$(".follow.button").on("click", function(){
		var idx = $(this).index();
		$(".follow").removeClass("on");
		$(".follow:nth-child("+(idx+1)+")").addClass("on");
	});
});