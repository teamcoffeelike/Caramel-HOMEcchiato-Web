package com.hanul.coffeelike.recipeedit.exception.runtime;

public class DuplicatedTotalStepCountSet extends InterpretationException{
	public DuplicatedTotalStepCountSet(){
		super("총 페이지 수가 두 번 이상 설정됨");
	}

	@Override public String jsonErrorMessage(){
		return DUPLICATED_TOTAL_STEP_COUNT_SET;
	}
}
