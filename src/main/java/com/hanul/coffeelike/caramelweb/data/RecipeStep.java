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
	private int step;
	@Nullable private String image;
	private String text;

	public RecipeStep(int recipe, int step, @Nullable String image, String text){
		this.recipe = recipe;
		this.step = step;
		this.image = image;
		this.text = text;
	}

	public int getRecipe(){
		return recipe;
	}
	public void setRecipe(int recipe){
		this.recipe = recipe;
	}
	public int getStep(){
		return step;
	}
	public void setStep(int step){
		this.step = step;
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

	public enum Json implements JsonSerializer<RecipeStep>{
		INSTANCE;

		@Override public JsonElement serialize(RecipeStep src,
		                                       Type typeOfSrc,
		                                       JsonSerializationContext context){
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("recipe", src.getRecipe());
			jsonObject.addProperty("step", src.getStep());
			if(AttachmentFileResolver.doesRecipeStepImageExists(src.getImage()))
				jsonObject.addProperty("image",
						AttachmentURLConverter.recipeStepImageFromId(src.getRecipe(), src.getStep()));
			jsonObject.addProperty("text", src.getText());
			return jsonObject;
		}
	}
}
