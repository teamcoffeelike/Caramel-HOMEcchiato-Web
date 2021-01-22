package com.hanul.coffeelike.recipeedit.exception.runtime;

public class NoTotalStepsCountSet extends InterpretationException{
	public NoTotalStepsCountSet(){
		super("총 페이지 수 설정되지 않음");
	}

	@Override public String jsonErrorMessage(){
		return NO_TOTAL_STEPS_COUNT_SET;
	}
}
