/**
* 팔로워/팔로우 탭 클릭시 리스트 보여주기
*/
$(function() {
	$(".follow-tab.button").on("click", function(){
		var idx = $(this).index();
		$(".follow-tab").removeClass("on");
		$(".follow-tab:nth-child("+(idx+1)+")").addClass("on");
	});
});
