package com.project.blog.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.blog.config.Constants;
import com.project.blog.payloads.ApiResponse;
import com.project.blog.payloads.PostDto;
import com.project.blog.payloads.PostResponse;
import com.project.blog.services.FileService;
import com.project.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, 
			@PathVariable int userId, 
			@PathVariable int categoryId) {
		
		PostDto createdPost = postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}
	
	@GetMapping("/posts/category/{catId}")
	public ResponseEntity<List<PostDto>> getAllPostsByCategory(@PathVariable int catId){
		List<PostDto> postDtos = postService.getAllPostsByCategory(catId);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
	}
	
	@GetMapping("/posts/user/{userId}")
	public ResponseEntity<List<PostDto>> getAllPostsByUser(@PathVariable int userId){
		List<PostDto> postDtos = postService.getAllPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = Constants.defaultPageNumber, required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.defaultPageSize, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = Constants.defaultSortBy, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = Constants.defaultSortDir, required = false) String sortDir){
		PostResponse response = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable int postId){
		PostDto postDto = postService.getPostById(postId);
		
		return ResponseEntity.ok(postDto);
	}
	
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable int postId){
		PostDto updatedPostDto = postService.updatePost(postDto, postId);
		return ResponseEntity.ok(updatedPostDto);
	}
	
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable int postId){
		postService.deletePost(postId);
		return ResponseEntity.ok(new ApiResponse("Post Deleted Successfully", true));
	}
	
	@GetMapping("/posts/search")
	public ResponseEntity<List<PostDto>> searchPosts(@RequestParam(value = "query") String keyword){
		List<PostDto> postDtos = postService.findByKeyword(keyword);
		return ResponseEntity.ok(postDtos);
	}
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable int postId) throws IOException{
		
		PostDto postDto = postService.getPostById(postId);
		
		String fileName = fileService.uploadImage(path, image);
		
		postDto.setImageName(fileName);
		PostDto updatePostDto = postService.updatePost(postDto, postId);
		
		return ResponseEntity.ok(updatePostDto);
	}
	
	@GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
	public void getImage(
			@PathVariable String imageName,
			HttpServletResponse response) throws IOException {
		
		InputStream resource = fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
	}
}
