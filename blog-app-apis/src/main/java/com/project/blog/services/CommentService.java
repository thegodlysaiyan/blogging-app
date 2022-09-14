package com.project.blog.services;

import com.project.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, int postId, int userId);
	
	void deleteComment(int commentId);
}
