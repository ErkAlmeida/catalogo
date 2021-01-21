package com.catalogo.services;

import com.catalogo.entities.Category;
import com.catalogo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> findAll(){

        return categoryRepository.findAll();
    }
}
