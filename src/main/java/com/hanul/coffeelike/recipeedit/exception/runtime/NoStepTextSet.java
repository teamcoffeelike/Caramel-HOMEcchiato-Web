package com.hanul.coffeelike.recipeedit.exception.runtime;

public class NoStepTextSet extends InterpretationException{
	public NoStepTextSet(int step){
		super("페이지 "+step+"의 내용 설정되지 않음");
	}

	@Override public String jsonErrorMessage(){
		return NO_STEP_TEXT_SET;
	}
}
