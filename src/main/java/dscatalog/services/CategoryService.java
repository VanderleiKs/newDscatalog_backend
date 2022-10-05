package dscatalog.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dscatalog.dto.CategoryDTO;
import dscatalog.entities.Category;
import dscatalog.repositories.CategoryRepository;
import dscatalog.services.exceptions.DatabaseException;
import dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
		return repository.findAll(pageRequest).map(e -> new CategoryDTO(e));
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		return new CategoryDTO(
				repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not Found")));
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO cat) {
		Category category = new Category();
		category.setName(cat.getName());
		CategoryDTO dto = new CategoryDTO(repository.save(category));
		return dto;
	}
	
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
		Category cat = repository.getReferenceById(id);
		cat.setName(dto.getName());
		return new CategoryDTO(repository.save(cat));
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Entity not Found with id " + id);
		}
	}

	public void delete(Long id) {
		try {
		repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException ex) {
			throw new ResourceNotFoundException("entity not found");
		}
		catch(DataIntegrityViolationException ex) {
			throw new DatabaseException("Database integrity violation");
		}
	}
}
