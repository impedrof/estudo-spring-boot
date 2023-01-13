package dev.impedrof;

import dev.impedrof.domain.entity.Cliente;
import dev.impedrof.domain.repositorio.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication {

    @Autowired Clientes repo;

    @Bean
    public CommandLineRunner init(@Autowired Clientes repo) {
        return args -> {
            repo.save(new Cliente("Vayne"));
            repo.save(new Cliente("Draven"));
            System.out.println(repo.findAll());

            boolean exists = repo.existsByNome("Dravsen");
            System.out.println("O cliente Draven existe? " + (exists ? "Sim" : "Não") );
        };
    }

    @GetMapping("/todos")
    public List<Cliente> obterTodos() {
        return this.repo.findAll();
    }

    @GetMapping("{id}")
    public Cliente obterPorId(@PathVariable int id) {
        return this.repo.findById(id).orElseThrow();
    }

    @PostMapping()
    public void atualizar(@RequestBody Cliente cliente) {
        this.repo.save(cliente);
    }

    @DeleteMapping("{id}")
    public void deletar(@PathVariable int id) {
        this.repo.deleteById(id);
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }

}
