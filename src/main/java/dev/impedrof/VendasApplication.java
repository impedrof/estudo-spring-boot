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
            repo.salvar(new Cliente("Vayne"));
            repo.salvar(new Cliente("Draven"));
            System.out.println(repo.obterClientes());
            Cliente draven = new Cliente(2, "Dravensssssssssss");
            repo.atualizar(draven);
            System.out.println(repo.obterClientes());

            repo.deletar(2);
            System.out.println(repo.obterClientes());

            Cliente cliente = repo.obterPorId(1);
            System.out.println(cliente);

            System.out.println(repo.buscarPorNome("Draven"));
            System.out.println(repo.obterClientes());
        };
    }

    @GetMapping("/todos")
    public List<Cliente> obterTodos() {
        return this.repo.obterClientes();
    }

    @GetMapping("{id}")
    public Cliente obterPorId(@PathVariable int id) {
        return this.repo.obterPorId(id);
    }

    @PostMapping()
    public void atualizar(@RequestBody Cliente cliente) {
        this.repo.atualizar(cliente);
    }

    @DeleteMapping("{id}")
    public void deletar(@PathVariable int id) {
        this.repo.deletar(id);
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }

}
