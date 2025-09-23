package com.hamo.mart.category.repository;

import com.hamo.mart.category.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    Category parentCategory;

    @BeforeEach
    void setUp() {
        parentCategory = new Category("식재료", null);
        testEntityManager.persist(parentCategory);
        testEntityManager.flush();
    }


    @Test
    @DisplayName("카테고리 등록")
    void testCreateCategory() {
        Category category  = new Category("과일", parentCategory);
        Category save = categoryRepository.save(category);

        assertNotNull(save);
        assertEquals("과일", save.getName());
        assertEquals(parentCategory, save.getParent());
    }

    @Test
    @DisplayName("카테고리 수정")
    void testUpdateCategory() {
        Category category  = new Category("과일", parentCategory);
        Category save = categoryRepository.save(category);

        save.updateName("신선식품");
        Category updated = categoryRepository.save(save);

        assertNotNull(updated);
        assertEquals("신선식품", updated.getName());
    }

    @Test
    @DisplayName("카테고리 삭제")
    void testDeleteCategory() {
        Category category  = new Category("과일", parentCategory);
        Category save = categoryRepository.save(category);

        categoryRepository.deleteById(save.getId());
        boolean exists = categoryRepository.existsById(save.getId());

        assertFalse(exists);
    }

    @Test
    @DisplayName("카테고리 조회")
    void testGetCategory() {
        Category category  = new Category("과일", parentCategory);
        Category save = categoryRepository.save(category);

        Category found = categoryRepository.findById(save.getId()).orElse(null);

        assertNotNull(found);
        assertEquals("과일", found.getName());
        assertEquals(parentCategory, found.getParent());
    }

    @Test
    @DisplayName("카테고리 목록 조회")
    void testGetCategories() {
        Category category1  = new Category("과일", parentCategory);
        Category category2  = new Category("채소", parentCategory);
        categoryRepository.save(category1);
        categoryRepository.save(category2);

        var categories = categoryRepository.findAll();

        assertEquals(3, categories.size()); // 부모 카테고리 포함
    }

}