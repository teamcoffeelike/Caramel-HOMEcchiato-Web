package com.hanul.coffeelike.recipeedit.exception.runtime;

public class NoImageToRemoveFromOriginalStep extends InterpretationException{
	public NoImageToRemoveFromOriginalStep(int originalStep){
		super("기존 페이지 "+originalStep+"에서 삭제할 이미지 없음");
	}

	@Override public String jsonErrorMessage(){
		return NO_IMAGE_TO_REMOVE_FROM_ORIGINAL_STEP;
	}
}
