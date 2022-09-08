package com.project.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.entities.User;
import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.payloads.UserDto;
import com.project.blog.repositories.UserRepo;
import com.project.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = dtoToUser(userDto);
		User createdUser = userRepo.save(user);
		return userToDto(createdUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, int userId) {
		// TODO Auto-generated method stub
		User foundUser = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		foundUser.setName(userDto.getName());
		foundUser.setEmail(userDto.getEmail());
		foundUser.setPassword(userDto.getPassword());
		foundUser.setAbout(userDto.getAbout());
		
		User updatedUser = userRepo.save(foundUser);
		
		return userToDto(updatedUser);
	}

	@Override
	public UserDto getuserById(int userId) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		return userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> users = userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(int userId) {
		// TODO Auto-generated method stub
		User userToDelete =  userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		userRepo.delete(userToDelete);
	}
	
	private User dtoToUser(UserDto userDto) {
		
		User user = modelMapper.map(userDto, User.class);		
		return user;
	}
	
	private UserDto userToDto(User user) {
		
		UserDto  userDto = modelMapper.map(user, UserDto.class);
		return userDto;
	}

}
