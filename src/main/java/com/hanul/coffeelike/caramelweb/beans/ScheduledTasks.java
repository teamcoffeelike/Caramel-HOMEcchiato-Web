package com.hanul.coffeelike.caramelweb.beans;

import com.hanul.coffeelike.caramelweb.service.FileService;
import com.hanul.coffeelike.caramelweb.service.UserAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks{
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

	@Autowired
	private UserAuthService userAuthService;
	@Autowired
	private FileService fileService;

	/**
	 * 하루에 한 번 실행. TODO: 테스트를 위해 30분마다 한 번 실행으로 변경
	 */
	@Scheduled(cron = "0 0/30 * * * ?")
	public void onceEachDay(){
		removeStaleAuthTokens();
		deleteUnreferencedFiles();
	}

	/**
	 * 마지막 접속 이후 일정 기간이 지난 로그인 토큰을 데이터베이스에서 삭제.
	 */
	private void removeStaleAuthTokens(){
		LOGGER.info("만료된 인증 토큰 제거 중...");
		int removed = userAuthService.removeStaleAuthTokens();
		if(removed>0){
			LOGGER.info("인증 토큰 "+removed+"개 제거됨.");
		}
	}

	private void deleteUnreferencedFiles(){
		LOGGER.info("쓰이지 않는 이미지 파일 제거 중...");
		// TODO
	}
}
