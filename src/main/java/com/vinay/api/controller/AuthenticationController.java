package com.vinay.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinay.api.entity.Users;
import com.vinay.api.payload.JwtAuthenticationResponse;
import com.vinay.api.payload.LoginDto;
import com.vinay.api.payload.RefreshTokenRequest;
import com.vinay.api.payload.SignUpDto;
import com.vinay.api.security.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	
	
	private final AuthenticationService authenticationService;
	
	@PostMapping("/signup")
	public ResponseEntity<Users> signup(@RequestBody SignUpDto signUpDto){
		
		return ResponseEntity.ok(authenticationService.signup(signUpDto));
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthenticationResponse> sigin(@RequestBody LoginDto loginDto){
		return ResponseEntity.ok(authenticationService.sigin(loginDto));
	}
	
//	@PostMapping("/refresh")
//	public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
//		return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
//	}
	
	

}
