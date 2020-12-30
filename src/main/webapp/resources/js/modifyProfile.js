/*다른 사진 선택하기*/
$('.another-pic').click(function(){
	$('#popup, #popup-background').css('display', 'none');
	$('#img-attach').removeAttr('disabled');
	$('#img-attach').val('');
	$('#img-attach').click();
});

/*현재 사진 삭제하기*/
$('.delete-pic').click(function(){
	$('#popup, #popup-background').css('display', 'none');
	$('#img-attach').removeAttr('disabled');
	$('#img-attach').val('');
	$('#post-img').attr('src', "imgs/profile.png");
});

/*팝업창 취소*/
$('.cancle').click(function(){
	$('#popup, #popup-background').css('display', 'none'); 
	$('#img-attach').removeAttr('disabled');
});

$(document).on('click', '#tabs li', function(){
	$('#tabs li').removeClass();
	$(this).addClass('active');

	var idx = $('#tabs li.active').index();
	$('#tabContent > div').removeClass();
	$('#tabContent > div:eq('+ idx +')').addClass('active');
	
});