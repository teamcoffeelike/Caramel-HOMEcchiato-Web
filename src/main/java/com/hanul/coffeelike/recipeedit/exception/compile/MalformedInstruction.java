package com.hanul.coffeelike.recipeedit.exception.compile;

public class MalformedInstruction extends CompileException{
	public MalformedInstruction(){}
	public MalformedInstruction(String message){
		super(message);
	}
	public MalformedInstruction(String message, Throwable cause){
		super(message, cause);
	}
	public MalformedInstruction(Throwable cause){
		super(cause);
	}
	public MalformedInstruction(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}

	@Override public String jsonErrorMessage(){
		return MALFORMED_INSTRUCTION;
	}
}
