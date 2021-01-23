package com.hanul.coffeelike.recipeedit.exception.compile;

public class UnknownFunction extends MalformedInstruction{
	public UnknownFunction(char fun){
		super("정의되지 않은 Function "+fun);
	}

	@Override public String jsonErrorMessage(){
		return UNKNOWN_FUNCTION;
	}
}
