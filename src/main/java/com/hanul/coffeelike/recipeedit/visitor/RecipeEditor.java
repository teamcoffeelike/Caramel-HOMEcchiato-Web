package com.hanul.coffeelike.recipeedit.visitor;

import com.hanul.coffeelike.caramelweb.data.Recipe;
import com.hanul.coffeelike.caramelweb.data.RecipeStep;
import com.hanul.coffeelike.recipeedit.exception.RecipeEditorException;
import com.hanul.coffeelike.recipeedit.exception.runtime.DuplicatedOriginalStepSelection;
import com.hanul.coffeelike.recipeedit.exception.runtime.DuplicatedStepImageSet;
import com.hanul.coffeelike.recipeedit.exception.runtime.DuplicatedStepSelection;
import com.hanul.coffeelike.recipeedit.exception.runtime.ModifyInWriteContext;
import com.hanul.coffeelike.recipeedit.exception.runtime.NoImageToRemoveFromOriginalStep;
import com.hanul.coffeelike.recipeedit.exception.runtime.NoOriginalStepHandled;
import com.hanul.coffeelike.recipeedit.exception.runtime.NoStepTextSet;
import com.hanul.coffeelike.recipeedit.exception.runtime.OriginalStepOutOfBounds;
import com.hanul.coffeelike.recipeedit.template.StepTemplate;

public class RecipeEditor extends BaseRecipeBuilder{
	private final Recipe recipe;
	private final boolean[] stepFlags;

	public RecipeEditor(Recipe recipe){
		this.recipe = recipe;
		this.stepFlags = new boolean[recipe.getSteps().size()];
	}

	@Override public void moveStep(int prevIndex, int newIndex) throws RecipeEditorException{
		expectOriginalStepIndexInRange(prevIndex);
		expectStepIndexInRange(newIndex);
		if(stepFlags[prevIndex]) throw new DuplicatedOriginalStepSelection(prevIndex);
		if(t.steps[newIndex]!=null) throw new DuplicatedStepSelection(newIndex);
		stepFlags[prevIndex] = true;
		t.steps[newIndex] = new StepTemplate(prevIndex);
		selectedStepIndex = newIndex;
	}
	@Override public void removeStep(int step) throws RecipeEditorException{
		expectOriginalStepIndexInRange(step);
		if(stepFlags[step]) throw new DuplicatedOriginalStepSelection(step);
		stepFlags[step] = true;
	}

	@Override public void removeStepImage() throws RecipeEditorException{
		StepTemplate step = selectedStep();
		if(step.originalIndex==null) throw new ModifyInWriteContext();
		if(step.image.isSet()) throw new DuplicatedStepImageSet(selectedStepIndex);
		if(recipe.getSteps().get(step.originalIndex).getImage()==null) throw new NoImageToRemoveFromOriginalStep(selectedStepIndex);
		step.image.removeFile();
	}

	private void expectOriginalStepIndexInRange(int index) throws RecipeEditorException{
		if(index<0||index>=stepFlags.length) throw new OriginalStepOutOfBounds(index, stepFlags.length);
	}

	@Override protected void checkCover() throws RecipeEditorException{
		for(int i = 0; i<stepFlags.length; i++){
			if(!stepFlags[i]) throw new NoOriginalStepHandled(i);
		}
	}

	@Override protected void checkStep(int index, StepTemplate step) throws RecipeEditorException{
		if(step.originalIndex!=null){
			RecipeStep original = recipe.getSteps().get(step.originalIndex);
			if(!step.image.isSet()&&original.getImage()!=null) step.image.imageId = original.getImage();
			if(step.text==null) step.text = original.getText();
		}else{
			if(step.text==null) throw new NoStepTextSet(index);
		}
	}
}
