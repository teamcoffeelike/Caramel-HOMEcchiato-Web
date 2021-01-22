package com.hanul.coffeelike.recipeedit.exception.compile;

public class UnknownMode extends MalformedInstruction{
	public UnknownMode(char mode){
		super("정의되지 않은 Mode "+mode);
	}

	@Override public String jsonErrorMessage(){
		return UNKNOWN_MODE;
	}
}
