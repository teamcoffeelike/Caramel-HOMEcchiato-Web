package com.hanul.coffeelike.recipeedit.ast;

import com.hanul.coffeelike.recipeedit.exception.RecipeEditorException;
import com.hanul.coffeelike.recipeedit.visitor.RecipeEditorVisitor;

public final class RemoveStepImage extends RecipeEditorAST{
	public static final RemoveStepImage INSTANCE = new RemoveStepImage();

	private RemoveStepImage(){}

	@Override public void visit(RecipeEditorVisitor visitor) throws RecipeEditorException{
		visitor.removeStepImage();
	}
}
