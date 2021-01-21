package com.hanul.coffeelike.caramelweb.util.recipeedit;

import com.hanul.coffeelike.caramelweb.data.RecipeCategory;
import org.springframework.web.multipart.MultipartFile;

public abstract class RecipeEditorAST{
	public abstract void visit(RecipeEditorFunctionVisitor visitor) throws RecipeEditorException;

	public static final class SetCategory extends RecipeEditorAST{
		public final RecipeCategory category;
		public SetCategory(RecipeCategory category){
			this.category = category;
		}
		@Override public void visit(RecipeEditorFunctionVisitor visitor) throws RecipeEditorException{
			visitor.setCategory(category);
		}
	}

	public static final class SetTitle extends RecipeEditorAST{
		public final String title;
		public SetTitle(String title){
			this.title = title;
		}
		@Override public void visit(RecipeEditorFunctionVisitor visitor) throws RecipeEditorException{
			visitor.setTitle(title);
		}
	}

	public static final class SetCoverImage extends RecipeEditorAST{
		public final MultipartFile image;
		public SetCoverImage(MultipartFile image){
			this.image = image;
		}
		@Override public void visit(RecipeEditorFunctionVisitor visitor) throws RecipeEditorException{
			visitor.setCoverImage(image);
		}
	}

	public static final class SetTotalStepCount extends RecipeEditorAST{
		public final int totalStepCount;
		public SetTotalStepCount(int totalStepCount){
			this.totalStepCount = totalStepCount;
		}
		@Override public void visit(RecipeEditorFunctionVisitor visitor) throws RecipeEditorException{
			visitor.setTotalStepCount(totalStepCount);
		}
	}

	public static abstract class StepSelector extends RecipeEditorAST{
		public final int step;
		protected StepSelector(int step){
			this.step = step;
		}

		public static final class NewStep extends StepSelector{
			public NewStep(int step){
				super(step);
			}
			@Override public void visit(RecipeEditorFunctionVisitor visitor) throws RecipeEditorException{
				visitor.newStep(step);
			}
		}

		public static final class SelectStep extends StepSelector{
			public SelectStep(int step){
				super(step);
			}
			@Override public void visit(RecipeEditorFunctionVisitor visitor) throws RecipeEditorException{
				visitor.selectStep(step);
			}
		}

		public static final class MoveStep extends StepSelector{
			private final int prevIndex;
			public MoveStep(int prevIndex, int step){
				super(step);
				this.prevIndex = prevIndex;
			}
			@Override public void visit(RecipeEditorFunctionVisitor visitor) throws RecipeEditorException{
				visitor.moveStep(prevIndex, step);
			}
		}
	}

	public static final class RemoveStep extends RecipeEditorAST{
		public final int step;
		public RemoveStep(int step){
			this.step = step;
		}
		@Override public void visit(RecipeEditorFunctionVisitor visitor) throws RecipeEditorException{
			visitor.removeStep(step);
		}
	}

	public static final class SetStepImage extends RecipeEditorAST{
		public final MultipartFile image;
		public SetStepImage(MultipartFile image){
			this.image = image;
		}
		@Override public void visit(RecipeEditorFunctionVisitor visitor) throws RecipeEditorException{
			visitor.setStepImage(image);
		}
	}

	public static final class RemoveStepImage extends RecipeEditorAST{
		public static final RemoveStepImage INSTANCE = new RemoveStepImage();
		private RemoveStepImage(){}
		@Override public void visit(RecipeEditorFunctionVisitor visitor) throws RecipeEditorException{
			visitor.removeStepImage();
		}
	}

	public static final class SetStepText extends RecipeEditorAST{
		public final String stepText;
		public SetStepText(String stepText){
			this.stepText = stepText;
		}
		@Override public void visit(RecipeEditorFunctionVisitor visitor) throws RecipeEditorException{
			visitor.setStepText(stepText);
		}
	}
}
