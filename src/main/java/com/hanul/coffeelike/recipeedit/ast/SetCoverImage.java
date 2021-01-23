package com.hanul.coffeelike.recipeedit.ast;

import com.hanul.coffeelike.recipeedit.exception.RecipeEditorException;
import com.hanul.coffeelike.recipeedit.visitor.RecipeEditorVisitor;
import org.springframework.web.multipart.MultipartFile;

public final class SetCoverImage extends RecipeEditorAST{
	public final MultipartFile image;

	public SetCoverImage(MultipartFile image){
		this.image = image;
	}

	@Override public void visit(RecipeEditorVisitor visitor) throws RecipeEditorException{
		visitor.setCoverImage(image);
	}
}
