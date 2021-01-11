package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

public class PostImageData{
	private int id;
	@Nullable private String image;

	public PostImageData(int id, @Nullable String image){
		this.id = id;
		this.image = image;
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
}
