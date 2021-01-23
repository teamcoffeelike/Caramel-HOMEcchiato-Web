package com.hanul.coffeelike.recipeedit.exception.runtime;

public class DuplicatedOriginalStepSelection extends InterpretationException{
	public DuplicatedOriginalStepSelection(int originalStep){
		super("두 번 이상의 기존 페이지 "+originalStep+" 선택");
	}
	@Override public String jsonErrorMessage(){
		return DUPLICATED_ORIGINAL_STEP_SELECTION;
	}
}
