package com.hanul.coffeelike.recipeedit.exception.runtime;

public class NoTitleSet extends InterpretationException{
	public NoTitleSet(){
		super("타이틀 설정되지 않음");
	}
	@Override public String jsonErrorMessage(){
		return NO_TITLE_SET;
	}
}
