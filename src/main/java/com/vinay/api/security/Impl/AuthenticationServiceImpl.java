package com.vinay.api.security.Impl;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vinay.api.entity.Role;
import com.vinay.api.entity.Users;
import com.vinay.api.payload.JwtAuthenticationResponse;
import com.vinay.api.payload.LoginDto;
import com.vinay.api.payload.RefreshTokenRequest;
import com.vinay.api.payload.SignUpDto;
import com.vinay.api.repository.UserRepository;
import com.vinay.api.security.AuthenticationService;
import com.vinay.api.security.JWTService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final AuthenticationManager authenticationManager;
	
	private final JWTService jwtService;

	@Override
	public Users signup(SignUpDto signUpDto) {
		Users user = new Users();
		user.setEmail(signUpDto.getEmail());
		user.setName(signUpDto.getName());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		user.setRole(Role.USER);

		return userRepository.save(user);
	}

	@Override
	public JwtAuthenticationResponse sigin(LoginDto loginDto) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginDto.getEmail(), loginDto.getPassword()));
		var user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(
				() -> new IllegalArgumentException("Invalid email or password"));
		var jwt = jwtService.generateToken(user);
//		var refeshToken = jwtService.generateRefreshToken(new HashMap<>(),user);
		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
		
		jwtAuthenticationResponse.setToken(jwt);
//		jwtAuthenticationResponse.setRefreshToken(refeshToken);
		
		return jwtAuthenticationResponse;
	}

//	@Override
//	public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
//		String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
//		Users user = userRepository.findByEmail(userEmail).orElseThrow();
//		if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
//			var jwt = jwtService.generateToken(user);
//			JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
//			jwtAuthenticationResponse.setToken(jwt);
//			jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
//			
//			return jwtAuthenticationResponse;
//		}
//		return null;
//	}

}
