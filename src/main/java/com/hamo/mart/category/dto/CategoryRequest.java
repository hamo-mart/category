package com.hamo.mart.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CategoryRequest {

    @NotBlank
    private String name;

    private Long parentId;
}
