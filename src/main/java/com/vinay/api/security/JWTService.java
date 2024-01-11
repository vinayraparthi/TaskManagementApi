package com.vinay.api.security;

import java.util.HashMap;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
	
    String extractUsername(String token);
	
	String generateToken(UserDetails userDetails);
	
	public boolean isTokenValid(String token, UserDetails userDetails);

//	String generateRefreshToken(HashMap<String , Object> extraClaims, UserDetails userDetails);


}
