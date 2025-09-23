package com.hamo.mart.category.service.impl;

import com.hamo.mart.category.domain.Category;
import com.hamo.mart.category.dto.CategoryRequest;
import com.hamo.mart.category.dto.CategoryResponse;
import com.hamo.mart.category.exception.CategoryNotFoundException;
import com.hamo.mart.category.repository.CategoryRepository;
import com.hamo.mart.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {

        Category parent = categoryRepository.findById(categoryRequest.getParentId()).orElse(null);
        Category category = new Category(categoryRequest.getName(), parent);
        Category save = categoryRepository.save(category);
        return toResponse(save);
    }

    @Transactional
    @Override
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        Category category = findCategoryById(categoryId);

        Category parent = null;

        if (categoryRequest.getParentId() != null) {
            parent = findCategoryById(categoryRequest.getParentId());
            category.updateParent(parent);
        }
        category.updateName(categoryRequest.getName());

        return toResponse(category);
    }

    @Transactional
    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryResponse getCategory(Long categoryId) {
            Category category = findCategoryById(categoryId);
        return toResponse(category);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CategoryResponse> getCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(this::toResponse);
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    private CategoryResponse toResponse(Category category) {
        Long parentId = category.getParent() != null ? category.getParent().getId() : null;
        return new CategoryResponse(category.getId(), category.getName(), parentId);
    }
}
