package com.microservice.comment.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CommentDto {


    private String id;
    private String name;
    private String email;
    private String body;
    private String postId;


}
