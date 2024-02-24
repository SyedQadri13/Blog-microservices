package com.microservice.comment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalIdCache;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    private String id;
    private String name;
    private String email;
    private String body;
    private String postId;

}
