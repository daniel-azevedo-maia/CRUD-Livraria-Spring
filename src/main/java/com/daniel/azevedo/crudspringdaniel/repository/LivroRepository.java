package com.daniel.azevedo.crudspringdaniel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daniel.azevedo.crudspringdaniel.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

	@Query("SELECT l FROM Livro l WHERE l.autor = ?1")
	List<Livro> findByAutor(String autor);

	@Query("SELECT l FROM Livro l ORDER BY l.anoPublicacao ASC")
	List<Livro> findAllOrderByAnoPublicacaoAsc();

	@Query("SELECT l FROM Livro l ORDER BY l.anoPublicacao DESC")
	List<Livro> findAllOrderByAnoPublicacaoDesc();

	@Query("SELECT DISTINCT l.autor FROM Livro l ORDER BY l.autor ASC")
	List<String> findAllAutoresOrderByNomeAsc();

	@Query("SELECT DISTINCT l.autor FROM Livro l ORDER BY l.autor DESC")
	List<String> findAllAutoresOrderByNomeDesc();

}