package com.vinay.api;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.vinay.api.entity.Role;
import com.vinay.api.entity.Users;
import com.vinay.api.repository.UserRepository;

@SpringBootApplication
public class TaskManagementApiApplication implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;


	public static void main(String[] args) {
		SpringApplication.run(TaskManagementApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Users adminAccount = userRepository.findByRole(Role.ADMIN);
		if(null == adminAccount) {
			Users user = new Users();
			user.setEmail("admin@gmail.com");
			user.setName("admin");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			user.setRole(Role.ADMIN);
			userRepository.save(user);
		}
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedMethods("*")
					.allowedOrigins("http://localhost:3000");
			}
		};
	}

}
