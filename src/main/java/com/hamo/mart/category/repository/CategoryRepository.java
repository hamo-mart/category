package com.hamo.mart.category.repository;

import com.hamo.mart.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
