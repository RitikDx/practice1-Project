package com.practice1.repository;

import com.practice1.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //Custom method to find.
    List<Comment> findByPostId(long postId);
}
