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

public class Post{
	private int id;
	@Nullable private String image;
	private String text;
	private UserProfileData author;
	private Date postDate;
	@Nullable private Date lastEditDate;

	private int likes;
	private int reactions;
	@Nullable private Boolean likedByYou;

	public Post(){}
	public Post(int id,
	            @Nullable String image,
	            String text,
	            UserProfileData author,
	            Date postDate,
	            @Nullable Date lastEditDate,
	            int likes,
	            int reactions,
	            @Nullable Boolean likedByYou){
		this.id = id;
		this.image = image;
		this.text = text;
		this.author = author;
		this.postDate = postDate;
		this.lastEditDate = lastEditDate;
		this.likes = likes;
		this.reactions = reactions;
		this.likedByYou = likedByYou;
	}

	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
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
	@Nullable
	public Date getLastEditDate(){
		return lastEditDate;
	}
	public void setLastEditDate(@Nullable Date lastEditDate){
		this.lastEditDate = lastEditDate;
	}

	public int getLikes(){
		return likes;
	}
	public void setLikes(int likes){
		this.likes = likes;
	}
	public int getReactions(){
		return reactions;
	}
	public void setReactions(int reactions){
		this.reactions = reactions;
	}
	@Nullable
	public Boolean getLikedByYou(){
		return likedByYou;
	}
	public void setLikedByYou(@Nullable Boolean likedByYou){
		this.likedByYou = likedByYou;
	}

	@Override public String toString(){
		return "Post{"+
				"id="+id+
				", image='"+image+'\''+
				", text='"+text+'\''+
				", author="+author+
				", postDate="+postDate+
				", lastEditDate="+lastEditDate+
				", likes="+likes+
				", reactions="+reactions+
				", likedByYou="+likedByYou+
				'}';
	}


	public enum Json implements JsonSerializer<Post>{
		INSTANCE;

		@Override public JsonElement serialize(Post src,
		                                       Type typeOfSrc,
		                                       JsonSerializationContext context){
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", src.getId());
			if(AttachmentFileResolver.doesPostImageExists(src.getImage()))
				jsonObject.addProperty("image", AttachmentURLConverter.postImageFromId(src.getId()));
			jsonObject.addProperty("text", src.getText());
			jsonObject.add("author", context.serialize(src.getAuthor()));
			jsonObject.add("postDate", context.serialize(src.getPostDate()));
			if(src.getLastEditDate()!=null)
				jsonObject.add("lastEditDate", context.serialize(src.getLastEditDate()));

			jsonObject.addProperty("likes", src.getLikes());
			jsonObject.addProperty("reactions", src.getReactions());
			if(src.getLikedByYou()!=null)
				jsonObject.addProperty("likedByYou", src.getLikedByYou());
			return jsonObject;
		}
	}
}
