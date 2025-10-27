package br.scandelari.app.services;

import br.scandelari.app.model.Medicamento;
import br.scandelari.app.model.Medicamento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

@ApplicationScoped
@Transactional
public class MedicamentoService {

    @PersistenceContext
    private EntityManager em;

    public Medicamento findById(Long id) {
        return em.find(Medicamento.class, id);
    }

    public List<Medicamento> findAll() {
        return em.createQuery("SELECT m FROM Medicamento m", Medicamento.class).getResultList();
    }

    public void create(Medicamento medicamento) {
        em.persist(medicamento);
    }
    public void update(Medicamento medicamento) {
        em.merge(medicamento);
    }
    public void delete(Long id) {
        em.remove(findById(id));
    }
}
