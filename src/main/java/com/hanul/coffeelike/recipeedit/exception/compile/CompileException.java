package com.hanul.coffeelike.recipeedit.exception.compile;

import com.hanul.coffeelike.recipeedit.exception.RecipeEditorException;

public abstract class CompileException extends RecipeEditorException{
	public CompileException(){}
	public CompileException(String message){
		super(message);
	}
	public CompileException(String message, Throwable cause){
		super(message, cause);
	}
	public CompileException(Throwable cause){
		super(cause);
	}
	public CompileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
