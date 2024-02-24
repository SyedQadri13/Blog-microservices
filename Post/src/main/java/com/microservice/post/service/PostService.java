package com.microservice.post.service;

import com.microservice.post.payload.PostDto;
import com.microservice.post.repository.PostRepository;

public interface PostService {

    PostDto savePost(PostDto postDto);

    PostDto findPostById(String postId);

    PostDto deletePostById(String id);

    PostDto getPostWithComments(String postId);
}
