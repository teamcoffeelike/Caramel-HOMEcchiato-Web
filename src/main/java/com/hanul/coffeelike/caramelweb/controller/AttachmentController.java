package com.hanul.coffeelike.caramelweb.controller;

import com.hanul.coffeelike.caramelweb.data.Post;
import com.hanul.coffeelike.caramelweb.service.FileService;
import com.hanul.coffeelike.caramelweb.service.PostService;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
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
import java.io.PrintWriter;

@RestController
public class AttachmentController{
	private final Logger logger = LoggerFactory.getLogger(AttachmentController.class);

	@Autowired
	private FileService fileService;
	@Autowired
	private PostService postService;

	@RequestMapping(value = "/images/profileImage")
	public void profileImage(HttpServletResponse response,
	                         @RequestParam int id){
		File profileImage = fileService.getProfileImage(id);
		if(profileImage==null){
			respondWithBadRequest(response);
			return;
		}
		paste(profileImage, response);
	}

	@RequestMapping(value = "/images/postImage")
	public void postImage(HttpServletResponse response,
	                      @RequestParam int id){
		Post post = postService.post(id, null);
		if(post==null||post.getImage()==null){
			respondWithBadRequest(response);
			return;
		}

		File image = fileService.getPostImage(post.getImage());
		if(image==null){
			respondWithBadRequest(response);
			return;
		}

		paste(image, response);
	}

	private void respondWithBadRequest(HttpServletResponse response){
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		try(PrintWriter w = response.getWriter()){
			w.print(JsonHelper.failure("bad_request"));
		}catch(IOException e){
			logger.error("응답 중 에러 발생", e);
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
			logger.error("응답 중 에러 발생", e);
		}
	}
}
