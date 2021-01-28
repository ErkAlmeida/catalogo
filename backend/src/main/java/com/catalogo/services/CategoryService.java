package com.catalogo.services;

import com.catalogo.dto.CategoryDTO;
import com.catalogo.entities.Category;
import com.catalogo.repositories.CategoryRepository;

import com.catalogo.services.exception.DatabaseException;
import com.catalogo.services.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(PageRequest pageRequest){

        Page<Category> list = categoryRepository.findAll(pageRequest);

        return list.map(c -> new CategoryDTO(c));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findBYId(Long id){

        Optional<Category> byId = categoryRepository.findById(id);

        return  new CategoryDTO(byId.orElseThrow(()-> new ResourceNotFoundException("Categoria não encontrada")));
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO categoryDTO){

        Category category = new Category();

        category.setName(categoryDTO.getName());

        category = categoryRepository.save(category);

        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO update(Long id,CategoryDTO categoryDto) {

       try {
           Category category = categoryRepository.getOne(id);

           category.setName(categoryDto.getName());
           category = categoryRepository.save(category);

           return new CategoryDTO(category);

       }catch (EntityNotFoundException e){
           throw  new ResourceNotFoundException("Categoria não encontrada Id: "+id);
       }

    }

    public void delete(Long id) {

        try {
          categoryRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw  new ResourceNotFoundException("Categoria não encontrada Id: "+id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Violação de integridade");
        }
    }
}

