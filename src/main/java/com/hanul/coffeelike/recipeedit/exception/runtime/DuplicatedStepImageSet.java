package com.hanul.coffeelike.recipeedit.exception.runtime;

public class DuplicatedStepImageSet extends InterpretationException{
	public DuplicatedStepImageSet(int step){
		super("중복된 "+step+" 페이지 첨부 이미지 설정");
	}

	@Override public String jsonErrorMessage(){
		return DUPLICATED_STEP_IMAGE_SET;
	}
}
