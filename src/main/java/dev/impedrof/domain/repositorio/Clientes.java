package dev.impedrof.domain.repositorio;

import dev.impedrof.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class Clientes {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void salvar(Cliente cliente) {
        entityManager.persist(cliente);
    }

    @Transactional
    public void atualizar(Cliente cliente) {
        entityManager.merge(cliente);
    }

    @Transactional
    public void deletar(Cliente cliente) {
        if (!entityManager.contains(cliente)) {
            entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
    }

    @Transactional
    public void deletar(int id) {
        Cliente cliente = obterPorId(id);
        deletar(cliente);
    }

    @Transactional(readOnly = true)
    public Cliente obterPorId(int id) {
        return entityManager.find(Cliente.class, id);
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome) {
        String jpql = "SELECT c FROM Cliente c WHERE c.nome like :nome";
        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<Cliente> obterClientes() {
        return entityManager.createQuery("from Cliente", Cliente.class).getResultList();
    }
}
