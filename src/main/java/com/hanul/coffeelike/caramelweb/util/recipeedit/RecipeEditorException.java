package com.hanul.coffeelike.caramelweb.util.recipeedit;

public class RecipeEditorException extends Exception{
	public RecipeEditorException(){
	}
	public RecipeEditorException(String message){
		super(message);
	}
	public RecipeEditorException(String message, Throwable cause){
		super(message, cause);
	}
	public RecipeEditorException(Throwable cause){
		super(cause);
	}
	public RecipeEditorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
