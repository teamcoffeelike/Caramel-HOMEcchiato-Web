package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

import java.sql.Date;

public class Post{
	private int id;
	@Nullable private String image;
	private String text;
	private UserProfileData author;
	private Date postDate;
	@Nullable private Date lastEditDate;
	private String isDeleted;

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
	            String isDeleted,
	            int likes,
	            int reactions,
	            @Nullable Boolean likedByYou){
		this.id = id;
		this.image = image;
		this.text = text;
		this.author = author;
		this.postDate = postDate;
		this.lastEditDate = lastEditDate;
		this.isDeleted = isDeleted;
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
	public String getIsDeleted(){
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted){
		this.isDeleted = isDeleted;
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
}
