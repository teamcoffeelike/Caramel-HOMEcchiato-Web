package com.hanul.coffeelike.caramelweb.util;

import org.apache.tika.Tika;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class FileExtensionUtils{
	private FileExtensionUtils(){}

	private static final Tika tika = new Tika();
	private static final Map<MediaType, String> extensions = new HashMap<>();

	static{
		extensions.put(MediaType.IMAGE_PNG, "png");
		extensions.put(MediaType.IMAGE_JPEG, "jpeg");
		extensions.put(MediaType.IMAGE_GIF, "gif");
	}

	public static MediaType getMediaType(File file){
		try{
			return MediaType.parseMediaType(tika.detect(file));
		}catch(IOException ex){
			ex.printStackTrace();
		}catch(InvalidMediaTypeException ex){
			ex.printStackTrace();
		}
		return MediaType.ALL;
	}

	public static MediaType getMediaType(MultipartFile file){
		try(InputStream is = file.getInputStream()){
			return getMediaType(is);
		}catch(IOException e){
			e.printStackTrace();
		}
		return MediaType.ALL;
	}

	public static MediaType getMediaType(InputStream data){
		try{
			return MediaType.parseMediaType(tika.detect(data));
		}catch(IOException ex){
			ex.printStackTrace();
		}catch(InvalidMediaTypeException ex){
			ex.printStackTrace();
		}
		return MediaType.ALL;
	}

	@Nullable public static String extension(File file){
		return extensions.get(getMediaType(file));
	}

	@Nullable public static String extension(MultipartFile file){
		return extensions.get(getMediaType(file));
	}
}
