package com.hanul.coffeelike.caramelweb.data;

public class UserSettingData{
	private UserProfileData user;
	private boolean isSocialAccount;

	public UserSettingData(){}
	public UserSettingData(UserProfileData user, boolean isSocialAccount){
		this.user = user;
		this.isSocialAccount = isSocialAccount;
	}

	public UserProfileData getUser(){
		return user;
	}
	public void setUser(UserProfileData user){
		this.user = user;
	}
	public boolean isSocialAccount(){
		return isSocialAccount;
	}
	public void setSocialAccount(boolean socialAccount){
		isSocialAccount = socialAccount;
	}
}
