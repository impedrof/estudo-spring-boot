package dev.impedrof.rest.controller;

import dev.impedrof.domain.entity.Produto;
import dev.impedrof.domain.repository.ProdutosRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    private final ProdutosRepository repo;

    public ProdutoController(ProdutosRepository repo) {
        this.repo = repo;
    }

    // salvar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Produto produto) {
        repo.save(produto);
    }

    // deletar
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        repo.findById(id).map(produto -> {
            repo.deleteById(id);
            return Void.class;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    // atualizar
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Produto produto) {
        repo.findById(id).map(produtoExistente -> {
            produto.setId(produtoExistente.getId());
            repo.save(produto);
            return produtoExistente;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    // pesquisar
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> find(Produto produto) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Produto> example = Example.of(produto, matcher);
        return repo.findAll(example);
    }

}
