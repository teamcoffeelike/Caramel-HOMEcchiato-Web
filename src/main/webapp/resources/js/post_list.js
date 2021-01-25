function scrolled(y){
	let scrollBottom = $(window).scrollTop()+$(window).height();
	const MARGIN = 500;

	return scrollBottom+MARGIN >= y;
}

var ajaxSent = false;
var oldest;

function fetchPost(){
	if(!ajaxSent){
		ajaxSent = true;
		$.ajax({
			url: "api/recentPosts",
			type: "get",
			data: { "since": oldest },
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
						$(".postList").append(
							`<div class='postDetail'>
								<div class='userBox'>
									<img class='profile-image' src='${e.author.profileImage ? e.author.profileImage : "imgs/profile.png"}'/>
									<a class='profile-name' href='profile?userId=${e.author.id}'>${e.author.name}</a>
								</div>

								<a href='post?id=${e.id}'><img class='post-image' src='${e.image?e.image : "imgs/post.png"}'></a>

								<div class='contentBox'>
									<a class='btnLike'
										><i class='far fa-heart'></i>
										<span class='likeCount'>${e.likes}</span
									></a>
									<div class='content'>${e.text}</div>
									<div class='postDate'>${date_short}</div>
								</div>
							</div>`
						);

						let id = e.id;
						$(".postList>*:last-child .btnLike").on("click", function(){
							//console.log(this);
							
							let likeCount = $(this).children(".likeCount");
							let newLike = $(this).children(".fa-heart");
							if(!likeCount.hasClass("liked")){
								newLike.attr("data-prefix", "fas");
								likeCount.addClass("liked").text(parseInt(likeCount.text())+1);
							}else{
								newLike.attr("data-prefix", "far");
								likeCount.removeClass("liked").text(parseInt(likeCount.text())-1);
							}
							
							console.log(`asdfasdfasdfasdf ${id}`);
						});
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

