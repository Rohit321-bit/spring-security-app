package com.sa1.SecurityApp;

import com.sa1.SecurityApp.entities.User;
import com.sa1.SecurityApp.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityApplicationTests {
	@Autowired
	JwtService jwtService;
	@Test
	void contextLoads() {
		User user=new User(4L,"Rohit","abc@gmail.com","root");
		String generateToken= jwtService.getToken(user);
		System.out.println(generateToken);
		Long userId= jwtService.getUserId(generateToken);
		System.out.println(userId);
	}

}
