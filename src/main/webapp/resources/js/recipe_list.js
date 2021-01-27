var ajaxSent = false;
var endOfList = false;
var oldest;

function fetchPost(category, author){
	if(!ajaxSent&&!endOfList){
		ajaxSent = true;

		$.ajax({
			url: "api/recipeList",
			type: "get",
			data: {
				"since": oldest,
				"category": category,
				"author": author
			},
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
						let stars = [];
						let avgRating = e.averageRating ? e.averageRating : 0;
						for(let i = 1; i<=5; i++){
							stars.push(`<img class="star" draggable="false" src="${
								i<=avgRating ? "imgs/full_star.svg" : 
								i-0.5<=avgRating ? "imgs/half_star.svg" :
								"imgs/no_star.svg"
							}">`);
						}

						$(".recipe-list").append(
							`<div class="recipe c-${e.category}">
								<a class="recipe-link" href="recipe?recipe=${e.id}"
									><div class="recipe-cover-container">
										<img class="recipe-cover${e.coverImage ? "" : " empty"}" src="${e.coverImage ? e.coverImage : "imgs/post.png"}">
										<div class="recipe-rating">${stars.join("")}<span>(${e.ratings})</span></div>
									</div>
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
