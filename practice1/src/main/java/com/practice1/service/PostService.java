package com.practice1.service;

import com.practice1.Dto.PostDto;
import com.practice1.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto savePost(PostDto postDto);

    void deletePost(long id);

    PostDto updatePost(long id, PostDto postDto);

    PostDto getPostById(long id);

    PostResponse getPost(int pageNo, int pageSize, String sortBy, String sortDir);
}
