package com.hanul.coffeelike.caramelweb.beans;

import com.hanul.coffeelike.caramelweb.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks{
	@Autowired
	private UserAuthService userAuthService;

	/**
	 * 하루에 한 번 실행. 마지막 접속 이후 일정 기간이 지난 로그인 토큰을 데이터베이스에서 삭제.
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void removeStaleAuthTokens(){
		System.out.println("Removing stale auth tokens...");
		int removed = userAuthService.removeStaleAuthTokens();
		if(removed>0){
			System.out.println("Removed "+removed+" stale auth tokens.");
		}
	}
}
