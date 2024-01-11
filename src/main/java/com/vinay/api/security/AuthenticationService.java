package com.vinay.api.security;

import com.vinay.api.entity.Users;
import com.vinay.api.payload.JwtAuthenticationResponse;
import com.vinay.api.payload.LoginDto;
import com.vinay.api.payload.RefreshTokenRequest;
import com.vinay.api.payload.SignUpDto;

public interface AuthenticationService {
	
	Users signup(SignUpDto signUpDto);
	JwtAuthenticationResponse sigin(LoginDto loginDto);
//	JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
