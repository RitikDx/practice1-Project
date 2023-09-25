package com.practice1.service;

import com.practice1.Dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId,CommentDto commentDto);

    CommentDto getComments(long postId);

    void deleteComment(long postId, long commentId);

    CommentDto updateComment(long postId, CommentDto commentDto);
    List<CommentDto> findByPostId(long postId);

    CommentDto getCommentsById(Long postId, Long commentId);

    List<CommentDto> getAllCommentsById();
}
