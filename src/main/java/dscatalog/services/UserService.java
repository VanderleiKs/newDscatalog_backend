package dscatalog.services;

import dscatalog.dto.RoleDTO;
import dscatalog.dto.UserDTO;
import dscatalog.dto.UserInsertDTO;
import dscatalog.entities.Role;
import dscatalog.entities.User;
import dscatalog.repositories.RoleRepository;
import dscatalog.repositories.UserRepository;
import dscatalog.services.exceptions.DatabaseException;
import dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        return repository.findAll(pageable).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        return new UserDTO(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id)));
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        try {
            User user = new User();
            copyDtoToEntity(dto, user);
            user.setPassword(encoder.encode(dto.getPassword()));
            return new UserDTO(repository.save(user));
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Database integrity violation");
        }
    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
        try {
            User user = repository.getReferenceById(id);
            copyDtoToEntity(dto, user);
            return new UserDTO(repository.save(user));
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundException("Entity not found with id " + id);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Database integrity violation");
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException("Entity not found with id " + id);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Database integrity violation");
        }
    }

    private void copyDtoToEntity(UserDTO dto, User user) {
        user.setFirsName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        user.getRoles().clear();
        for (RoleDTO roleDTO : dto.getRoles()) {
            Role role = roleRepository.getReferenceById(roleDTO.getId());
            user.getRoles().add(role);
        }
    }

}
