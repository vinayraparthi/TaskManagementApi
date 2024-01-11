package com.vinay.api.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
	
	private String email;
	private String password;

}
