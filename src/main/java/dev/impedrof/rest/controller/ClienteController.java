package dev.impedrof.rest.controller;

import dev.impedrof.domain.entity.Cliente;
import dev.impedrof.domain.repository.ClientesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("clientes")
public class ClienteController {

    private final ClientesRepository repo;

    public ClienteController(ClientesRepository repo) {
        this.repo = repo;
    }

    @GetMapping()
    @ResponseBody
    public ResponseEntity<?> findAll() {
        try {
            List<Cliente> clientes = repo.findAll();
            return ResponseEntity.ok(clientes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Optional<Cliente> cliente = repo.findById(id);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> save(@RequestBody Cliente cliente) {
        try {
            Cliente clienteSalvo = repo.save(cliente);
            return ResponseEntity.ok(clienteSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
