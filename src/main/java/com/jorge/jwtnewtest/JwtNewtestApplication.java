package com.jorge.jwtnewtest;

import com.jorge.jwtnewtest.model.Role;
import com.jorge.jwtnewtest.model.User;
import com.jorge.jwtnewtest.repository.UserRepository;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
@Slf4j
public class JwtNewtestApplication implements CommandLineRunner {
	@Autowired
	UserRepository repository;
	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(JwtNewtestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Trying to Save User...");
		var jorgeUser = new User();
		jorgeUser.setUsername("Jorge");
		jorgeUser.setPassword(passwordEncoder.encode("jorge123"));

		var jorgeRole = Role.builder()
				.name("USER")
				.users(Set.of(jorgeUser))
				.build();

		jorgeUser.getRoles().add(jorgeRole);
		repository.save(jorgeUser);

		log.info("User Saved");
	}
}
