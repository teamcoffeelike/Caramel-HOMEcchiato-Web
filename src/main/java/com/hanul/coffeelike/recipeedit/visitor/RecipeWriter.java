package com.hanul.coffeelike.recipeedit.visitor;

import com.hanul.coffeelike.recipeedit.exception.RecipeEditorException;
import com.hanul.coffeelike.recipeedit.exception.runtime.ModifyInWriteContext;
import com.hanul.coffeelike.recipeedit.exception.runtime.NoCategorySet;
import com.hanul.coffeelike.recipeedit.exception.runtime.NoCoverImageSet;
import com.hanul.coffeelike.recipeedit.exception.runtime.NoStepTextSet;
import com.hanul.coffeelike.recipeedit.exception.runtime.NoTitleSet;
import com.hanul.coffeelike.recipeedit.template.StepTemplate;

public class RecipeWriter extends BaseRecipeBuilder{
	@Override public void moveStep(int prevIndex, int newIndex) throws RecipeEditorException{
		throw new ModifyInWriteContext();
	}
	@Override public void removeStep(int step) throws RecipeEditorException{
		throw new ModifyInWriteContext();
	}
	@Override public void removeStepImage() throws RecipeEditorException{
		throw new ModifyInWriteContext();
	}

	@Override protected void checkCover() throws RecipeEditorException{
		if(t.category==null) throw new NoCategorySet();
		if(t.title==null) throw new NoTitleSet();
		if(!t.coverImage.isSet()) throw new NoCoverImageSet();
	}

	@Override protected void checkStep(int index, StepTemplate step) throws RecipeEditorException{
		if(step.text==null) throw new NoStepTextSet(index);
	}
}
