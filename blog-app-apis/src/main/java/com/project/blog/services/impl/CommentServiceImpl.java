package com.project.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.entities.Comment;
import com.project.blog.entities.Post;
import com.project.blog.entities.User;
import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.payloads.CommentDto;
import com.project.blog.repositories.CommentRepo;
import com.project.blog.repositories.PostRepo;
import com.project.blog.repositories.UserRepo;
import com.project.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, int postId, int userId) {
		// TODO Auto-generated method stub
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post Not Found"));
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		
		Comment comment = modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		
		Comment createdComment = commentRepo.save(comment);
		return modelMapper.map(createdComment, CommentDto.class);
	}

	@Override
	public void deleteComment(int commentId) {
		// TODO Auto-generated method stub
		Comment commentToDelete = commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment Not Found"));
		commentRepo.delete(commentToDelete);
	}

}
