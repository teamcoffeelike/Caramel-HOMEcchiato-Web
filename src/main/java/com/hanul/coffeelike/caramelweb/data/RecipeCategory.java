package com.hanul.coffeelike.caramelweb.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.springframework.lang.Nullable;

import java.lang.reflect.Type;

public enum RecipeCategory{
	HOT_COFFEE,
	ICE_COFFEE,
	TEA,
	ADE,
	SMOOTHIE,
	ETC;

	public String getName(){
		return toString();
	}

	public String getReadableName(){
		switch(this){
		case HOT_COFFEE: return "HOT Coffee";
		case ICE_COFFEE: return "ICE Coffee";
		case TEA: return "Tea";
		case ADE: return "Ade";
		case SMOOTHIE: return "Smoothie";
		case ETC: return "Etc.";
		default: throw new IllegalStateException("Unreachable");
		}
	}

	@Override public String toString(){
		return name().toLowerCase();
	}

	@Nullable public static RecipeCategory fromString(String string){
		try{
			return valueOf(string.toUpperCase());
		}catch(IllegalArgumentException ex){
			return null;
		}
	}

	public enum Json implements JsonSerializer<RecipeCategory>,
			JsonDeserializer<RecipeCategory>{
		INSTANCE;

		@Override public JsonElement serialize(RecipeCategory recipeCategory,
		                                       Type type,
		                                       JsonSerializationContext jsonSerializationContext){
			return new JsonPrimitive(recipeCategory.toString());
		}

		@Override public RecipeCategory deserialize(JsonElement jsonElement,
		                                            Type type,
		                                            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException{
			return fromString(jsonElement.getAsString());
		}
	}
}
