package com.hanul.coffeelike.recipeedit.exception.runtime;

public class StepSelectBeforeTotalCountSet extends InterpretationException{
	public StepSelectBeforeTotalCountSet(){
		super("총 페이지 갯수 설정 이전에 페이지 참조");
	}

	@Override public String jsonErrorMessage(){
		return STEP_SELECT_BEFORE_TOTAL_COUNT_SET;
	}
}
