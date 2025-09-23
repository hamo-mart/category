package com.hamo.mart.category.service.impl;

import com.hamo.mart.category.domain.Category;
import com.hamo.mart.category.dto.CategoryRequest;
import com.hamo.mart.category.dto.CategoryResponse;
import com.hamo.mart.category.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;


    @Test
    @DisplayName("카테고리 등록")
    void testCreateCategory() {
        CategoryRequest categoryRequest = new CategoryRequest("과일", 1L);
        Category parentCategory = new Category("식재료", null);
        when(categoryRepository.findById(categoryRequest.getParentId())).thenReturn(Optional.of(parentCategory));
        Category category = new Category(categoryRequest.getName(), parentCategory);
        when(categoryRepository.save(any())).thenReturn(category);
        var response = categoryService.createCategory(categoryRequest);
        assertNotNull(response);
        assertEquals("과일", response.getName());
        assertEquals(parentCategory.getId(), response.getParentId());

    }

    @Test
    @DisplayName("카테고리 수정")
    void testUpdateCategory() {
        CategoryRequest categoryRequest = new CategoryRequest("신선식품", 2L);
        Category parentCategory = new Category("식재료", null);
        Category newParentCategory = new Category("생활용품", null);
        Category existingCategory = new Category("과일", parentCategory);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(newParentCategory));
        var response = categoryService.updateCategory(1L, categoryRequest);
        assertNotNull(response);
        assertEquals("신선식품", response.getName());
        assertEquals(newParentCategory.getId(), response.getParentId());
    }

    @Test
    @DisplayName("카테고리 삭제")
    void testDeleteCategory() {

        Mockito.doNothing().when(categoryRepository).deleteById(1L);
        categoryService.deleteCategory(1L);
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("카테고리 단건 조회")
    void testGetCategory() {
        Category category = new Category("식재료", null);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        CategoryResponse categoryResponse = categoryService.getCategory(1L);

        assertNotNull(categoryResponse);
        assertEquals("식재료", categoryResponse.getName());
        assertNull(categoryResponse.getParentId());
    }

    @Test
    @DisplayName("카테고리 목록 조회")
    void testGetCategories() {
        Pageable pageable = PageRequest.of(0, 10);
        Category category1 = new Category("과일", null);
        Category category2 = new Category("채소", null);

        List<Category> categories = List.of(category1, category2);
        when(categoryRepository.findAll(pageable)).thenReturn(new PageImpl<>(categories, pageable, categories.size()));

        var response = categoryService.getCategories(pageable);

        assertNotNull(response);
        assertEquals(2, response.getTotalElements());
        assertEquals("과일", response.getContent().get(0).getName());
        assertEquals("채소", response.getContent().get(1).getName());

    }



}