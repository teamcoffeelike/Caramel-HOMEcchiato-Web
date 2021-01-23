package com.hanul.coffeelike.recipeedit.ast;

import com.hanul.coffeelike.recipeedit.exception.RecipeEditorException;
import com.hanul.coffeelike.recipeedit.visitor.RecipeEditorVisitor;

public final class SetStepText extends RecipeEditorAST{
	public final String stepText;

	public SetStepText(String stepText){
		this.stepText = stepText;
	}

	@Override public void visit(RecipeEditorVisitor visitor) throws RecipeEditorException{
		visitor.setStepText(stepText);
	}
}
