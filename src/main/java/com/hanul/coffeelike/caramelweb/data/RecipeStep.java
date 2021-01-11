package com.hanul.coffeelike.caramelweb.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.hanul.coffeelike.caramelweb.util.AttachmentFileResolver;
import com.hanul.coffeelike.caramelweb.util.AttachmentURLConverter;
import org.springframework.lang.Nullable;

import java.lang.reflect.Type;

public class RecipeStep{
	private int recipe;
	private int index;
	@Nullable private String image;
	private String text;
	@Nullable private RecipeTask task;

	public RecipeStep(int recipe, int index, @Nullable String image, String text, @Nullable RecipeTask task){
		this.recipe = recipe;
		this.index = index;
		this.image = image;
		this.text = text;
		this.task = task;
	}

	public int getRecipe(){
		return recipe;
	}
	public void setRecipe(int recipe){
		this.recipe = recipe;
	}
	public int getIndex(){
		return index;
	}
	public void setIndex(int index){
		this.index = index;
	}
	@Nullable public String getImage(){
		return image;
	}
	public void setImage(@Nullable String image){
		this.image = image;
	}
	public String getText(){
		return text;
	}
	public void setText(String text){
		this.text = text;
	}
	@Nullable public RecipeTask getTask(){
		return task;
	}
	public void setTask(@Nullable RecipeTask task){
		this.task = task;
	}

	public enum Json implements JsonSerializer<RecipeStep>{
		INSTANCE;

		@Override public JsonElement serialize(RecipeStep src,
		                                       Type typeOfSrc,
		                                       JsonSerializationContext context){
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("recipe", src.getRecipe());
			jsonObject.addProperty("index", src.getIndex());
			if(AttachmentFileResolver.doesRecipeStepImageExists(src.getImage()))
				jsonObject.addProperty("image",
						AttachmentURLConverter.recipeStepImageFromId(src.getRecipe(), src.getIndex()));
			jsonObject.addProperty("text", src.getText());
			if(src.getTask()!=null)
				jsonObject.add("task", context.serialize(src.getTask()));
			return jsonObject;
		}
	}
}
