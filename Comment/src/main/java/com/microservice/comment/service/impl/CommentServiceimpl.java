package com.microservice.comment.service.impl;

import com.microservice.comment.entity.Comment;
import com.microservice.comment.exception.ResourceNotFoundException;
import com.microservice.comment.payload.CommentDto;
import com.microservice.comment.payload.PostDto;
import com.microservice.comment.repository.CommentRepository;
import com.microservice.comment.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceimpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public CommentDto saveComment(CommentDto commentDto) {
        PostDto postid = restTemplate.getForObject("http://POST-SERVICE/api/posts/"+commentDto.getPostId(), PostDto.class);
        if (postid != null) {
            String commentid = UUID.randomUUID().toString();
            commentDto.setId(commentid);
            Comment comment = mapToEntity(commentDto);
            Comment save = commentRepository.save(comment);
            CommentDto dto = mapToDto(save);
            return dto;
        }else {
            throw  new ResourceNotFoundException("Not found");
        }
    }

    // return comments with postId

    @Override
    public List<Comment> getAllCommentsByPostId(String postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        if (comments.isEmpty()) {
            throw new ResourceNotFoundException("No comments found for post with ID: " + postId);
        }
        return comments;
    }

    public CommentDto mapToDto(Comment comment){

        return modelMapper.map(comment,CommentDto.class);
    }
    public Comment mapToEntity(CommentDto commentDto){

        return modelMapper.map(commentDto,Comment.class);
    }

}
