package com.practice1.service;

import com.practice1.Dto.PostDto;
import com.practice1.entity.Post;
import com.practice1.exception.ResourceNotFound;
import com.practice1.payload.PostResponse;
import com.practice1.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{

    private PostRepository repo;
    private ModelMapper modelMapper;


    public PostServiceImpl(PostRepository repo,ModelMapper modelMapper) {
        this.repo = repo;
        this.modelMapper = modelMapper;
    }

    PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;
    }

    Post mapToEntity(PostDto dto){
        Post post=modelMapper.map(dto,Post.class);
//        Post post = new Post();
//        post.setId(dto.getId());
//        post.setTitle(dto.getTitle());
//        post.setDescription(dto.getDescription());
//        post.setContent(dto.getContent());

        return post;
    }
    @Override
    public PostDto savePost( PostDto postDto) {

        Post post = mapToEntity(postDto);

        Post savedPost= repo.save(post);

        PostDto dto= mapToDto(savedPost);
        return dto;
    }

    @Override
    public void deletePost(long id) {
        repo.deleteById(id);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = repo.findById(id).orElseThrow(
                ()->new ResourceNotFound("Post not found with id " + id)
        );
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post updatedPost = repo.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = repo.findById(id).orElseThrow(
                ()->new ResourceNotFound("Post not found with id "+id)
        );
        PostDto dto = mapToDto(post);
        return dto;
    }
    @Override
    public PostResponse getPost(int pageNo, int pageSize, String sortBy,String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();
       Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        Page<Post> pagePost = repo.findAll(pageable);
        List<Post> posts = pagePost.getContent();
        List<PostDto> postDtos = posts.stream()
                .map(this::mapToDto).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setPostDto(postDtos);
        postResponse.setPageNo(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElement(pagePost.getNumberOfElements());
        postResponse.setLast(pagePost.isLast());
        postResponse.setTotalPages(pagePost.getTotalPages());

        return postResponse;
    }

}