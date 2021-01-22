package com.hanul.coffeelike.recipeedit.visitor;

import com.hanul.coffeelike.caramelweb.data.RecipeCategory;
import com.hanul.coffeelike.recipeedit.exception.RecipeEditorException;
import com.hanul.coffeelike.recipeedit.exception.runtime.DuplicatedCategorySet;
import com.hanul.coffeelike.recipeedit.exception.runtime.DuplicatedCoverImageSet;
import com.hanul.coffeelike.recipeedit.exception.runtime.DuplicatedStepImageSet;
import com.hanul.coffeelike.recipeedit.exception.runtime.DuplicatedStepSelection;
import com.hanul.coffeelike.recipeedit.exception.runtime.DuplicatedStepTextSet;
import com.hanul.coffeelike.recipeedit.exception.runtime.DuplicatedTitleSet;
import com.hanul.coffeelike.recipeedit.exception.runtime.DuplicatedTotalStepCountSet;
import com.hanul.coffeelike.recipeedit.exception.runtime.NoStepSelected;
import com.hanul.coffeelike.recipeedit.exception.runtime.NoStepSet;
import com.hanul.coffeelike.recipeedit.exception.runtime.NoTotalStepsCountSet;
import com.hanul.coffeelike.recipeedit.exception.runtime.StepOutOfBounds;
import com.hanul.coffeelike.recipeedit.exception.runtime.StepSelectBeforeTotalCountSet;
import com.hanul.coffeelike.recipeedit.template.RecipeTemplate;
import com.hanul.coffeelike.recipeedit.template.StepTemplate;
import org.springframework.web.multipart.MultipartFile;

public abstract class BaseRecipeBuilder implements RecipeEditorVisitor{
	protected final RecipeTemplate t = new RecipeTemplate();

	protected int selectedStepIndex = -1;

	@Override public void setCategory(RecipeCategory category) throws RecipeEditorException{
		if(t.category!=null) throw new DuplicatedCategorySet();
		t.category = category;
	}
	@Override public void setTitle(String title) throws RecipeEditorException{
		if(t.title!=null) throw new DuplicatedTitleSet();
		t.title = title;
	}
	@Override public void setCoverImage(MultipartFile coverImage) throws RecipeEditorException{
		if(t.coverImage.isSet()) throw new DuplicatedCoverImageSet();
		t.coverImage.setFile(coverImage);
	}
	@Override public void setTotalStepCount(int totalStepCount) throws RecipeEditorException{
		if(t.steps!=null) throw new DuplicatedTotalStepCountSet();
		t.steps = new StepTemplate[totalStepCount];
	}
	@Override public void newStep(int step) throws RecipeEditorException{
		expectStepIndexInRange(step);
		if(t.steps[step]!=null) throw new DuplicatedStepSelection(step);
		t.steps[step] = new StepTemplate();
		selectedStepIndex = step;
	}
	@Override public void selectStep(int step) throws RecipeEditorException{
		moveStep(step, step);
	}
	@Override public abstract void moveStep(int prevIndex, int newIndex) throws RecipeEditorException;
	@Override public abstract void removeStep(int step) throws RecipeEditorException;
	@Override public void setStepImage(MultipartFile image) throws RecipeEditorException{
		StepTemplate step = selectedStep();
		if(step.image.isSet()) throw new DuplicatedStepImageSet(selectedStepIndex);
		step.image.setFile(image);
	}
	@Override public abstract void removeStepImage() throws RecipeEditorException;
	@Override public void setStepText(String text) throws RecipeEditorException{
		StepTemplate step = selectedStep();
		if(step.text!=null) throw new DuplicatedStepTextSet(selectedStepIndex);
		step.text = text;
	}

	protected void expectStepCountDefined() throws RecipeEditorException{
		if(t.steps==null) throw new StepSelectBeforeTotalCountSet();
	}
	protected void expectStepIndexInRange(int step) throws RecipeEditorException{
		expectStepCountDefined();
		if(step<0||step>=t.steps.length) throw new StepOutOfBounds(step, t.steps.length);
	}

	protected StepTemplate selectedStep() throws RecipeEditorException{
		expectStepCountDefined();
		if(this.selectedStepIndex==-1) throw new NoStepSelected();
		return t.steps[selectedStepIndex];
	}

	public RecipeTemplate compile() throws RecipeEditorException{
		if(t.steps==null) throw new NoTotalStepsCountSet();
		checkCover();
		for(int i = 0; i<t.steps.length; i++){
			if(t.steps[i]==null) throw new NoStepSet(i);
			checkStep(i, t.steps[i]);
		}
		return t;
	}

	protected abstract void checkCover() throws RecipeEditorException;
	protected abstract void checkStep(int index, StepTemplate step) throws RecipeEditorException;
}
