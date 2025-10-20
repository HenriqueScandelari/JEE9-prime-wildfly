package br.scandelari.app.repository;

import br.scandelari.app.model.Pessoa;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class PessoaRepository {

    @PersistenceContext
    private EntityManager em;

    public void persist(Pessoa pessoa) {
        em.persist(pessoa);;
    }
}
