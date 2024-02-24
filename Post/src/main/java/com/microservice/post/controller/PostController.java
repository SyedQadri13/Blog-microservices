package com.microservice.post.controller;


import com.microservice.post.payload.PostDto;

import com.microservice.post.service.PostService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/posts")
public class PostController {


    private PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }
    //http://localhost:8081/api/posts
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        PostDto dto = postService.savePost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostByPostId(@PathVariable String postId) {
        PostDto dto = postService.findPostById(postId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostDto> deletePostById(@PathVariable String id) {
        PostDto deletedPost = postService.deletePostById(id);
        return new ResponseEntity<>(deletedPost, HttpStatus.OK);
    }
    //http://localhost:8081/api/posts/{postId}/comments
    @GetMapping("/{postId}/comments")
    @CircuitBreaker(name = "commentBreaker", fallbackMethod = "commentFallback")
    public ResponseEntity<PostDto> getPostWithComments(@PathVariable String postId){

        PostDto postDto = postService.getPostWithComments(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);

    }
    public ResponseEntity<PostDto> commentFallback(String postId, Exception ex) {
        System.out.println("Fallback is executed because service is down : "+ ex.getMessage());

        ex.printStackTrace();

        PostDto dto = new PostDto();
        dto.setId("1234");
        dto.setTitle("Service Down");
        dto.setContent("Service Down");
        dto.setDescription("Service Down");

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}

