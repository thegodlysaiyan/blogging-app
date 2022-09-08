package com.project.blog.services;

import java.util.List;
import com.project.blog.payloads.UserDto;

public interface UserService {

	UserDto createUser(UserDto userDto);
	
	UserDto updateUser(UserDto userDto, int userId);
	
	UserDto getuserById(int userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(int userId);
	
}
