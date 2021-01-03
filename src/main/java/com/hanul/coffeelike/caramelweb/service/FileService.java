package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.FileDAO;
import com.hanul.coffeelike.caramelweb.data.ProfileImageData;
import com.hanul.coffeelike.caramelweb.util.FileExtensionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

@Service
public class FileService{
	private static final String PROFILE_IMAGE = "profileImage";
	private static final String POST_IMAGE = "postImage";

	private static final Logger logger = LoggerFactory.getLogger(FileService.class);

	@Autowired
	private FileDAO fileDAO;

	private final File storageRoot = new File("/images");
	private static final Random rng = new Random();

	///////////////////////////////////////////////////////////////////////
	//
	// 프로필 이미지
	//
	///////////////////////////////////////////////////////////////////////

	/**
	 * @return 유저가 존재하지 않거나 작업 실패 시 {@code false}, 성공 시 {@code true}
	 */
	public boolean setProfileImage(int userId, MultipartFile multipartFile){
		// TODO https://stackoverflow.com/q/3334313 ?
		ProfileImageData prevProfileImage = fileDAO.findProfileImage(userId);
		if(prevProfileImage==null){ // User doesn't exists
			return false;
		}

		String name = generateUniqueFilename("ProfileImage", userId, FileExtensionUtils.extension(multipartFile));
		File file = new File(getStorage(PROFILE_IMAGE), name);
		String filePath = file.getPath();
		logger.debug("profileImage = {} ({})", filePath, file.getAbsolutePath());


		// 멀티파트 내용물을 스토리지로 이동
		if(!trySaveFile(multipartFile, file)) return false;

		// DB에 삽입
		if(!fileDAO.setUserProfileImage(userId, filePath)){
			// DB에 삽입 실패, 파일 삭제
			tryRemoveFile(file);
			return false;
		}
		// 기존 파일 삭제
		String prevFile = prevProfileImage.getProfileImage();
		if(prevFile!=null){
			tryRemoveFile(new File(getStorage(PROFILE_IMAGE), prevFile));
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
		String profileImage = prevProfileImage.getProfileImage();
		if(profileImage!=null)
			return tryRemoveFile(new File(getStorage(PROFILE_IMAGE), profileImage));
		else return true;
	}

	@Nullable public File getProfileImageFromUser(int userId){
		ProfileImageData prevProfileImage = fileDAO.findProfileImage(userId);
		if(prevProfileImage!=null){
			String profileImage = prevProfileImage.getProfileImage();
			if(profileImage!=null)
				return new File(getStorage(PROFILE_IMAGE), profileImage);
		}
		return null;
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
		String filename = generateUniqueFilename("PostImage", postId, FileExtensionUtils.extension(image));
		File file = new File(getStorage(POST_IMAGE), filename);

		if(!trySaveFile(image, file)) return null;

		return filename;
	}

	/**
	 * 파일 시스템에 저장된 포스트 이미지를 삭제합니다.
	 *
	 * @return 작업 실패 시 {@code false}. 삭제할 이미지가 없거나 작업 성공 시 {@code true}
	 */
	public boolean removePostImage(String filename){
		File file = new File(getStorage(POST_IMAGE), filename);
		return tryRemoveFile(file);
	}

	/**
	 * 전달받은 파일명에 해당하는 파일을 가져옵니다.
	 *
	 * @return 파일이 존재하지 않을 시 {@code null}
	 */
	@Nullable public File getPostImageFile(String filename){
		File file = new File(getStorage(POST_IMAGE), filename);
		return file.exists() ? file : null;
	}


	private File getStorage(String type){
		return new File(storageRoot, type);
	}

	private boolean trySaveFile(MultipartFile multipartFile, File destination){
		try{
			multipartFile.transferTo(destination);
			return true;
		}catch(Exception ex){
			logger.error("파일 '{}' 생성 중 오류 발생.", destination.getPath(), ex);
			return false;
		}
	}

	private boolean tryRemoveFile(File file){
		try{
			if(!file.delete()){
				logger.warn("파일 '{}'이 존재하지 않아 삭제할 수 없습니다.", file);
			}
			return true;
		}catch(SecurityException ex){
			logger.error("파일 '{}' 삭제가 허용되지 않았습니다.", file, ex);
			return false;
		}
	}

	private static final DateTimeFormatter DATE_TIME_FORMATTER =
			DateTimeFormatter.ofPattern("uuMMdd.HH.mm.ss.SSS")
			.withLocale(Locale.US)
			.withZone(ZoneId.of("UTC"));

	/**
	 * 타입과 데이터 키를 이용해 겹치지 않는(희망사항) 파일명을 생성합니다.
	 */
	private static String generateUniqueFilename(String type,
	                                             Object primaryIdentifier,
	                                             @Nullable String extension){
		StringBuilder stb = new StringBuilder()
				.append(type)
				.append('-')
				.append(primaryIdentifier)
				.append('-')
				.append(DATE_TIME_FORMATTER.format(LocalDateTime.now()))
				.append('-')
				.append(rng.nextInt(1024));

		if(extension!=null&&!extension.isEmpty()){
			stb.append('.').append(extension);
		}

		return stb.toString();
	}
}
