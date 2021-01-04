package com.hanul.coffeelike.caramelweb.service;

import com.hanul.coffeelike.caramelweb.dao.UserAuthDAO;
import com.hanul.coffeelike.caramelweb.data.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserAuthService{
	@Autowired
	private UserAuthDAO dao;

	public UUID generateAuthToken(int userId){
		UUID uuid = UUID.randomUUID();
		dao.addAuthToken(uuid, userId);
		return uuid;
	}

	public void removeAuthToken(UUID authToken){
		dao.removeAuthToken(authToken);
	}

	@Nullable public AuthToken getAuthTokenInformation(UUID uuid){
		return dao.getAuthTokenInformation(uuid);
	}

	public void updateAuthToken(UUID uuid){
		dao.updateAuthToken(uuid);
	}

	public int removeStaleAuthTokens(){
		return dao.removeStaleAuthTokens();
	}
}
