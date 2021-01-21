package com.hanul.coffeelike.caramelweb.util.recipeedit;

import com.hanul.coffeelike.caramelweb.data.Recipe;
import com.hanul.coffeelike.caramelweb.data.RecipeCategory;
import org.springframework.web.multipart.MultipartFile;

// TODO
public class RecipeEditor implements RecipeEditorFunctionVisitor{
	private final RecipeTemplate t = new RecipeTemplate();

	private int selectedStepIndex = -1;

	public RecipeEditor(Recipe recipe){
		// TODO
	}

	@Override public void setCategory(RecipeCategory category) throws RecipeEditorException{

	}
	@Override public void setTitle(String title) throws RecipeEditorException{

	}
	@Override public void setCoverImage(MultipartFile image) throws RecipeEditorException{

	}
	@Override public void setTotalStepCount(int totalStepCount) throws RecipeEditorException{

	}
	@Override public void newStep(int step) throws RecipeEditorException{

	}
	@Override public void selectStep(int step) throws RecipeEditorException{

	}
	@Override public void moveStep(int prevIndex, int newIndex) throws RecipeEditorException{

	}
	@Override public void removeStep(int step) throws RecipeEditorException{

	}
	@Override public void setStepImage(MultipartFile image) throws RecipeEditorException{

	}
	@Override public void removeStepImage() throws RecipeEditorException{

	}
	@Override public void setStepText(String text) throws RecipeEditorException{

	}

	public RecipeTemplate compile(){
		return t;
	}
}
