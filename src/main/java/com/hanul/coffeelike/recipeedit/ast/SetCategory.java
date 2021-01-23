package com.hanul.coffeelike.recipeedit.ast;

import com.hanul.coffeelike.caramelweb.data.RecipeCategory;
import com.hanul.coffeelike.recipeedit.exception.RecipeEditorException;
import com.hanul.coffeelike.recipeedit.visitor.RecipeEditorVisitor;

public final class SetCategory extends RecipeEditorAST{
	public final RecipeCategory category;

	public SetCategory(RecipeCategory category){
		this.category = category;
	}

	@Override public void visit(RecipeEditorVisitor visitor) throws RecipeEditorException{
		visitor.setCategory(category);
	}
}
