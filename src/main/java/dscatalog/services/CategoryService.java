package dscatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dscatalog.dto.CategoryDTO;
import dscatalog.entities.Category;
import dscatalog.repositories.CategoryRepository;
import dscatalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		return repository.findAll().stream().map(e -> new CategoryDTO(e)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		return new CategoryDTO(
				repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not Found")));
	}

	public CategoryDTO insert(CategoryDTO cat) {
		Category category = new Category();
		category.setName(cat.getName());
		CategoryDTO dto = new CategoryDTO(repository.save(category));
		return dto;
	}
}
