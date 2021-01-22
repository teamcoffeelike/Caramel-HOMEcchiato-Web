package com.hanul.coffeelike.recipeedit.exception.runtime;

public class OriginalStepOutOfBounds extends InterpretationException{
	public OriginalStepOutOfBounds(int originalStep, int originalSteps){
		super("범위를 벗어난 기존 페이지 "+originalStep+"/"+originalSteps+" 참조");
	}

	@Override public String jsonErrorMessage(){
		return ORIGINAL_STEP_OUT_OF_BOUNDS;
	}
}
