package com.microservice.post.service.impl;

import com.microservice.post.config.RestTemplateConfig;
import com.microservice.post.entity.Post;
import com.microservice.post.exception.ResourceNotFoundException;
import com.microservice.post.payload.PostDto;
import com.microservice.post.repository.PostRepository;
import com.microservice.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private RestTemplateConfig restTemplate;

    @Override
    public PostDto savePost(PostDto postDto) {
        // Map DTO to entity
        Post post = mapToEntity(postDto);

        // Generate UUID for the ID
        String postId = UUID.randomUUID().toString();
        post.setId(postId);

        // Save the entity
        Post savedPost = postRepository.save(post);

        // Map the saved entity back to DTO
        PostDto dto = mapToDto(savedPost);

        return dto;
    }

    @Override
    public PostDto findPostById(String postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with ID: " + postId)
        );
        PostDto dto = mapToDto(post);

        return dto;
    }

    @Override
    public PostDto deletePostById(String id) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with ID: " + id)
        );
        postRepository.deleteById(id);
        PostDto deletedPostDto = mapToDto(post);

        return deletedPostDto;
    }

    @Override
    public PostDto getPostWithComments(String postId) {

        Post post = postRepository.findById(postId).get();
        ArrayList comments = restTemplate.getRestTemplate().getForObject("http://COMMENT-SERVICE/api/comment/" + postId, ArrayList.class);
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        dto.setComments(comments);
        return dto;
    }


    PostDto mapToDto(Post post){
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;

    }

    Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

}
