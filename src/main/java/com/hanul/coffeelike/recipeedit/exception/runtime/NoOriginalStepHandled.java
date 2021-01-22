package com.hanul.coffeelike.recipeedit.exception.runtime;

public class NoOriginalStepHandled extends InterpretationException{
	public NoOriginalStepHandled(int step){
		super("기존 페이지 "+step+"를 처리하지 않음");
	}

	@Override public String jsonErrorMessage(){
		return NO_ORIGINAL_STEP_HANDLED;
	}
}
