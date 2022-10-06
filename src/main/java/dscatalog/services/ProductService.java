package dscatalog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dscatalog.dto.ProductDTO;
import dscatalog.entities.Product;
import dscatalog.repositories.ProductRepository;
import dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(PageRequest pageRequest) {
		Page<ProductDTO> page = repository.findAll(pageRequest).map((prod) -> new ProductDTO(prod, prod.getCategories()));
		return page;
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product prod = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(prod, prod.getCategories());
	}
	
	public ProductDTO insert(ProductDTO dto) {
		return null;
	}

}
