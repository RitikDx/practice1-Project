package com.practice1.Dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;

    @NotEmpty
    @Size(min=2,message = "Title should at least >2 characters")
    private String title;

    @NotEmpty
    @Size(min=2,message = "Description should at least >2 characters")
    private String description;

    @NotEmpty
    @Size(min=2,message = "Content should at least >2 characters")
    private String content;
}
