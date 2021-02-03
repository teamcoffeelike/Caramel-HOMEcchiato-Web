function scrolled(y){
	let scrollBottom = $(window).scrollTop()+$(window).height();
	const MARGIN = 500;

	return scrollBottom+MARGIN >= y;
}

var ajaxSent = false;
var oldest;
var userId;

function fetchPost(){
	if(!ajaxSent){
		ajaxSent = true;
		$.ajax({
			url: "api/usersPosts",
			type: "get",
			data: { "since": oldest, "id": userId },
			dataType: "json",
			success: function(data){
				ajaxSent = false;
				if(data.error){
					alert("Error: "+data.error);
					return;
				}
				if(data.posts&&data.posts.length){
					for(e of data.posts){
						var date = new Date(e.postDate);
						var date_short = date.toLocaleDateString();
						
						let par = new URLSearchParams();
						par.append("id", e.id);
						par.append("from", `profile?userId=${userId}`);
						
						$(".postList").append(
							`<a class='imageBox' href='post?${par}'>
								<img class='post-image' src='${e.image?e.image : "imgs/post.png"}'>
							</a>`
						);
					}
					let lastData = data.posts[data.posts.length-1];
					oldest = lastData.postDate;
				}
			}, error: function(req, text){
				ajaxSent = false;
				alert(text+':'+req.status);
			}
		});
	}
}

$(function(){
	$(window).on("scroll", function(){
		if(scrolled($(".postEnd").offset().top)) fetchPost();
	});
	fetchPost();
});