package com.hanul.coffeelike.recipeedit.exception.runtime;

public class ModifyInWriteContext extends InterpretationException{
	public ModifyInWriteContext(){
		super("새 페이지 작성 중 기존 페이지 참조");
	}

	@Override public String jsonErrorMessage(){
		return MODIFY_IN_WRITE_CONTEXT;
	}
}
