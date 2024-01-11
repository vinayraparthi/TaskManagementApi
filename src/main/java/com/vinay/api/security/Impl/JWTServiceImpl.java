package com.vinay.api.security.Impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.vinay.api.entity.Users;
import com.vinay.api.exception.UserNotFound;
import com.vinay.api.repository.UserRepository;
import com.vinay.api.security.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl implements JWTService{
	
	@Autowired
	private UserRepository userRepository;
    public String generateToken(UserDetails userDetails) {
	   String email = userDetails.getUsername();
	   Users user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFound("usernotfound"));
		return Jwts.builder().setSubject(userDetails.getUsername())
				.claim("userId", user.getId())
				.claim("username", user.getName())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() +604800000))
				.signWith(getSignkey(), SignatureAlgorithm.HS256)
				.compact();
	}

	@Override
	public String extractUsername(String token) {
		
		return extractClaim(token, Claims::getSubject);
	}
	
	private <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
		final Claims claims = extractAallClaim(token);
		return claimsResolver.apply(claims);
		
	}
	
	private Key getSignkey() {
		byte[] key = Decoders.BASE64.decode("413F4428472B4B62506553685660597033733676397924422645294840406351");
		return Keys.hmacShaKeyFor(key);
	}
	
	private Claims extractAallClaim(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignkey()).build().parseClaimsJws(token).getBody();
	}

	@Override
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}

}
