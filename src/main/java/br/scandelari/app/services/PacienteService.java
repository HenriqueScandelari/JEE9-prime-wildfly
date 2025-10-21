package br.scandelari.app.services;

import br.scandelari.app.model.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class PacienteService {

    @PersistenceContext
    private EntityManager em;

    public Paciente findById(Long id) {
        return em.find(Paciente.class, 1L);
    }
}
