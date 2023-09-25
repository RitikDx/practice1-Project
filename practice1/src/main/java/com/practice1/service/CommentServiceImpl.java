package com.practice1.service;

import com.practice1.entity.Comment;
import com.practice1.entity.Post;
import com.practice1.exception.ResourceNotFound;
import com.practice1.Dto.CommentDto;
import com.practice1.repository.CommentRepository;
import com.practice1.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{


    private CommentRepository commentRepo;
    private PostRepository postRepo;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo, ModelMapper modelMapper) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }
    private CommentDto maptoDto(Comment comment) {
        CommentDto dto = modelMapper.map(comment, CommentDto.class);
        return dto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);
        Post  post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id: " + postId)
        );
        comment.setPost(post);
        Comment savedComment = commentRepo.save(comment);

        CommentDto Dto = maptoDto(savedComment);
        return Dto;
    }

    @Override
    public CommentDto getComments(long postId) {
        Comment comment = commentRepo.findById(postId).orElseThrow(
                ()->new ResourceNotFound("Comment not found with id: "+ postId)
        );
        CommentDto commentDto=  maptoDto(comment);
        return commentDto;
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        postRepo.findById(postId).orElseThrow(
                ()->new ResourceNotFound("not found")
        );
        commentRepo.findById(commentId).orElseThrow(
                ()->new ResourceNotFound("Comment not found with id: "+ postId)
        );
        commentRepo.deleteById(commentId);
    }

    @Override
    public CommentDto updateComment(long postId, CommentDto commentDto) {
        Comment comment = commentRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFound("Comment not found with id: "+ postId)
        );
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment savedComment = commentRepo.save(comment);
        CommentDto updatedDto = maptoDto(savedComment);
        return updatedDto;
    }

    @Override
    public List<CommentDto> findByPostId(long postId) {
        Post post = postRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFound("Post not found with id: "+ postId)
        );

        List<Comment> comments = commentRepo.findByPostId(postId);
        List<CommentDto> commentDtos = comments.stream().map(comment ->
                maptoDto(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public CommentDto getCommentsById(Long postId, Long commentId) {
        Post post = postRepo.findById(postId).orElseThrow(
                ()->new ResourceNotFound("Post not found ith id: "+ postId)
        );

       Comment comment  =  commentRepo.findById(commentId).orElseThrow(
                ()-> new ResourceNotFound("Post not found ith id: "+ commentId)
        );

      CommentDto commentDto = maptoDto(comment);
        return commentDto;
    }

    @Override
    public List<CommentDto> getAllCommentsById() {
       List<Comment> comments  = commentRepo.findAll();
        List<CommentDto> commentDtos = comments.stream().map(comment -> maptoDto(comment)).collect(Collectors.toList());
        return commentDtos;
    }


}
