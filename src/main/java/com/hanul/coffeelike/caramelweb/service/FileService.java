package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.FileDAO;
import com.hanul.coffeelike.caramelweb.data.PostImageData;
import com.hanul.coffeelike.caramelweb.data.ProfileImageData;
import com.hanul.coffeelike.caramelweb.util.AttachmentFileResolver;
import com.hanul.coffeelike.caramelweb.util.AttachmentType;
import com.hanul.coffeelike.caramelweb.util.FileExtensionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

@Service
public class FileService{
	private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

	@Autowired
	private FileDAO fileDAO;

	///////////////////////////////////////////////////////////////////////
	//
	// 프로필 이미지
	//
	///////////////////////////////////////////////////////////////////////

	/**
	 * @return 유저가 존재하지 않거나 작업 실패 시 {@code false}, 성공 시 {@code true}
	 */
	public boolean setProfileImage(int userId, MultipartFile multipartFile){
		ProfileImageData prevProfileImage = fileDAO.findProfileImage(userId);
		if(prevProfileImage==null){ // User doesn't exists
			return false;
		}

		String name = trySaveFile(multipartFile,
				AttachmentType.PROFILE_IMAGE,
				generateUniqueFilename(AttachmentType.PROFILE_IMAGE, userId));

		// 멀티파트 내용물을 스토리지로 이동
		if(name==null) return false;

		// DB에 삽입
		if(!fileDAO.setUserProfileImage(userId, name)){
			// DB에 삽입 실패, 파일 삭제
			tryRemoveFile(AttachmentFileResolver.getProfileImageFile(name));
			return false;
		}
		// 기존 파일 삭제
		String prevFile = prevProfileImage.getProfileImage();
		if(prevFile!=null){
			tryRemoveFile(AttachmentFileResolver.getProfileImageFile(prevFile));
		}
		return true;
	}

	/**
	 * @return 유저가 존재하지 않거나 작업 실패 시 {@code false}. 삭제할 이미지가 없거나 작업 성공 시 {@code true}
	 */
	public boolean removeProfileImageFromUser(int userId){
		ProfileImageData prevProfileImage = fileDAO.findProfileImage(userId);
		if(prevProfileImage==null){ // User doesn't exists
			return false;
		}
		fileDAO.removeProfileImage(userId);
		String profileImage = prevProfileImage.getProfileImage();
		if(profileImage!=null)
			return tryRemoveFile(AttachmentFileResolver.getProfileImageFile(profileImage));
		else return true;
	}

	/**
	 * 전달받은 유저의 프로필 이미지를 가져옵니다.
	 *
	 * @return 부착된 파일이 존재하지 않을 시 {@code null}. null이 아닐 경우에도 실제 파일이 없을 수 있음.
	 */
	@Nullable public File getProfileImageFromUser(int userId){
		ProfileImageData prevProfileImage = fileDAO.findProfileImage(userId);
		if(prevProfileImage==null||prevProfileImage.getProfileImage()==null) return null;
		return AttachmentFileResolver.getProfileImageFile(prevProfileImage.getProfileImage());
	}

	///////////////////////////////////////////////////////////////////////
	//
	// 글 첨부
	//
	///////////////////////////////////////////////////////////////////////

	/**
	 * 파일 시스템에 포스트 이미지를 저장하고 데이터베이스에 삽입 가능한 파일 식별자를 반환합니다.
	 *
	 * @return 파일 식별자, 이미지 저장 실패 시 {@code null}
	 */
	@Nullable public String savePostImage(int postId, MultipartFile image){
		return trySaveFile(image, AttachmentType.POST_IMAGE, generateUniqueFilename(AttachmentType.POST_IMAGE, postId));
	}

	/**
	 * 파일 시스템에 저장된 포스트 이미지를 삭제합니다.
	 *
	 * @return 작업 실패 시 {@code false}. 삭제할 이미지가 없거나 작업 성공 시 {@code true}
	 */
	public boolean removePostImage(String filename){
		return tryRemoveFile(AttachmentFileResolver.getPostImageFile(filename));
	}

	/**
	 * 전달받은 파일명에 해당하는 포스트 이미지 파일을 가져옵니다.
	 *
	 * @return 부착된 파일이 존재하지 않을 시 {@code null}. null이 아닐 경우에도 실제 파일이 없을 수 있음.
	 */
	public File getPostImageFromPost(int postId){
		PostImageData postImage = fileDAO.findPostImage(postId);
		if(postImage==null||postImage.getImage()==null) return null;
		return AttachmentFileResolver.getPostImageFile(postImage.getImage());
	}

	@Nullable private String trySaveFile(MultipartFile multipartFile, AttachmentType type, String generatedName){
		File storage = AttachmentFileResolver.getStorage(type);
		File dest = new File(storage, generatedName);
		try{
			//noinspection ResultOfMethodCallIgnored
			storage.mkdirs();
			if(!dest.createNewFile()){
				LOGGER.warn("파일 '{} ({})' 생성 실패.", dest.getPath(), dest.getAbsolutePath());
			}
			multipartFile.transferTo(dest);
		}catch(Exception e){
			LOGGER.error("파일 '{} ({})' 생성 중 오류 발생.", dest.getPath(), dest.getAbsolutePath(), e);
			return null;
		}
		String ext = FileExtensionUtils.extension(dest);
		if(ext==null){
			LOGGER.debug("이미지 파일 저장: {} ({})", dest.getPath(), dest.getAbsolutePath());
			return generatedName;
		}

		File dest2 = new File(storage, generatedName+"."+ext);
		try{
			Files.move(dest.toPath(), dest2.toPath());
		}catch(Exception e){
			LOGGER.error("파일 '{} ({})' 생성 중 오류 발생.", dest2.getPath(), dest2.getAbsolutePath(), e);
			tryRemoveFile(dest);
			return null;
		}
		LOGGER.debug("이미지 파일 저장: {} ({})", dest2, dest2.getAbsolutePath());
		return generatedName+"."+ext;
	}

	private boolean tryRemoveFile(File file){
		try{
			if(!file.delete()){
				LOGGER.error("파일 '{} ({})'이 존재하지 않아 삭제할 수 없습니다.", file.getPath(), file.getAbsolutePath());
			}
			return true;
		}catch(Exception ex){
			LOGGER.error("파일 '{} ({})' 삭제가 허용되지 않았습니다.", file.getPath(), file.getAbsolutePath(), ex);
			return false;
		}
	}

	private static final DateTimeFormatter DATE_TIME_FORMATTER =
			DateTimeFormatter.ofPattern("uuMMdd.HH.mm.ss.SSS")
					.withLocale(Locale.US)
					.withZone(ZoneId.of("UTC"));

	private static final Random RNG = new Random();

	/**
	 * 타입과 데이터 키를 이용해 겹치지 않는(희망사항) 파일명을 생성합니다.
	 */
	private static String generateUniqueFilename(AttachmentType type,
	                                             Object primaryIdentifier){
		return type.baseFileName+'-'+
				primaryIdentifier+'-'+
				DATE_TIME_FORMATTER.format(LocalDateTime.now())+'-'+
				RNG.nextInt(1024);
	}
}
