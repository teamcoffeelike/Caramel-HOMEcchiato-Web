package com.hanul.coffeelike.recipeedit.exception.runtime;

public class DuplicatedCoverImageSet extends InterpretationException{
	public DuplicatedCoverImageSet(){
		super("레시피 표지 사진이 두 번 이상 설정됨");
	}

	@Override public String jsonErrorMessage(){
		return DUPLICATED_COVER_IMAGE_SET;
	}
}
