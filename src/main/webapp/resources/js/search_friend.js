function goto(id){
	location.href="profile?userId=" + id;
}

$(function(){
	$("#searchName").on("input", function(){
		$.ajax({
			type: "post",
			url: "api/searchUserByName",
			data: { name: this.value },
			success: function(response){
				console.log("searchUserByName "+JSON.stringify(response));
				
				if($("#searchName").val() != "") {
					$("#searchNameResult").html(
						response.users.map(function(e){
							return `<div class="searchNameRow" onclick="goto(${e.id})">
								<img src="${e.profileImage == null ? 'imgs/profile.png' : e.profileImage }" class="searchProfileImage"> 
								<span class="searchResult">${e.name}</span>
							</div>`
						}).join("")
					);
				}else {
					$("#searchNameResult").html("");
				}
			},
			error: function(req, text){
				alert(text + " : " + req.status);
			}
		});
	});
});