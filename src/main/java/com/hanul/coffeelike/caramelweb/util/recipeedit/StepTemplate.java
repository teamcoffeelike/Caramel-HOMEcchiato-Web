package com.hanul.coffeelike.caramelweb.util.recipeedit;

import org.springframework.web.multipart.MultipartFile;

public final class StepTemplate{
	MultipartFile image;
	String text;

	public MultipartFile getImage(){
		return image;
	}
	public String getText(){
		return text;
	}
}
