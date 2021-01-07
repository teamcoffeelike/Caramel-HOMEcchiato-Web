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
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

@Service
public class FileService{
	private static final String PROFILE_IMAGE = "profileImage";
	private static final String POST_IMAGE = "postImage";

	private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

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

		String name = trySaveFile(multipartFile, getStorage(PROFILE_IMAGE), generateUniqueFilename("ProfileImage", userId));

		// 멀티파트 내용물을 스토리지로 이동
		if(name==null) return false;

		// DB에 삽입
		if(!fileDAO.setUserProfileImage(userId, name)){
			// DB에 삽입 실패, 파일 삭제
			tryRemoveFile(getStorage(PROFILE_IMAGE), name);
			return false;
		}
		// 기존 파일 삭제
		String prevFile = prevProfileImage.getProfileImage();
		if(prevFile!=null){
			tryRemoveFile(getStorage(PROFILE_IMAGE), prevFile);
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
			return tryRemoveFile(getStorage(PROFILE_IMAGE), profileImage);
		else return true;
	}

	/**
	 * 전달받은 유저의 프로필 이미지를 가져옵니다.
	 *
	 * @return 부착된 파일이 존재하지 않을 시 {@code null}. null이 아닐 경우에도 실제 파일이 없을 수 있음.
	 */
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
		return trySaveFile(image, getStorage(POST_IMAGE), generateUniqueFilename("PostImage", postId));
	}

	/**
	 * 파일 시스템에 저장된 포스트 이미지를 삭제합니다.
	 *
	 * @return 작업 실패 시 {@code false}. 삭제할 이미지가 없거나 작업 성공 시 {@code true}
	 */
	public boolean removePostImage(String filename){
		return tryRemoveFile(getStorage(POST_IMAGE), filename);
	}

	/**
	 * 전달받은 파일명에 해당하는 포스트 이미지 파일을 가져옵니다.
	 *
	 * @return 부착된 파일이 존재하지 않을 시 {@code null}. null이 아닐 경우에도 실제 파일이 없을 수 있음.
	 */
	public File getPostImageFile(String filename){
		return new File(getStorage(POST_IMAGE), filename);
	}


	private File getStorage(String type){
		return new File(storageRoot, type);
	}

	@Nullable private String trySaveFile(MultipartFile multipartFile, File storage, String generatedName){
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
			try{
				if(!dest.delete()){
					LOGGER.error("임시 파일 '{} ({})' 삭제 중 오류 발생: 삭제 불가.", dest.getPath(), dest.getAbsolutePath());
				}
			}catch(Exception e2){
				LOGGER.error("임시 파일 '{} ({})' 삭제 중 오류 발생.", dest.getPath(), dest.getAbsolutePath(), e2);
			}
			return null;
		}
		LOGGER.debug("이미지 파일 저장: {} ({})", dest2, dest2.getAbsolutePath());
		return generatedName+"."+ext;
	}

	private boolean tryRemoveFile(File storage, String filename){
		File file = new File(storage, filename);
		try{
			if(!file.delete()){
				LOGGER.error("파일 '{}'이 존재하지 않아 삭제할 수 없습니다.", file);
			}
			return true;
		}catch(SecurityException ex){
			LOGGER.error("파일 '{}' 삭제가 허용되지 않았습니다.", file, ex);
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
	                                             Object primaryIdentifier){
		return type+'-'+
				primaryIdentifier+'-'+
				DATE_TIME_FORMATTER.format(LocalDateTime.now())+'-'+
				rng.nextInt(1024);
	}
}
