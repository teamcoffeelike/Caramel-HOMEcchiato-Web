package com.hanul.coffeelike.recipeedit.ast;

import com.hanul.coffeelike.recipeedit.exception.RecipeEditorException;
import com.hanul.coffeelike.recipeedit.visitor.RecipeEditorVisitor;

public abstract class StepSelector extends RecipeEditorAST{
	public final int step;

	protected StepSelector(int step){
		this.step = step;
	}

	public static final class NewStep extends StepSelector{
		public NewStep(int step){
			super(step);
		}

		@Override public void visit(RecipeEditorVisitor visitor) throws RecipeEditorException{
			visitor.newStep(step);
		}
	}

	public static final class SelectStep extends StepSelector{
		public SelectStep(int step){
			super(step);
		}

		@Override public void visit(RecipeEditorVisitor visitor) throws RecipeEditorException{
			visitor.selectStep(step);
		}
	}

	public static final class MoveStep extends StepSelector{
		private final int prevIndex;

		public MoveStep(int prevIndex, int step){
			super(step);
			this.prevIndex = prevIndex;
		}

		@Override public void visit(RecipeEditorVisitor visitor) throws RecipeEditorException{
			visitor.moveStep(prevIndex, step);
		}
	}
}
