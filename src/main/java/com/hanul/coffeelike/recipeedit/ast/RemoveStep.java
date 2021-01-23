package com.hanul.coffeelike.recipeedit.ast;

import com.hanul.coffeelike.recipeedit.exception.RecipeEditorException;
import com.hanul.coffeelike.recipeedit.visitor.RecipeEditorVisitor;

public final class RemoveStep extends RecipeEditorAST{
	public final int step;

	public RemoveStep(int step){
		this.step = step;
	}

	@Override public void visit(RecipeEditorVisitor visitor) throws RecipeEditorException{
		visitor.removeStep(step);
	}
}
