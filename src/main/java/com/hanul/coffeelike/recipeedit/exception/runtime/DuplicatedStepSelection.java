package com.hanul.coffeelike.recipeedit.exception.runtime;

public class DuplicatedStepSelection extends InterpretationException{
	public DuplicatedStepSelection(int step){
		super("두 번 이상의 페이지 "+step+" 선택");
	}

	@Override public String jsonErrorMessage(){
		return DUPLICATED_STEP_SELECTION;
	}
}
