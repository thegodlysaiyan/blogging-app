package com.project.blog.payloads;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.project.blog.entities.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
	
	@NotEmpty
	@Size(min = 2, message = "Name must be greater than 1 character.")
	private String name;
	
	@Email(message = "Email address invalid.")
	private String email;
	
	@NotEmpty
	@Size(min = 3, max = 10, message = "Password must be between 3 and 10 characters.")
	private String password;
	
	@NotEmpty
	private String about;
	
	private List<RoleDto> roles = new ArrayList<>();
}
