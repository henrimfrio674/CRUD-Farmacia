package com.generation.farmacia.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.farmacia.Model.Produto;
import com.generation.farmacia.Repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Produto")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
    @GetMapping
    public ResponseEntity<List<Produto>> getAll(){
        return ResponseEntity.ok(produtoRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable Long id){
        return produtoRepository.findById(id)
            .map(resposta -> ResponseEntity.ok(resposta))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<Produto>> getByTitle(@PathVariable 
    String nome){
        return ResponseEntity.ok(produtoRepository
            .findAllByNomeContainingIgnoreCase(nome));
    }
    
    @PostMapping
    public ResponseEntity<Produto> post(@Valid @RequestBody Produto tema){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(produtoRepository.save(tema));
    }
    
    @PutMapping
    public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto){
        return produtoRepository.findById(produto.getId())
            .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
            .body(produtoRepository.save(produto)))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Produto> tema = produtoRepository.findById(id);
        
        if(tema.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        
        produtoRepository.deleteById(id);              
    }

}
