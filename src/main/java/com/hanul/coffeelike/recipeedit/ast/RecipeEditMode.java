package com.hanul.coffeelike.recipeedit.ast;

public abstract class RecipeEditMode{
	public static final class WriteMode extends RecipeEditMode{
		public static final WriteMode INSTANCE = new WriteMode();
		private WriteMode(){}

		@Override public String toString(){
			return "WriteMode";
		}
	}

	public static final class EditMode extends RecipeEditMode{
		public final int id;
		public EditMode(int id){
			this.id = id;
		}

		@Override public String toString(){
			return "EditMode "+id;
		}
	}
}
