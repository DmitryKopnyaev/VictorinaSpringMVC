package com.dimentor.victorina.service;

import com.dimentor.victorina.model.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long id);
    void saveCategory(Category category);
    void updateCategory(Category category);
    Category deleteCategory(Long id);
    List<Category> getAll();

    Category getByValue(String value);
//    List<Category> getFromUrl();
}