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
public class PacienteLazyDataMode extends LazyDataModel<Paciente> {
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

//                CriteriaQuery<Long> countCriteria = cb.createQuery(Long.class);
//                Root<?> entityRoot = countCriteria.from(Paciente.class);
//
//                countCriteria.select(cb.count(entityRoot));
//                countCriteria.where(predicates.toArray(new Predicate[predicates.size()]));

//                count.where(predicates.toArray(new Predicate[0]));
//                Query countQuery = em.createQuery(countCriteria);
//                countQuery.setFirstResult(first);
//                countQuery.setMaxResults(pageSize);
//                Long count = em.createQuery(countCriteria).getSingleResult();
//                System.out.println("##############################################");
////                System.out.println(" QUITERIA COUNT: "+ countQuery.getFirstResult());
//                System.out.println(" QUITERIA COUNT 2: "+ count);
//
//                rowCount = count.intValue();
            }
        }
        if (sortBy != null) {
            for (Map.Entry<String, SortMeta> entry : sortBy.entrySet()) {
                String sortField = entry.getKey();
                SortMeta sortMeta = entry.getValue();
                if (sortMeta.getOrder() == SortOrder.ASCENDING)
                    cq.orderBy(cb.asc(root.get(sortField)));
                else if (sortMeta.getOrder() == SortOrder.DESCENDING)
                    cq.orderBy(cb.desc(root.get(sortField)));
            }

        }

        Query query = em.createQuery(cq);
        var tot = query.getResultList().size();
        query.setFirstResult(first);
        query.setMaxResults(pageSize);


        var result = query.getResultList();

//        rowCount = em.createQuery("SELECT COUNT(p) FROM Paciente p", Long.class).getSingleResult().intValue();
//        this.setRowCount(rowCount);
        System.out.println("--------------------------------------");
        System.out.println(" COUNT LIST: " + tot);
        this.setRowCount(tot);
        return result;
    }

}
