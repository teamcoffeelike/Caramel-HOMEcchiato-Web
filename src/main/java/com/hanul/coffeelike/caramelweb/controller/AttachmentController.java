package com.hanul.coffeelike.caramelweb.controller;

import com.hanul.coffeelike.caramelweb.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
public class AttachmentController{
	private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentController.class);

	@Autowired
	private FileService fileService;

	@RequestMapping(value = "/images/profileImage")
	public void profileImage(HttpServletResponse response,
	                         @RequestParam int id){
		File profileImage = fileService.getProfileImageFromUser(id);
		if(profileImage==null||!profileImage.exists()){
			respondWithBadRequest(response);
			return;
		}
		paste(profileImage, response);
	}

	@RequestMapping(value = "/images/postImage")
	public void postImage(HttpServletResponse response,
	                      @RequestParam int id){
		File image = fileService.getPostImageFromPost(id);
		if(image==null||!image.exists()){
			respondWithBadRequest(response);
			return;
		}

		paste(image, response);
	}

	@RequestMapping(value = "/images/recipeImage/cover")
	public void recipeCoverImage(HttpServletResponse response,
	                             @RequestParam int id){
		File image = fileService.getRecipeCoverImage(id);
		if(image==null||!image.exists()){
			respondWithBadRequest(response);
			return;
		}

		paste(image, response);
	}

	@RequestMapping(value = "/images/recipeImage/step")
	public void recipeStepImage(HttpServletResponse response,
	                            @RequestParam int recipe,
	                            @RequestParam int step){
		File image = fileService.getRecipeStepImage(recipe, step);
		if(image==null||!image.exists()){
			respondWithBadRequest(response);
			return;
		}

		paste(image, response);
	}

	private void respondWithBadRequest(HttpServletResponse response){
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		try{
			response.sendError(404, "Not Found");
		}catch(IOException e){
			LOGGER.error("응답 중 에러 발생", e);
		}
	}

	private void paste(File file, HttpServletResponse response){
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		try(OutputStream os = response.getOutputStream();
		    BufferedOutputStream bos = new BufferedOutputStream(os);
		    InputStream is = new FileInputStream(file);
		    BufferedInputStream bis = new BufferedInputStream(is)){

			while(true){
				int b = bis.read();
				if(b==-1) break;
				bos.write(b);
			}
		}catch(IOException e){
			LOGGER.error("응답 중 에러 발생", e);
		}
	}
}
