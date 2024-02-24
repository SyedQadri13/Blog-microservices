package com.microservice.comment.service;

import com.microservice.comment.entity.Comment;
import com.microservice.comment.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto saveComment(CommentDto commentDto);

    List<Comment> getAllCommentsByPostId(String postId);
}
