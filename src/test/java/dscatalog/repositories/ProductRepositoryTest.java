package dscatalog.repositories;

import dscatalog.Factory;
import dscatalog.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTest {

	long existingId;
	long nonExistId;
	@Autowired
	private ProductRepository repository;

	@BeforeEach
	void setUp(){
		existingId = 1L;
		nonExistId = 1000L;
	}
	
	@Test
	public void deleteShouldDeleteProductWhenIdExists() {
		repository.deleteById(existingId);
		Optional<Product> prod = repository.findById(existingId);
		Assertions.assertFalse(prod.isPresent());
	}

	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist(){
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> repository.deleteById(nonExistId));
	}

	@Test
	public void saveShouldPersistObject(){
		Product prod = Factory.createProduct();
		prod = repository.save(prod);

		Assertions.assertNotNull(prod.getId());
		Assertions.assertEquals(26, prod.getId());
	}

	@Test
	public void findByIdShouldReturnNonEmptyWhenIdExist(){
		Optional<Product> prod = repository.findById(existingId);
		Assertions.assertTrue(prod.isPresent());
	}

	@Test
	public void findByIdShouldReturnEmptyWhenIdDoesNotExist(){
		Optional<Product> prod = repository.findById(nonExistId);
		Assertions.assertTrue(prod.isEmpty());
	}

}
