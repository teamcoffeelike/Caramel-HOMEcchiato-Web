package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

public class ProfileImageData{
	@Nullable private String profileImage;

	public ProfileImageData(@Nullable String profileImage){
		this.profileImage = profileImage;
	}

	@Nullable public String getProfileImage(){
		return profileImage;
	}
	public void setProfileImage(@Nullable String profileImage){
		this.profileImage = profileImage;
	}
}
