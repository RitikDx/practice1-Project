package com.practice1.controller;

import com.practice1.Dto.CommentDto;
import com.practice1.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable(value="postId") long postId,
            @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto),
                HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> getComments(
            @PathVariable(value="postId") long postId){
        CommentDto dto =commentService.getComments(postId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    // http://localhost:8080/api/posts/1/comments/3
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
        public ResponseEntity<String> deleteComment(
                @PathVariable(value = "postId") long postId,
                @PathVariable(value = "commentId") long commentId){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("comment is deleted with id: " + commentId , HttpStatus.OK);
        }

        @PutMapping("/posts/{postId}/comments")
        public ResponseEntity<CommentDto> updateComment(
                @PathVariable(value="postId") long postId,
                @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.updateComment(postId,commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
        }

        //http://localhost:8080/api/posts/1/comment
        @GetMapping("/posts/{postId}/comment")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId){
        return commentService.findByPostId(postId);
        }

    //http://localhost:8080/api/posts/1/comment/
    @GetMapping("/posts/{postId}/comment/{commentId}")
    public CommentDto getCommentsById(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId){
        return commentService.getCommentsById(postId, commentId);
    }
    //http://localhost:8080/api/comments
    @GetMapping("/comments")
    public List<CommentDto> getAllCommentsById(){
        List<CommentDto> dto =commentService.getAllCommentsById();
        return dto;
    }
}
