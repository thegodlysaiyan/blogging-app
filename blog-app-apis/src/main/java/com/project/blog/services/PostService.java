package com.project.blog.services;

import java.util.List;

import com.project.blog.payloads.PostDto;
import com.project.blog.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto, int userId, int categoryId);
	
	PostDto updatePost(PostDto postDto, int postId);
	
	void deletePost(int postId);
	
	PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	PostDto getPostById(int postId);
	
	List<PostDto> getAllPostsByCategory(int categoryId);
	
	List<PostDto> getAllPostsByUser(int userId);
	
	List<PostDto> findByKeyword(String keyword);
	
}
