package com.hanul.coffeelike.recipeedit.visitor;

import com.hanul.coffeelike.caramelweb.data.RecipeCategory;
import com.hanul.coffeelike.recipeedit.exception.RecipeEditorException;
import org.springframework.web.multipart.MultipartFile;

public interface RecipeEditorVisitor{
	void setCategory(RecipeCategory category) throws RecipeEditorException;
	void setTitle(String title) throws RecipeEditorException;
	void setCoverImage(MultipartFile image) throws RecipeEditorException;
	void setTotalStepCount(int totalStepCount) throws RecipeEditorException;
	void newStep(int step) throws RecipeEditorException;
	void selectStep(int step) throws RecipeEditorException;
	void moveStep(int prevIndex, int newIndex) throws RecipeEditorException;
	void removeStep(int step) throws RecipeEditorException;
	void setStepImage(MultipartFile image) throws RecipeEditorException;
	void removeStepImage() throws RecipeEditorException;
	void setStepText(String text) throws RecipeEditorException;
}
