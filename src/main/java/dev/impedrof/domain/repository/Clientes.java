package dev.impedrof.domain.repository;

import dev.impedrof.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface Clientes extends JpaRepository<Cliente, Integer> {

//    @Query(value = "SELECT c FROM Cliente c WHERE c.nome like %:nome%")
    @Query(value = "SELECT * FROM cliente WHERE nome like %:nome%", nativeQuery = true)
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Cliente c WHERE c.nome = :nome")
    void deleteByNome(@Param("nome") String nome);

    boolean existsByNome(String nome);

    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.pedidos where c.id = :id")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);

}
