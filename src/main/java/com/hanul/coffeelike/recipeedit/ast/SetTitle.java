package com.hanul.coffeelike.recipeedit.ast;

import com.hanul.coffeelike.recipeedit.exception.RecipeEditorException;
import com.hanul.coffeelike.recipeedit.visitor.RecipeEditorVisitor;

public final class SetTitle extends RecipeEditorAST{
	public final String title;

	public SetTitle(String title){
		this.title = title;
	}

	@Override public void visit(RecipeEditorVisitor visitor) throws RecipeEditorException{
		visitor.setTitle(title);
	}
}
