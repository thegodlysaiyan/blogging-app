package com.project.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.blog.entities.Category;
import com.project.blog.entities.Post;
import com.project.blog.entities.User;
import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.payloads.PostDto;
import com.project.blog.payloads.PostResponse;
import com.project.blog.repositories.CategoryRepo;
import com.project.blog.repositories.PostRepo;
import com.project.blog.repositories.UserRepo;
import com.project.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, int userId, int categoryId) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));
		
		Post post = modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post createdPost = postRepo.save(post);
		
		return modelMapper.map(createdPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, int postId) {
		// TODO Auto-generated method stub
		Post postToUpdate = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post Not Found"));
		postToUpdate.setTitle(postDto.getTitle());
		postToUpdate.setContent(postDto.getContent());
		postToUpdate.setImageName(postDto.getImageName());
		
		Post updatedPost = postRepo.save(postToUpdate);
		
		return modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(int postId) {
		// TODO Auto-generated method stub
		Post postToDelete = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post Not Found"));
		postRepo.delete(postToDelete);
	}

	@Override
	public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir){
		// TODO Auto-generated method stub
		Sort sort = "ASC".equals(sortDir) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> page = postRepo.findAll(pageable);
		List<Post> posts = page.getContent();
		List<PostDto> postDtos = posts.stream()
				.map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse response = new PostResponse();
		
		response.setContent(postDtos);
		response.setPageNumber(page.getNumber());
		response.setPageSize(page.getSize());
		response.setTotalElements(page.getTotalElements());
		response.setTotalPages(page.getTotalPages());
		response.setLastPage(page.isLast());
		
		return response;
	}

	@Override
	public PostDto getPostById(int postId) {
		// TODO Auto-generated method stub
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post Not Found"));
		
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getAllPostsByCategory(int categoryId) {
		// TODO Auto-generated method stub
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));
		List<Post> posts = postRepo.findByCategory(category);
		List<PostDto> postDtos = posts.stream()
				.map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getAllPostsByUser(int userId) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		List<Post> posts = postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream()
				.map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> findByKeyword(String keyword) {
		// TODO Auto-generated method stub
		List<Post> posts = postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream()
				.map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
