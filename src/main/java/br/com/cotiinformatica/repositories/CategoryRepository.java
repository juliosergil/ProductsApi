package br.com.cotiinformatica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	boolean existsByName(String name);
}
