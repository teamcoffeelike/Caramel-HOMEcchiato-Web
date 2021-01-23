package com.hanul.coffeelike.caramelweb.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.hanul.coffeelike.caramelweb.util.AttachmentFileResolver;
import com.hanul.coffeelike.caramelweb.util.AttachmentURLConverter;
import org.springframework.lang.Nullable;

import java.lang.reflect.Type;
import java.sql.Date;

public class RecipeCover{
	private int id;
	private RecipeCategory category;
	private String title;
	@Nullable private String coverImage;
	private UserProfileData author;
	private Date postDate;
	@Nullable private Date lastEditDate;
	private int ratings;
	@Nullable private Double averageRating;
	@Nullable private Double yourRating;

	public RecipeCover(){}
	public RecipeCover(int id,
	                   RecipeCategory category,
	                   String title,
	                   @Nullable String coverImage,
	                   UserProfileData author,
	                   Date postDate,
	                   @Nullable Date lastEditDate,
	                   int ratings,
	                   @Nullable Double averageRating,
	                   @Nullable Double yourRating){
		this.id = id;
		this.category = category;
		this.title = title;
		this.coverImage = coverImage;
		this.author = author;
		this.postDate = postDate;
		this.lastEditDate = lastEditDate;
		this.ratings = ratings;
		this.averageRating = averageRating;
		this.yourRating = yourRating;
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
	public UserProfileData getAuthor(){
		return author;
	}
	public void setAuthor(UserProfileData author){
		this.author = author;
	}
	public Date getPostDate(){
		return postDate;
	}
	public void setPostDate(Date postDate){
		this.postDate = postDate;
	}
	@Nullable public Date getLastEditDate(){
		return lastEditDate;
	}
	public void setLastEditDate(@Nullable Date lastEditDate){
		this.lastEditDate = lastEditDate;
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
	@Nullable public Double getYourRating(){
		return yourRating;
	}
	public void setYourRating(@Nullable Double yourRating){
		this.yourRating = yourRating;
	}


	public enum Json implements JsonSerializer<RecipeCover>{
		INSTANCE;

		@Override public JsonElement serialize(RecipeCover src,
		                                       Type typeOfSrc,
		                                       JsonSerializationContext context){
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", src.getId());
			jsonObject.add("category", context.serialize(src.getCategory()));
			jsonObject.addProperty("title", src.getTitle());
			if(AttachmentFileResolver.doesRecipeCoverImageExists(src.getCoverImage()))
				jsonObject.addProperty("coverImage", AttachmentURLConverter.recipeCoverImageFromId(src.getId()));
			jsonObject.add("author", context.serialize(src.getAuthor()));
			jsonObject.addProperty("postDate", src.getPostDate().getTime());
			if(src.getLastEditDate()!=null)
				jsonObject.addProperty("lastEditDate", src.getLastEditDate().getTime());
			jsonObject.addProperty("ratings", src.getRatings());
			if(src.getAverageRating()!=null)
				jsonObject.addProperty("averageRating", src.getAverageRating());
			if(src.getYourRating()!=null)
				jsonObject.addProperty("yourRating", src.getYourRating());
			return jsonObject;
		}
	}
}
