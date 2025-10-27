package br.scandelari.app.services;

import br.scandelari.app.model.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.UniqueConstraint;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLWarning;
import java.util.List;

@ApplicationScoped
@Transactional
public class PacienteService {

    @PersistenceContext
    private EntityManager em;

    public Paciente findById(Long id) {
        return em.find(Paciente.class, id);
    }

    public List<Paciente> findAll() {
        return em.createQuery("SELECT p FROM Paciente p", Paciente.class).getResultList();
    }

    public void create(Paciente paciente) {
        em.persist(paciente);
    }
    public void update(Paciente paciente) {
        em.merge(paciente);
    }
    public void delete(Long id) {
        em.remove(findById(id));
    }
    public void createOrUpdate(Paciente paciente) throws ConstraintViolationException, PersistenceException {
        paciente.setCpf(paciente.getCpf().replaceAll("[^0-9]", ""));
        if (null != paciente.getIdPaciente()) {
            create(paciente);
        } else {
            update(paciente);
        }
    }
}
