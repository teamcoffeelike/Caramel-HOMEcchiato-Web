package com.hanul.coffeelike.caramelweb.util;

import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;

import java.io.File;

public final class FileUtils{
	private FileUtils(){}

	public static String extension(String filename){
		return extension(filename, "");
	}
	public static String extension(String filename, @Nullable String defaultValue){
		int i = filename.lastIndexOf('.');
		if(i==-1) return defaultValue;
		return filename.substring(i);
	}

	// TODO https://stackoverflow.com/q/3334313 ?
	public static MediaType getMediaType(File file){
		switch(extension(file.getName())){
		case "gif":
			return MediaType.IMAGE_GIF;
		case "jpeg":
		case "jpg":
			return MediaType.IMAGE_JPEG;
		case "png":
			return MediaType.IMAGE_PNG;
		default:
			return MediaType.ALL;
		}
	}
}
