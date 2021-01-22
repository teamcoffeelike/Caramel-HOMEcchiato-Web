package com.hanul.coffeelike.recipeedit.exception.runtime;

public class DuplicatedCategorySet extends InterpretationException{
	public DuplicatedCategorySet(){
		super("레시피 카테고리가 두 번 이상 설정됨");
	}

	@Override public String jsonErrorMessage(){
		return DUPLICATED_CATEGORY_SET;
	}
}
