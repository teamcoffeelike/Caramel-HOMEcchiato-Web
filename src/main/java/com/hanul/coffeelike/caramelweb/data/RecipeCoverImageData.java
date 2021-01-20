package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

public class RecipeCoverImageData{
	private int id;
	@Nullable private String coverImage;

	public RecipeCoverImageData(int id, @Nullable String coverImage){
		this.id = id;
		this.coverImage = coverImage;
	}

	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	@Nullable public String getCoverImage(){
		return coverImage;
	}
	public void setCoverImage(@Nullable String coverImage){
		this.coverImage = coverImage;
	}
}
