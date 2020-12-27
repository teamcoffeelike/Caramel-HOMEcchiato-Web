package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

public class ProfileData {

	private int id;
	@Nullable private String name;
	@Nullable private String motd;
	@Nullable private String profileImage;
	@Nullable private String post;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMotd() {
		return motd;
	}
	public void setMotd(String motd) {
		this.motd = motd;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	
}
