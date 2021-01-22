package com.hanul.coffeelike.recipeedit.exception.runtime;

public class DuplicatedStepTextSet extends InterpretationException{
	public DuplicatedStepTextSet(int step){
		super("중복된 "+step+" 페이지 내용 설정");
	}

	@Override public String jsonErrorMessage(){
		return DUPLICATED_STEP_TEXT_SET;
	}
}
