package br.scandelari.app.repository;

import br.scandelari.app.model.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

@ApplicationScoped
@Transactional
public class PacienteLazyDataMode extends LazyDataModel<Paciente> {
    @PersistenceContext
    private EntityManager em;


    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return 0;
    }

    @Override
    public List<Paciente> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        TypedQuery<Paciente> query = em.createQuery("SELECT p FROM Paciente p", Paciente.class);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);

        Long rowCount = em.createQuery("SELECT COUNT(p) FROM Paciente p", Long.class).getSingleResult();
        this.setRowCount(rowCount.intValue());
        return query.getResultList();
    }

//    public List<Paciente> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
//
//    }
}
