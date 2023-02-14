package dev.impedrof.domain.repository;

import dev.impedrof.domain.entity.Cliente;
import dev.impedrof.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

    Set<Pedido> findByCliente(Cliente cliente);
}
