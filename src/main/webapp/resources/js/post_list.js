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
				console.log("Received "+data);
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
                            `<div class='userBox'>
                                <img class='profile-image' src='${e.author.profileImage}'/>
                                <a class='profile-name' href='profile?userId=${e.author.id}'>${e.author.name}</a>
                            </div>
                            
                            <a href='post?id=${e.id}'><img class='post-image' src='${e.image}'></a>
                            
                            <div class='contentBox'>
                                <div class='content'>${e.text}</div>
                                <div class='postDate'>${date_short}</div>
                                <a class='reaction' href='post?id=${e.id}'><strong>댓글 ${e.reactions }</strong>&nbsp;&nbsp; 댓글보기</a>
                            </div>`
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