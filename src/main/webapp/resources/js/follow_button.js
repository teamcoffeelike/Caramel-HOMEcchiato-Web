
var followButtons = {};

function registerFollowButton(widgets, id, following){
	let o = {
		widget: widgets,
		following: following,
		onclick: function(){
			let o = followButtons[id];

			console.log(`id ${id}, following ${o.following}`);

			$.ajax({
				url: "api/setFollowing",
				type: "post",
				data: { "followingId": id, "following": !o.following },
				dataType: "json",
				success: function(data){
					console.log("Received "+JSON.stringify(data));
				}
			});
			o.following = !o.following;
			updateClass(o);
		}
	};
	followButtons[id] = o;

	for(e of widgets)
		e.addEventListener("click", o.onclick);
	updateClass(o);
}

function updateClass(o){
	if(o.following){
		$(o.widget).removeClass("following")
				.children(".text")
				.text("팔로잉")
				.css("color", "#e67461");
	}else{
		$(o.widget).addClass("following")
				.children(".text")
				.text("팔로우")
				.css("color", "#fff");
	}
}
