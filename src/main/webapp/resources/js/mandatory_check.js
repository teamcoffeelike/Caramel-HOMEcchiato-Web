/**
 * 입력항목 입력여부 확인 
 */

function necessary(){
	var ok = true;
	$('.mandatory').each(function(){
		if( $(this).val().trim()=='' ){
			alert( $(this).attr('title') + '을 입력하세요!');
			$(this).val('');
			$(this).focus();
			ok = false;
		}
	});
	return ok;
}