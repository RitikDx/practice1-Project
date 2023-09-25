package com.practice1.controller;

import com.practice1.Dto.PostDto;
import com.practice1.payload.PostResponse;
import com.practice1.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    //http://localhost:8080/api/post
    @PreAuthorize("hasRole('ADMIN')")  //only an admin can access this part
    @PostMapping
    public ResponseEntity<?> savePost(@Valid @RequestBody PostDto postDto, BindingResult result){


        //<?> this generic return any type of data.
        //@VALID validates the data entered as per the logics provided in DTO class.
        //BindingResult store the error in variable *result*

        if(result.hasErrors()){             //hasError check whether there are errors or not.
            return new ResponseEntity<>
                    (result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = service.savePost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/post/2
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        service.deletePost(id);
         return new ResponseEntity<>("Post is Deleted", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @PathVariable("id") long id,@RequestBody PostDto postDto){
        PostDto dto = service.updatePost(id,postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id) {
        PostDto dto =service.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //http://localhost:8080/api/post?pageNo=0&pageSize=5&sortBy=title&sortDir=asc
    @GetMapping
    public PostResponse getPost(
            @RequestParam(value="pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir
    ){
       PostResponse postResponse = service.getPost(pageNo,pageSize,sortBy,sortDir);
       return postResponse;
    }
}
