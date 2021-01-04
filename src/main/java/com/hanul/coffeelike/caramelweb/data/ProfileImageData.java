package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

public class ProfileImageData{
	private int id;
	@Nullable private String profileImage;

	public ProfileImageData(int id,
			                @Nullable String profileImage){
		this.id = id;
		this.profileImage = profileImage;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Nullable public String getProfileImage(){
		return profileImage;
	}
	public void setProfileImage(@Nullable String profileImage){
		this.profileImage = profileImage;
	}
}
