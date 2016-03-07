package name.mutant.dough.service;

import name.mutant.dough.DoughException;
import name.mutant.dough.domain.Payee;
import name.mutant.dough.domain.Payee_;
import name.mutant.dough.service.filter.request.*;
import name.mutant.dough.service.filter.response.PayeeFilterResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class PayeeService extends BaseService {
    private static final Logger LOG = LogManager.getLogger();

    private PayeeService() {
    }

    public static Payee readPayee(Long id) throws DoughException {
        DaoFunction<Payee> function = new DaoFunction<Payee>() {
            public Payee doFunction(EntityManager entityManager) throws Exception {
                return entityManager.find(Payee.class, id);
            }

            public String getErrorMessage() {
                return "Error reading payee id=\"" + id + "\".";
            }
        };
        return doWithEntityManager(function);
    }

    public static List<String> validateSavePayee(Payee payee) {
        List<String> errors = new ArrayList<>();

        // TODO Business validation rules here.

        return errors;
    }

    public static Payee savePayee(Payee payee) throws DoughException {
        DaoFunction<Payee> function = new DaoFunction<Payee>() {
            public Payee doFunction(EntityManager entityManager) throws Exception {
                return entityManager.merge(payee);
            }

            public String getErrorMessage() {
                return "Error saving payee=\"" + payee + "\".";
            }
        };
        return doInTransaction(function);
    }

    public static PayeeFilterResponse filterPayees(PayeeFilterRequest request) throws DoughException {
        DaoFunction<PayeeFilterResponse> function = new DaoFunction<PayeeFilterResponse>() {
            public PayeeFilterResponse doFunction(EntityManager entityManager) throws Exception {
                // Select ...
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Payee> cq = cb.createQuery(Payee.class);
                Root<Payee> payee = cq.from(Payee.class);
                cq.select(payee);
                CriteriaQuery<Long> cq2 = cb.createQuery(Long.class);
                Root<Payee> payee2 = cq2.from(Payee.class);
                cq2.select(cb.count(payee2));

                // Where ...
                Collection<Predicate> whereCollection = new HashSet<Predicate>();
                if (request.getWhereNameLike() != null) {
                    Expression<String> lowerName = cb.lower(payee.get(Payee_.name));
                    String pattern = "%" + request.getWhereNameLike().toLowerCase() + "%";
                    Predicate nameLike = cb.like(lowerName, pattern);
                    whereCollection.add(nameLike);
                }
                if (request.getWhereTypeEq() != null) {
                    Predicate typeEq = cb.equal(payee.get(Payee_.type), request.getWhereTypeEq());
                    whereCollection.add(typeEq);
                }
                if (!whereCollection.isEmpty()) {
                    Predicate[] whereArray = new Predicate[whereCollection.size()];
                    whereArray = whereCollection.toArray(whereArray);
                    cq.where(whereArray);
                    cq2.where(whereArray);
                }

                // Order by ...
                List<Path<?>> orderByPathList = new ArrayList<>();
                if (request.getOrderByField() == PayeeOrderByField.NAME) {
                    orderByPathList.add(payee.get(Payee_.name));
                }
                // Always order by id.
                orderByPathList.add(payee.get(Payee_.id));
                // Ascending or Descending?
                List<Order> orderByList = new ArrayList<>();
                for (Path<?> orderByPath : orderByPathList) {
                    Order orderBy = null;
                    if (request.getOrderByDirection() == OrderByDirection.DESC) {
                        orderBy = cb.desc(orderByPath);
                    } else {
                        orderBy = cb.asc(orderByPath);
                    }
                    orderByList.add(orderBy);
                }
                cq.orderBy(orderByList);

                PayeeFilterResponse response = new PayeeFilterResponse();

                // Get a page of records.
                TypedQuery<Payee> query = entityManager.createQuery(cq);
                if (request.getFirst() > 0) query.setFirstResult(request.getFirst());
                if (request.getMax() >= 0) query.setMaxResults(request.getMax());
                response.setResultList(query.getResultList());

                // Get total record count.
                TypedQuery<Long> query2 = entityManager.createQuery(cq2);
                Long count = query2.getSingleResult();
                response.setCount(count);
                return response;
            }

            public String getErrorMessage() {
                return "Error reading payees with filter request=\"" + request + "\".";
            }
        };
        return doWithEntityManager(function);
    }
}
