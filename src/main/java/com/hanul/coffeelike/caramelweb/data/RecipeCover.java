package com.hanul.coffeelike.caramelweb.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.hanul.coffeelike.caramelweb.util.AttachmentFileResolver;
import com.hanul.coffeelike.caramelweb.util.AttachmentURLConverter;
import org.springframework.lang.Nullable;

import java.lang.reflect.Type;

public class RecipeCover{
	private int id;
	private RecipeCategory category;
	private String title;
	@Nullable private String coverImage;
	private int author;
	private int ratings;
	@Nullable private Double averageRating;

	public RecipeCover(int id,
	                   RecipeCategory category,
	                   String title,
	                   @Nullable String coverImage,
	                   int author,
	                   int ratings,
	                   @Nullable Double averageRating){
		this.id = id;
		this.category = category;
		this.title = title;
		this.coverImage = coverImage;
		this.author = author;
		this.ratings = ratings;
		this.averageRating = averageRating;
	}

	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public RecipeCategory getCategory(){
		return category;
	}
	public void setCategory(RecipeCategory category){
		this.category = category;
	}
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	@Nullable public String getCoverImage(){
		return coverImage;
	}
	public void setCoverImage(@Nullable String coverImage){
		this.coverImage = coverImage;
	}
	public int getAuthor(){
		return author;
	}
	public void setAuthor(int author){
		this.author = author;
	}
	public int getRatings(){
		return ratings;
	}
	public void setRatings(int ratings){
		this.ratings = ratings;
	}
	@Nullable public Double getAverageRating(){
		return averageRating;
	}
	public void setAverageRating(@Nullable Double averageRating){
		this.averageRating = averageRating;
	}

	public enum Json implements JsonSerializer<RecipeCover>{
		INSTANCE;

		@Override public JsonElement serialize(RecipeCover src,
		                                       Type typeOfSrc,
		                                       JsonSerializationContext context){
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", src.getId());
			jsonObject.add("recipeCategory", context.serialize(src.getCategory()));
			jsonObject.addProperty("title", src.getTitle());
			if(AttachmentFileResolver.doesRecipeCoverImageExists(src.getCoverImage()))
				jsonObject.addProperty("coverImage", AttachmentURLConverter.recipeCoverImageFromId(src.getId()));
			jsonObject.addProperty("author", src.getAuthor());
			jsonObject.addProperty("ratings", src.getRatings());
			if(src.getAverageRating()!=null)
				jsonObject.addProperty("averageRating", src.getAverageRating());
			return jsonObject;
		}
	}
}
