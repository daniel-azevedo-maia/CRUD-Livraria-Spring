package com.daniel.azevedo.crudspringdaniel.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.azevedo.crudspringdaniel.model.Livro;
import com.daniel.azevedo.crudspringdaniel.repository.LivroRepository;

@RestController
@RequestMapping("/livros")
public class LivroController {

	// REFATORE OS CÓDIGOS ABAIXO PARA MELHORAR AS RESPOSTAS HTTP
	// USANDO RESPOSTAS COMO "CREATED", 200, OK, NOT FOUND ETC.
	// USE TAMBÉM O OPTIONAL, QUANDO couber.

	@Autowired
	private LivroRepository livroRepository;

	@PostMapping
	public ResponseEntity<Livro> criarLivro(@RequestBody Livro livro) {
		Livro novoLivro = livroRepository.save(livro);

		return ResponseEntity.ok(novoLivro);
	}

	@GetMapping
	public ResponseEntity<List<Livro>> listarLivros() {
		List<Livro> livros = livroRepository.findAll();
		return ResponseEntity.ok(livros);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Livro> buscarLivroPorId(@PathVariable Long id) {
		Optional<Livro> livro = livroRepository.findById(id);
		return livro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Livro> atualizarLivro(@PathVariable Long id, 
			@RequestBody Livro livroAtualizado) {
		
		Optional<Livro> livroAtual = livroRepository.findById(id);
		
		if (livroAtual.isPresent()) {
			
			Livro livro = livroAtual.get();
			BeanUtils.copyProperties(livroAtualizado, livro, "id");
			
			/*
			Livro livro = livroAtual.get();
			
			livro.setTitulo(livroAtualizado.getTitulo());
			livro.setAutor(livroAtualizado.getAutor());
			livro.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
			*/
			
			livroRepository.save(livro);
			return ResponseEntity.ok(livro);
			
		
		}
		
		return ResponseEntity.notFound().build();		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deletarLivro(@PathVariable Long id) {
		Optional<Livro> livroAtual = livroRepository.findById(id);
		if (livroAtual.isPresent()) {
			livroRepository.delete(livroAtual.get());
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/autor/{autor}")
	public ResponseEntity<List<Livro>> listarLivrosPorAutor(@PathVariable String autor) {
		List<Livro> livros = livroRepository.findByAutor(autor);
		if (livros.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(livros);
	}
	
	@GetMapping("/data-publicacao/crescente")
	public ResponseEntity<List<Livro>> listarLivrosPorDataPublicacaoCrescente() {
		List<Livro> livros = livroRepository.findAllOrderByAnoPublicacaoAsc();
		return ResponseEntity.ok(livros);
	}
	
	@GetMapping("/data-publicacao/decrescente")
	public ResponseEntity<List<Livro>> listarLivroPorDataPublicacaoDecrescente() {
		List<Livro> livros = livroRepository.findAllOrderByAnoPublicacaoDesc();
		return ResponseEntity.ok(livros);
	}
	
	@GetMapping("/autores/crescente")
	public ResponseEntity<List<String>> listarAutoresPorNomeCrescente() {
		List<String> autores = livroRepository.findAllAutoresOrderByNomeAsc();
		return ResponseEntity.ok(autores);
	}
	
	@GetMapping("/autores/decrescente")
	public ResponseEntity<List<String>> listarAutoresPorNomeDecrescente() {
		List<String> autores = livroRepository.findAllAutoresOrderByNomeDesc();
		return ResponseEntity.ok(autores);
	}


}
