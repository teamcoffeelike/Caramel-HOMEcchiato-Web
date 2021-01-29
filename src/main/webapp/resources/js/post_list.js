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
			success: onFetchSuccess,
			error: function(req, text){
				ajaxSent = false;
				alert(text+':'+req.status);
			}
		});
	}
}

function fetchLikedPost(likedBy){
	if(!ajaxSent){
		ajaxSent = true;
		$.ajax({
			url: "api/likedPosts",
			type: "get",
			data: { "since": oldest, "pages": 10, "likedBy": likedBy },
			success: onFetchSuccess,
			error: function(req, text){
				ajaxSent = false;
				alert(text+':'+req.status);
			}
		});
	}
}

function onFetchSuccess(data){
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
						<a class='btnLike${e.likedByYou ? " liked" : ""}'
							><i class='${e.likedByYou ? "fas" : "far"} fa-heart'></i>
							<span class='likeCount'>${e.likes}</span
						></a>
						<div class='content'>${e.text}</div>
						<div class='postDate'>${date_short}</div>
					</div>
				</div>`
			);

			let id = e.id;
			$(".postList>*:last-child .btnLike").on("click", function(){
				let likeCount = $(this).children(".likeCount");
				let newLike = $(this).children(".fa-heart");
				let like;
				
				if(!$(this).hasClass("liked")){
					newLike.attr("data-prefix", "fas");
					$(this).addClass("liked");
					likeCount.text(parseInt(likeCount.text())+1);
					like = true;
				}else{
					newLike.attr("data-prefix", "far");
					$(this).removeClass("liked");
					likeCount.text(parseInt(likeCount.text())-1);
					like = false;
				}
				
				$.ajax({
					url: "api/likePost",
					type: "post",
					data: { "post": id, "like": like },
					success: function(data){
						if(data.error){
							alert("Error: "+data.error);
						}
					}, error: function(req, text){
						alert(text+':'+req.status);
					}
				});
			});
		}
		let lastData = data.posts[data.posts.length-1];
		oldest = lastData.likedDate ? lastData.likedDate : lastData.postDate;
	}
}