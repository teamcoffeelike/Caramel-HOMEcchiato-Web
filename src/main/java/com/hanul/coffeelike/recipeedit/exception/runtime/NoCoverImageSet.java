package com.hanul.coffeelike.recipeedit.exception.runtime;

public class NoCoverImageSet extends InterpretationException{
	public NoCoverImageSet(){
		super("표지 사진 설정되지 않음");
	}

	@Override public String jsonErrorMessage(){
		return NO_COVER_IMAGE_SET;
	}
}
