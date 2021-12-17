package com.catalogo.services;

import com.catalogo.dto.RoleDTO;
import com.catalogo.dto.UserDTO;
import com.catalogo.dto.UserInsertDTO;
import com.catalogo.entities.Role;
import com.catalogo.entities.User;
import com.catalogo.repositories.RoleRepository;
import com.catalogo.repositories.UserRepository;
import com.catalogo.services.exception.DatabaseException;
import com.catalogo.services.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(PageRequest pageRequest){
        Page<User> list = userRepository.findAll(pageRequest);
        return list.map(c -> new UserDTO(c));
    }

    @Transactional(readOnly = true)
    public UserDTO findBYId(Long id){
        Optional<User> byId = userRepository.findById(id);
        User user = byId.orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        return  new UserDTO(user);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto){
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity = userRepository.save(entity);
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id,UserDTO userDto) {
       try {
           User entity = userRepository.getOne(id);
           copyDtoToEntity(userDto, entity);
           entity = userRepository.save(entity);
           return new UserDTO(entity);
       }catch (EntityNotFoundException e){
           throw  new ResourceNotFoundException("Categoria não encontrada Id: "+id);
       }
    }

    public void delete(Long id) {
        try {
          userRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw  new ResourceNotFoundException("Categoria não encontrada Id: "+id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Violação de integridade");
        }
    }

    private void copyDtoToEntity(UserDTO dto, User entity){
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();
        for(RoleDTO roleDTO: dto.getRoles()){
            Role role = roleRepository.getOne(roleDTO.getId());
            entity.getRoles().add(role);
        }
    }
}

