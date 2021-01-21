package com.hanul.coffeelike.caramelweb.util.recipeedit;

import com.hanul.coffeelike.caramelweb.data.RecipeCategory;
import org.springframework.web.multipart.MultipartFile;

public abstract class RecipeFunctionPrinter implements RecipeEditorFunctionVisitor{
	@Override public void setCategory(RecipeCategory category){
		print("SetCategory "+category);
	}
	@Override public void setTitle(String title){
		print("SetTitle \""+title+'"');
	}
	@Override public void setCoverImage(MultipartFile image){
		print("SetCoverImage #"+image.getName());
	}
	@Override public void setTotalStepCount(int totalStepCount){
		print("SetTotalStepCount "+totalStepCount);
	}
	@Override public void newStep(int step){
		print("NewStep "+step);
	}
	@Override public void selectStep(int step){
		print("SelectStep "+step);
	}
	@Override public void moveStep(int prevIndex, int newIndex){
		print("MoveStep "+prevIndex+" -> "+newIndex);
	}
	@Override public void removeStep(int step){
		print("RemoveStep "+step);
	}
	@Override public void setStepImage(MultipartFile image){
		print("SetStepImage "+image.getName());
	}
	@Override public void removeStepImage(){
		print("RemoveStepImage");
	}
	@Override public void setStepText(String text){
		print(text.length()>20 ?
				"SetStepText \""+text.substring(0, 20)+"...\"" :
				"SetStepText \""+text+'"');
	}

	protected abstract void print(String string);
}
