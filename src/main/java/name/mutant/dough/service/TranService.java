package name.mutant.dough.service;

import name.mutant.dough.DoughException;
import name.mutant.dough.domain.Acct;
import name.mutant.dough.domain.Acct_;
import name.mutant.dough.domain.Tran;
import name.mutant.dough.domain.Tran_;
import name.mutant.dough.service.filter.request.TranFilterRequest;
import name.mutant.dough.service.filter.request.TranOrderByField;
import name.mutant.dough.service.filter.response.TranFilterResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class TranService extends BaseService {
    private static final Logger LOG = LogManager.getLogger();

    private TranService() {
    }

    public static Tran readTran(Long id) throws DoughException {
        DaoFunction<Tran> function = new DaoFunction<Tran>() {
            public Tran doFunction(EntityManager entityManager) throws Exception {
                return entityManager.find(Tran.class, id);
            }

            public String getErrorMessage() {
                return "Error reading tran id=\"" + id + "\".";
            }
        };
        return doWithEntityManager(function);
    }

    public static List<String> validateSaveTran(Tran tran) {
        List<String> errors = new ArrayList<>();

        // TODO Business validation rules here.

        return errors;
    }

    public static Tran saveTran(Tran tran) throws DoughException {
        DaoFunction<Tran> function = new DaoFunction<Tran>() {
            public Tran doFunction(EntityManager entityManager) throws Exception {
                return entityManager.merge(tran);
            }

            public String getErrorMessage() {
                return "Error saving tran=\"" + tran + "\".";
            }
        };
        return doInTransaction(function);
    }

    public static TranFilterResponse filterTrans(TranFilterRequest request) throws DoughException {
        DaoFunction<TranFilterResponse> function = new DaoFunction<TranFilterResponse>() {
            public TranFilterResponse doFunction(EntityManager entityManager) throws Exception {
                // Select ...
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Tran> cq = cb.createQuery(Tran.class);
                CriteriaQuery<Long> cq2 = cb.createQuery(Long.class);
                Root<Tran> tran = cq.from(Tran.class);
                cq2.from(Tran.class);
                Join<Tran, Acct> acct = tran.join(Tran_.acct);
                cq.select(tran);
                cq2.select(cb.count(tran));

                // Where ...
                Collection<Predicate> whereCollection = new HashSet<>();
                if (request.getWhereAcctIdEq() != null) {
                    Predicate acctIdEq = cb.equal(acct.get(Acct_.id), request.getWhereAcctIdEq());
                    whereCollection.add(acctIdEq);
                }
                if (request.getWhereTypeEq() != null) {
                    Predicate typeEq = cb.equal(tran.get(Tran_.type), request.getWhereTypeEq());
                    whereCollection.add(typeEq);
                }
                if (request.getWherePostDateAfter() != null) {
                    Predicate postDateAfter = cb.greaterThan(tran.get(Tran_.postDate), request.getWherePostDateAfter());
                    whereCollection.add(postDateAfter);
                }
                if (request.getWherePostDateBefore() != null) {
                    Predicate postDateBefore = cb.lessThan(tran.get(Tran_.postDate), request.getWherePostDateBefore());
                    whereCollection.add(postDateBefore);
                }
                if (request.getWhereUserDateAfter() != null) {
                    Predicate userDateAfter = cb.greaterThan(tran.get(Tran_.userDate), request.getWhereUserDateAfter());
                    whereCollection.add(userDateAfter);
                }
                if (request.getWhereUserDateBefore() != null) {
                    Predicate userDateBefore = cb.lessThan(tran.get(Tran_.userDate), request.getWhereUserDateBefore());
                    whereCollection.add(userDateBefore);
                }
                if (request.getWhereAmountGreaterOrEqual() != null) {
                    Predicate amountGreaterOrEqual = cb.greaterThanOrEqualTo(tran.get(Tran_.amount), request.getWhereAmountGreaterOrEqual());
                    whereCollection.add(amountGreaterOrEqual);
                }
                if (request.getWhereAmountLessOrEqual() != null) {
                    Predicate amountLessOrEqual = cb.lessThanOrEqualTo(tran.get(Tran_.amount), request.getWhereAmountLessOrEqual());
                    whereCollection.add(amountLessOrEqual);
                }
                if (request.getWhereFitIdEq() != null) {
                    Predicate fitIdEq = cb.equal(tran.get(Tran_.fitId), request.getWhereFitIdEq());
                    whereCollection.add(fitIdEq);
                }
                if (request.getWhereSicEq() != null) {
                    Predicate sicEq = cb.equal(tran.get(Tran_.sic), request.getWhereSicEq());
                    whereCollection.add(sicEq);
                }
                if (request.getWhereCheckNumberEq() != null) {
                    Predicate checkNumberEq = cb.equal(tran.get(Tran_.checkNumber), request.getWhereCheckNumberEq());
                    whereCollection.add(checkNumberEq);
                }
                if (request.getWhereNameLike() != null) {
                    Expression<String> lowerName = cb.lower(tran.get(Tran_.name));
                    String pattern = "%" + request.getWhereNameLike().toLowerCase() + "%";
                    Predicate nameLike = cb.like(lowerName, pattern);
                    whereCollection.add(nameLike);
                }
                if (request.getWhereMemoLike() != null) {
                    Expression<String> lowerMemo = cb.lower(tran.get(Tran_.memo));
                    String pattern = "%" + request.getWhereMemoLike().toLowerCase() + "%";
                    Predicate memoLike = cb.like(lowerMemo, pattern);
                    whereCollection.add(memoLike);
                }
                attachWhereToQueries(whereCollection, cq, cq2);

                // Order by ...
                List<Expression<?>> orderByPathList = new ArrayList<>();
                if (request.getOrderByField() == TranOrderByField.ACCT_NAME) {
                    orderByPathList.add(acct.get(Acct_.name));
                }
                if (request.getOrderByField() == TranOrderByField.POST_DATE) {
                    orderByPathList.add(tran.get(Tran_.postDate));
                }
                if (request.getOrderByField() == TranOrderByField.USER_DATE) {
                    orderByPathList.add(tran.get(Tran_.userDate));
                }
                if (request.getOrderByField() == TranOrderByField.AMOUNT) {
                    orderByPathList.add(tran.get(Tran_.amount));
                }
                if (request.getOrderByField() == TranOrderByField.CHECK_NUMBER) {
                    orderByPathList.add(tran.get(Tran_.checkNumber));
                }
                if (request.getOrderByField() == TranOrderByField.NAME) {
                    orderByPathList.add(tran.get(Tran_.name));
                }
                if (request.getOrderByField() == TranOrderByField.MEMO) {
                    orderByPathList.add(tran.get(Tran_.memo));
                }
                // Always order by id.
                orderByPathList.add(tran.get(Tran_.id));
                // Ascending or Descending?
                attachOrderByToQuery(cb, orderByPathList, request.getOrderByDirection(), cq);

                TranFilterResponse response = new TranFilterResponse();

                // Get a page of records.
                TypedQuery<Tran> query = entityManager.createQuery(cq);
                if (request.getFirst() > 0) query.setFirstResult(request.getFirst());
                if (request.getMax() >= 0) query.setMaxResults(request.getMax());
                List<Tran> resultList = query.getResultList();
                response.setResultList(resultList);

                // Get total record count.
                TypedQuery<Long> query2 = entityManager.createQuery(cq2);
                Long count = query2.getSingleResult();
                response.setCount(count);
                return response;
            }

            public String getErrorMessage() {
                return "Error reading trans with filter request=\"" + request + "\".";
            }
        };
        return doWithEntityManager(function);
    }

    public static Tran readTranByAcctAndFitId(Long acctId, String fitId) throws DoughException {
        DaoFunction<Tran> function = new DaoFunction<Tran>() {
            private String message = "Error reading tran acctId=\"" + acctId + "\", fitId=\"" + fitId + "\".";

            public Tran doFunction(EntityManager entityManager) throws Exception {
                Acct acct = AcctService.readAcct(acctId);
                if (acct == null) {
                    String message = "Acct not found for acctId=\"" + acctId + "\".";
                    throw new DoughException(message);
                }

                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Tran> cq = cb.createQuery(Tran.class);
                Root<Tran> tran = cq.from(Tran.class);
                cq.select(tran);
                Predicate acctEquals = cb.equal(tran.get(Tran_.acct), acct);
                Predicate fitIdEquals = cb.equal(tran.get(Tran_.fitId), fitId);
                cq.where(acctEquals, fitIdEquals);
                TypedQuery<Tran> query = entityManager.createQuery(cq);
                List<Tran> resultList = query.getResultList();
                if (resultList.isEmpty()) {
                    return null;
                } else if (resultList.size() == 1) {
                    return resultList.iterator().next();
                } else {
                    String message = "Multiple trans found for acctId=\"" + acctId + "\", fitId=\"" + fitId + "\".";
                    throw new DoughException(message);
                }
            }

            public String getErrorMessage() {
                return message;
            }
        };
        return doWithEntityManager(function);
    }
}
