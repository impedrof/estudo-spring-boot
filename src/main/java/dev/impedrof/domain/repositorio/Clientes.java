package dev.impedrof.domain.repositorio;

import dev.impedrof.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class Clientes {

    private static final String INSERT = "INSERT INTO CLIENTE (nome) VALUES (?)";
    private static final String SELECT_ALL = "SELECT * FROM CLIENTE";
    private static final String UPDATE = "UPDATE CLIENTE SET nome = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM CLIENTE WHERE id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void salvar(Cliente cliente) {
        jdbcTemplate.update(INSERT, cliente.getNome());
    }

    public void atualizar(Cliente cliente) {
        jdbcTemplate.update(UPDATE, cliente.getNome(), cliente.getId());
    }

    public void deletar(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    public Optional<Cliente> obterPorId(int id) {
        return jdbcTemplate.query(SELECT_ALL.concat(" WHERE id = ?"), getClienteRowMapper(), id).stream().findFirst();
    }

    public List<Cliente> buscarPorNome(String nome) {
        return jdbcTemplate.query(SELECT_ALL.concat(" WHERE nome like ?"), getClienteRowMapper(), "%" + nome + "%");
    }

    public List<Cliente> obterClientes() {
        return jdbcTemplate.query(SELECT_ALL, getClienteRowMapper());
    }

    private RowMapper<Cliente> getClienteRowMapper() {
        return (rs, rowNum) -> new Cliente(rs.getInt("id"), rs.getString("nome"));
    }
}
