package com.hanul.coffeelike.recipeedit.exception.runtime;

public class NoCategorySet extends InterpretationException{
	public NoCategorySet(){
		super("카테고리 설정되지 않음");
	}

	@Override public String jsonErrorMessage(){
		return NO_CATEGORY_SET;
	}
}
