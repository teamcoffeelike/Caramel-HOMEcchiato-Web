package com.hanul.coffeelike.recipeedit.exception.runtime;

public class StepOutOfBounds extends InterpretationException{
	public StepOutOfBounds(int step, int steps){
		super("범위를 벗어난 페이지 "+step+"/"+steps+" 참조");
	}

	@Override public String jsonErrorMessage(){
		return STEP_OUT_OF_BOUNDS;
	}
}
