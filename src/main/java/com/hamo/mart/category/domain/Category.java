package com.hamo.mart.category.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = false, unique = true, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateParent(Category parent) {
        this.parent = parent;
    }


}
