package dev.impedrof.rest.controller;

import dev.impedrof.domain.entity.Cliente;
import dev.impedrof.domain.repository.ClientesRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "clientes")
public class ClienteController {

    private final ClientesRepository repo;

    public ClienteController(ClientesRepository repo) {
        this.repo = repo;
    }

    @GetMapping()
    public List<Cliente> findAll() {
        try {
            return repo.findAll();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível realizar a busca");
        }
    }

    @GetMapping("{id}")
    public Cliente getById(@PathVariable Integer id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public List<Cliente> save(@RequestBody List<Cliente> cliente) {
        return repo.saveAll(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        repo.findById(id)
                .map(cliente -> {
                    repo.delete(cliente);
                    return Void.class;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Cliente cliente) {
        repo
            .findById(id)
            .map(clienteExistente -> {
                cliente.setId(clienteExistente.getId());
                repo.save(cliente);
                return clienteExistente;
            }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }


    @GetMapping("filtro")
    public List<Cliente> find(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Cliente> example = Example.of(filtro, matcher);
        return repo.findAll(example);
    }
}
