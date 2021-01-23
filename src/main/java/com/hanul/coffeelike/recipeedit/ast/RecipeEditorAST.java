package com.hanul.coffeelike.recipeedit.ast;

import com.hanul.coffeelike.recipeedit.visitor.RecipeEditorVisitor;
import com.hanul.coffeelike.recipeedit.exception.RecipeEditorException;

public abstract class RecipeEditorAST{
	public abstract void visit(RecipeEditorVisitor visitor) throws RecipeEditorException;
}
