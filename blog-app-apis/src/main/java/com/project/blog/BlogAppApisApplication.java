package com.project.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.blog.config.Constants;
import com.project.blog.entities.Role;
import com.project.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(passwordEncoder.encode("pass"));
		try {
			Role adminRole = new Role(Constants.ADMIN_USER_ROLE, "ADMIN_USER");
			Role normalRole = new Role(Constants.NORMAL_USER_ROLE, "NORMAL_USER");
			
			List<Role> roles = List.of(adminRole, normalRole);
			
			List<Role> resultList = roleRepo.saveAll(roles);
			
			resultList.forEach(role -> System.out.print(role.getName()));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
