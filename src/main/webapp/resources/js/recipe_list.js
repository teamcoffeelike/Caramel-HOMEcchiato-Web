var ajaxSent = false;
var endOfList = false;
var oldest;

function fetchPost(category){
	if(!ajaxSent&&!endOfList){
		ajaxSent = true;
		$.ajax({
			url: "api/recipeList",
			type: "get",
			data: { "since": oldest, "category": category },
			dataType: "json",
			success: function(data){
				ajaxSent = false;
				if(data.error){
					alert("Error: "+data.error);
					return;
				}
				console.log(data.recipes.length+" elements");
				if(data.recipes&&data.recipes.length){
					for(e of data.recipes){
						var date = new Date(e.postDate);
						var dateString = date.toLocaleDateString();
						$(".recipe-list").append(
							`<div class="recipe c-${e.category}">
								<a class="recipe-link" href="recipe?recipe=${e.id}"
									><img class="recipe-cover${e.coverImage ? "" : " empty"}" src="${e.coverImage ? e.coverImage : "imgs/post.png"}">
									<div class="recipe-body">
										<img class="recipe-element-category" src="imgs/${e.category}_icon.png">
										<div class="recipe-title-container">
											<div class="sphere c-${e.category}"></div>
											<div class="shade">
												<div class="recipe-title">
													${e.title}
												</div>
											</div>
										</div>
									</div>
								</a>
								
								<div class="userBox"
									><img class="profile-image" src="${e.author.profileImage ? e.author.profileImage : "imgs/profile.png"}"/>
									<a class="profile-name" href="profile?userId=${e.author.id}">${e.author.name}</a
								></div>
							</div>`
						);
					}
					let lastData = data.recipes[data.recipes.length-1];
					oldest = lastData.postDate;
				}
				if(data.endOfList){
					console.log("End of list reached");
					endOfList = true;
				}
			}, error: function(req, text){
				ajaxSent = false;
				alert(text+":"+req.status);
			}
		});
	}
}
