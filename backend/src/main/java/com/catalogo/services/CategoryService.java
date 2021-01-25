package com.catalogo.services;

import com.catalogo.dto.CategoryDTO;
import com.catalogo.entities.Category;
import com.catalogo.repositories.CategoryRepository;
import com.catalogo.services.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){

        List<Category> list = categoryRepository.findAll();

        return list.stream().map(c -> new CategoryDTO(c)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findBYId(Long id){

        Optional<Category> byId = categoryRepository.findById(id);

        return  new CategoryDTO(byId.orElseThrow(()-> new EntityNotFoundException("Categoria não encontrada")));
    }

    public CategoryDTO insert(CategoryDTO categoryDTO){

        Category category = new Category();

        category.setName(categoryDTO.getName());

        category = categoryRepository.save(category);

        return new CategoryDTO(category);
    }
}

