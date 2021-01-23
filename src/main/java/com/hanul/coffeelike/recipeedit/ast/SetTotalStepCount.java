package com.hanul.coffeelike.recipeedit.ast;

import com.hanul.coffeelike.recipeedit.exception.RecipeEditorException;
import com.hanul.coffeelike.recipeedit.visitor.RecipeEditorVisitor;

public final class SetTotalStepCount extends RecipeEditorAST{
	public final int totalStepCount;

	public SetTotalStepCount(int totalStepCount){
		this.totalStepCount = totalStepCount;
	}

	@Override public void visit(RecipeEditorVisitor visitor) throws RecipeEditorException{
		visitor.setTotalStepCount(totalStepCount);
	}
}
