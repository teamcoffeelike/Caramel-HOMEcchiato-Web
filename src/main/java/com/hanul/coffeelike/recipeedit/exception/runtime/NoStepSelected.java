package com.hanul.coffeelike.recipeedit.exception.runtime;

public class NoStepSelected extends InterpretationException{
	public NoStepSelected(){
		super("현재 설정된 페이지 없음");
	}

	@Override public String jsonErrorMessage(){
		return NO_STEP_SELECTED;
	}
}
