package com.hanul.coffeelike.recipeedit.exception.runtime;

public class NoStepSet extends InterpretationException{
	public NoStepSet(int step){
		super("페이지 "+step+" 설정되지 않음");
	}

	@Override public String jsonErrorMessage(){
		return NO_STEP_SET;
	}
}
