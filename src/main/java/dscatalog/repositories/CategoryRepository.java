package dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dscatalog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}