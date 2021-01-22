package com.hanul.coffeelike.recipeedit.exception.runtime;

public class DuplicatedTitleSet extends InterpretationException{
	public DuplicatedTitleSet(){
		super("레시피 타이틀이 두 번 이상 설정됨");
	}

	@Override public String jsonErrorMessage(){
		return DUPLICATED_TITLE_SET;
	}
}
