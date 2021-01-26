package com.hanul.coffeelike.recipeedit.exception.runtime;

public class InvalidSteps extends InterpretationException{
	public InvalidSteps(int steps){
		super("부적합한 페이지 수 "+steps);
	}

	@Override public String jsonErrorMessage(){
		return INVALID_STEPS;
	}
}
