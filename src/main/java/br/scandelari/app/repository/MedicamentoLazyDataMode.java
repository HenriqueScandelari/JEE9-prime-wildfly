package br.scandelari.app.repository;

import br.scandelari.app.model.Medicamento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@Transactional
public class MedicamentoLazyDataMode extends LazyDataModel<Medicamento> {
    @PersistenceContext
    private EntityManager em;

    private List<Long> notIn;

    public List<Long> getNotIn() {
        return notIn;
    }

    public void setNotIn(List<Long> notIn) {
        this.notIn = notIn;
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return 0;
    }

    @Override
    public List<Medicamento> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
//        TypedQuery<Paciente> query = em.createQuery("SELECT p FROM Paciente p", Paciente.class);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Medicamento> cq = cb.createQuery(Medicamento.class);
        Root<Medicamento> root = cq.from(Medicamento.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filterBy != null) {
            for (Map.Entry<String, FilterMeta> entry : filterBy.entrySet()) {
                String filterProperty = entry.getKey();
                Object filterValue = entry.getValue().getFilterValue();

                if (filterValue != null && !filterValue.toString().isEmpty()) {
                    predicates.add(cb.like(root.get(filterProperty), "%" + filterValue + "%"));
                }
            }
            if (notIn != null && !notIn.isEmpty()) {
                Expression<String> nameExpression = root.get("idMedicamento");
                Predicate inPredicate = nameExpression.in(notIn);
                Predicate notInPredicate = cb.not(inPredicate);
                predicates.add(notInPredicate);
            }
            if (!predicates.isEmpty()) {
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
            }
        }

        Query query = em.createQuery(cq);
        var tot = query.getResultList().size();
        query.setFirstResult(first);
        query.setMaxResults(pageSize);

        var result = query.getResultList();

        this.setRowCount(tot);
        return result;
    }

}
