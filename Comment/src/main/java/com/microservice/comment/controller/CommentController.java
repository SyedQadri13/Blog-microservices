package com.microservice.comment.controller;

import com.microservice.comment.entity.Comment;
import com.microservice.comment.payload.CommentDto;
import com.microservice.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        CommentDto dto = commentService.saveComment(commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }

    //return comments with postId
    @GetMapping("{postId}")
    public List<Comment> getAllCommentsByPostId(@PathVariable String postId){

        List<Comment> comments = commentService.getAllCommentsByPostId(postId);
        return comments;

    }


}