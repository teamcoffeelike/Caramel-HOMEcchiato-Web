package com.hanul.coffeelike.recipeedit.exception.runtime;

import com.hanul.coffeelike.recipeedit.exception.RecipeEditorException;

/**
 * {@link RuntimeException}이 있어서ㅎ
 */
public abstract class InterpretationException extends RecipeEditorException{
	public InterpretationException(){}
	public InterpretationException(String message){
		super(message);
	}
	public InterpretationException(String message, Throwable cause){
		super(message, cause);
	}
	public InterpretationException(Throwable cause){
		super(cause);
	}
	public InterpretationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
