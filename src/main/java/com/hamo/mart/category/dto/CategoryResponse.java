package com.hamo.mart.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CategoryResponse {
    private Long id;
    private String name;
    private Long parentId;
}
