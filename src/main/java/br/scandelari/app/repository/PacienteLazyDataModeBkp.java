package br.scandelari.app.repository;

import br.scandelari.app.model.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@Transactional
public class PacienteLazyDataModeBkp extends LazyDataModel<Paciente> {
    @PersistenceContext
    private EntityManager em;


    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return 0;
    }

    @Override
    public List<Paciente> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
//        TypedQuery<Paciente> query = em.createQuery("SELECT p FROM Paciente p", Paciente.class);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Paciente> cq = cb.createQuery(Paciente.class);
        Root<Paciente> root = cq.from(Paciente.class);
        List<Predicate> predicates = new ArrayList<>();
        int rowCount = em.createQuery("SELECT COUNT(p) FROM Paciente p", Long.class).getSingleResult().intValue();

        if (filterBy != null) {
            for (Map.Entry<String, FilterMeta> entry : filterBy.entrySet()) {
                String filterProperty = entry.getKey();
                Object filterValue = entry.getValue().getFilterValue();

                if (filterValue != null && !filterValue.toString().isEmpty()) {
                    predicates.add(cb.like(root.get(filterProperty), "%" + filterValue + "%"));
                }
            }
            if (!predicates.isEmpty()) {
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                rowCount = getRowCount(cb, predicates, first, pageSize);
            }

        }

        Query query = em.createQuery(cq);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);

        this.setRowCount(rowCount);
        System.out.println("##############################################");
        System.out.println("[ROWCOUNT] " + this.getRowCount());
        return query.getResultList();
    }

    private int getRowCount(CriteriaBuilder cb, List<Predicate> predicates, int first, int pageSize) {
        CriteriaQuery<Long> count = cb.createQuery(Long.class);
        count.select(cb.count(count.from(Paciente.class)));
//        count.where(predicates.toArray(new Predicate[0]));
        count.where(predicates.toArray(new Predicate[predicates.size()]));
        Query countQuery = em.createQuery(count);
        countQuery.setFirstResult(first);
        countQuery.setMaxResults(pageSize);
        System.out.println(" QUITERIA COUNT: "+ countQuery.getFirstResult());
        return countQuery.getFirstResult();
    }
}
