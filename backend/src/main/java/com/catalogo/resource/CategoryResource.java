package com.catalogo.resource;

import com.catalogo.dto.CategoryDTO;
import com.catalogo.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
    ){

        PageRequest pageRequest = PageRequest.of(page,linesPerPage,Sort.Direction.valueOf(direction), orderBy);
        Page<CategoryDTO> list = categoryService.findAll(pageRequest);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value= "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){

        CategoryDTO categoryDTO = categoryService.findBYId(id);

        return ResponseEntity.ok().body(categoryDTO);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO categoryDto){

        categoryDto = categoryService.insert(categoryDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(categoryDto.getId()).toUri();

        return ResponseEntity.created(uri).body(categoryDto);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO categoryDto){

        categoryDto = categoryService.update(id, categoryDto);

        return ResponseEntity.ok().body(categoryDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id){

        categoryService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
