package com.catalogo.services;

import com.catalogo.dto.CategoryDTO;
import com.catalogo.dto.ProductDTO;
import com.catalogo.entities.Category;
import com.catalogo.entities.Product;
import com.catalogo.repositories.CategoryRepository;
import com.catalogo.repositories.ProductRepository;
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
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(PageRequest pageRequest){

        Page<Product> list = productRepository.findAll(pageRequest);

        return list.map(c -> new ProductDTO(c));
    }

    @Transactional(readOnly = true)
    public ProductDTO findBYId(Long id){

        Optional<Product> byId = productRepository.findById(id);
        Product product = byId.orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        return  new ProductDTO(product,product.getCategories());
    }


    @Transactional
    public ProductDTO insert(ProductDTO productDTO){

        Product product = new Product();

        copyDtoToEntity(productDTO, product);

        product = productRepository.save(product);

        return new ProductDTO(product, product.getCategories());
    }

    @Transactional
    public ProductDTO update(Long id,ProductDTO productDto) {

       try {
           Product product = productRepository.getOne(id);

           copyDtoToEntity(productDto, product);
           product = productRepository.save(product);

           return new ProductDTO(product,product.getCategories());

       }catch (EntityNotFoundException e){
           throw  new ResourceNotFoundException("Categoria não encontrada Id: "+id);
       }

    }

    public void delete(Long id) {

        try {
          productRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw  new ResourceNotFoundException("Categoria não encontrada Id: "+id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Violação de integridade");
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity){
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDate(dto.getDate());
        entity.setImgUrl(dto.getImgUrl());
        entity.setPrice(dto.getPrice());

        entity.getCategories().clear();
        for(CategoryDTO catDto: dto.getCategories()){
            Category category = categoryRepository.getOne(catDto.getId());
            entity.getCategories().add(category);
        }
    }
}

