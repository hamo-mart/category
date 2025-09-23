package com.hamo.mart.category.service;

import com.hamo.mart.category.dto.CategoryRequest;
import com.hamo.mart.category.dto.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {


    CategoryResponse createCategory(CategoryRequest categoryRequest);

    CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest);

    void deleteCategory(Long categoryId);

    CategoryResponse getCategory(Long categoryId);

    Page<CategoryResponse> getCategories(Pageable pageable);
}
