package dev.impedrof;

import dev.impedrof.domain.entity.Cliente;
import dev.impedrof.domain.entity.Pedido;
import dev.impedrof.domain.repository.ClientesRepository;
import dev.impedrof.domain.repository.PedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@RestController
public class VendasApplication {

    @Autowired
    ClientesRepository repo;

    @Bean
    public CommandLineRunner init(@Autowired ClientesRepository clientesRepositoryRepo, @Autowired PedidosRepository pedidosRepositoryRepo) {
        return args -> {
            System.out.println("Salvando clientes");
            Cliente cliente = new Cliente("Vayne");
            clientesRepositoryRepo.save(cliente);

            Pedido pedido = new Pedido();
            pedido.setCliente(cliente);
            pedido.setDataPedido(LocalDate.now());
            pedido.setTotal(BigDecimal.valueOf(100));
            pedidosRepositoryRepo.save(pedido);


//            Cliente clienteFetchPedidos = clientesRepo.findClienteFetchPedidos(cliente.getId());
//            System.out.println(clienteFetchPedidos);
//            System.out.println(clienteFetchPedidos.getPedidos());

            Set<Pedido> pedidosCliente = pedidosRepositoryRepo.findByCliente(cliente);
            System.out.println(pedidosCliente);
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
