package com.hanul.coffeelike.recipeedit.visitor;

import com.hanul.coffeelike.recipeedit.ast.RecipeEditorAST;
import com.hanul.coffeelike.recipeedit.exception.RecipeEditorException;

public class RecipeJoiner extends RecipePrinter{
	private final String separator;
	private final StringBuilder stb = new StringBuilder();
	private boolean first = true;

	public RecipeJoiner(String separator){
		this.separator = separator;
	}

	@Override protected void print(String string){
		if(first) first = false;
		else stb.append(separator);
		stb.append(string);
	}

	public RecipeJoiner apply(Iterable<RecipeEditorAST> it) throws RecipeEditorException{
		for(RecipeEditorAST ast : it) ast.visit(this);
		return this;
	}

	@Override public String toString(){
		return stb.toString();
	}
}
