package com.jorge.jwtnewtest;

import com.jorge.jwtnewtest.model.Role;
import com.jorge.jwtnewtest.model.User;
import com.jorge.jwtnewtest.repository.RoleRepository;
import com.jorge.jwtnewtest.repository.UserRepository;
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
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
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
		userRepository.save(jorgeUser);
		log.info("User Saved");

		log.info("Trying to Save Admin User...");
		var adminUser = new User();
		adminUser.setUsername("Admin");
		adminUser.setPassword(passwordEncoder.encode("admin123"));

		var adminRole = Role.builder()
				.name("ADMIN")
				.users(Set.of(adminUser))
				.build();
		adminUser.getRoles().add(adminRole);

		userRepository.save(adminUser);

		log.info("Admin User Saved Successfully");
	}
}
