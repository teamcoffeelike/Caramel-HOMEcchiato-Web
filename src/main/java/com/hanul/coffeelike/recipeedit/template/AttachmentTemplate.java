package com.hanul.coffeelike.recipeedit.template;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

public final class AttachmentTemplate{
	@Nullable private MultipartFile file;
	@Nullable public String imageId;
	private AttachmentType attachmentType = AttachmentType.INHERIT;

	@Nullable public MultipartFile getFile(){
		return file;
	}
	public void setFile(MultipartFile file){
		this.file = file;
		this.attachmentType = AttachmentType.SET;
	}
	public void removeFile(){
		this.attachmentType = AttachmentType.REMOVE;
	}
	public boolean isSet(){
		return imageId!=null||attachmentType!=AttachmentType.INHERIT;
	}

	public AttachmentType getAttachmentType(){
		return attachmentType;
	}

	public enum AttachmentType{
		INHERIT,
		SET,
		REMOVE
	}
}
