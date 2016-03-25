package name.mutant.dough.service;

import name.mutant.dough.DoughException;
import name.mutant.dough.domain.Acct;
import name.mutant.dough.domain.Acct_;
import name.mutant.dough.domain.Tran;
import name.mutant.dough.service.dto.AcctBalance;
import name.mutant.dough.service.filter.request.AcctFilterRequest;
import name.mutant.dough.service.filter.request.AcctOrderByField;
import name.mutant.dough.service.filter.request.OrderByDirection;
import name.mutant.dough.service.filter.response.AcctFilterResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class AcctService extends BaseService {
    private static final Logger LOG = LogManager.getLogger();

    private AcctService() {
    }

    public static Acct readAcct(Long id) throws DoughException {
        DaoFunction<Acct> function = new DaoFunction<Acct>() {
            public Acct doFunction(EntityManager entityManager) throws Exception {
                return entityManager.find(Acct.class, id);
            }

            public String getErrorMessage() {
                return "Error reading acct id=\"" + id + "\".";
            }
        };
        return doWithEntityManager(function);
    }

    public static List<String> validateSaveAcct(Acct acct) {
        List<String> errors = new ArrayList<>();

        // TODO Business validation rules here.

        return errors;
    }

    public static Acct saveAcct(Acct acct) throws DoughException {
        DaoFunction<Acct> function = new DaoFunction<Acct>() {
            public Acct doFunction(EntityManager entityManager) throws Exception {
                return entityManager.merge(acct);
            }

            public String getErrorMessage() {
                return "Error saving acct=\"" + acct + "\".";
            }
        };
        return doInTransaction(function);
    }

    public static AcctFilterResponse filterAccts(AcctFilterRequest request) throws DoughException {
        DaoFunction<AcctFilterResponse> function = new DaoFunction<AcctFilterResponse>() {
            public AcctFilterResponse doFunction(EntityManager entityManager) throws Exception {
                // Select ...
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Acct> cq = cb.createQuery(Acct.class);
                CriteriaQuery<Long> cq2 = cb.createQuery(Long.class);
                Root<Acct> acct = cq.from(Acct.class);
                cq2.from(Acct.class);
                cq.select(acct);
                cq2.select(cb.count(acct));

                // Where ...
                Collection<Predicate> whereCollection = new HashSet<>();
                if (request.getWhereAcctNbrEq() != null) {
                    Predicate acctNbrEq = cb.equal(acct.get(Acct_.acctNbr), request.getWhereAcctNbrEq());
                    whereCollection.add(acctNbrEq);
                }
                if (request.getWhereNameLike() != null) {
                    Expression<String> lowerName = cb.lower(acct.get(Acct_.name));
                    String pattern = "%" + request.getWhereNameLike().toLowerCase() + "%";
                    Predicate nameLike = cb.like(lowerName, pattern);
                    whereCollection.add(nameLike);
                }
                if (request.getWhereOrganizationLike() != null) {
                    Expression<String> lowerOrganization = cb.lower(acct.get(Acct_.organization));
                    String pattern = "%" + request.getWhereOrganizationLike().toLowerCase() + "%";
                    Predicate organizationLike = cb.like(lowerOrganization, pattern);
                    whereCollection.add(organizationLike);
                }
                if (request.getWhereFidEq() != null) {
                    Predicate fidEq = cb.equal(acct.get(Acct_.fid), request.getWhereFidEq());
                    whereCollection.add(fidEq);
                }
                if (request.getWhereOfxBankIdEq() != null) {
                    Predicate ofxBankIdEq = cb.equal(acct.get(Acct_.ofxBankId), request.getWhereOfxBankIdEq());
                    whereCollection.add(ofxBankIdEq);
                }
                if (request.getWhereOfxAcctIdEq() != null) {
                    Predicate ofxAcctIdEq = cb.equal(acct.get(Acct_.ofxAcctId), request.getWhereOfxAcctIdEq());
                    whereCollection.add(ofxAcctIdEq);
                }
                if (request.getWhereTypeEq() != null) {
                    Predicate typeEq = cb.equal(acct.get(Acct_.type), request.getWhereTypeEq());
                    whereCollection.add(typeEq);
                }
                attachWhereToQueries(whereCollection, cq, cq2);

                // Order by ...
                List<Expression<?>> orderByPathList = new ArrayList<>();
                if (request.getOrderByField() == AcctOrderByField.ACCT_NBR) {
                    orderByPathList.add(acct.get(Acct_.acctNbr));
                }
                if (request.getOrderByField() == AcctOrderByField.NAME) {
                    orderByPathList.add(acct.get(Acct_.name));
                }
                if (request.getOrderByField() == AcctOrderByField.ORGANIZATION) {
                    orderByPathList.add(acct.get(Acct_.organization));
                }
                if (request.getOrderByField() == AcctOrderByField.FID) {
                    orderByPathList.add(acct.get(Acct_.fid));
                }
                if (request.getOrderByField() == AcctOrderByField.OFX_BANK_ID) {
                    orderByPathList.add(acct.get(Acct_.ofxBankId));
                }
                if (request.getOrderByField() == AcctOrderByField.OFX_ACCT_ID) {
                    orderByPathList.add(acct.get(Acct_.ofxAcctId));
                }
                if (request.getOrderByField() == AcctOrderByField.TYPE) {
                    orderByPathList.add(acct.get(Acct_.type));
                }
                // Always order by id.
                orderByPathList.add(acct.get(Acct_.id));
                // Ascending or Descending?
                attachOrderByToQuery(cb, orderByPathList, request.getOrderByDirection(), cq);

                AcctFilterResponse response = new AcctFilterResponse();

                // Get a page of records.
                TypedQuery<Acct> query = entityManager.createQuery(cq);
                if (request.getFirst() > 0) query.setFirstResult(request.getFirst());
                if (request.getMax() >= 0) query.setMaxResults(request.getMax());
                List<Acct> resultList = query.getResultList();
                response.setResultList(resultList);

                // Get total record count.
                TypedQuery<Long> query2 = entityManager.createQuery(cq2);
                Long count = query2.getSingleResult();
                response.setCount(count);
                return response;
            }

            public String getErrorMessage() {
                return "Error reading accts with filter request=\"" + request + "\".";
            }
        };
        return doWithEntityManager(function);
    }

    public static Acct readAcctByFidAndOfxAcctId(String fid, String ofxAcctId) throws DoughException {
        DaoFunction<Acct> function = new DaoFunction<Acct>() {
            private String message = "Error reading acct fid=\"" + fid + "\", ofxAcctId=\"" + ofxAcctId + "\".";

            public Acct doFunction(EntityManager entityManager) throws Exception {
                AcctFilterRequest request = new AcctFilterRequest();
                request.setWhereFidEq(fid);
                request.setWhereOfxAcctIdEq(ofxAcctId);
                AcctFilterResponse response = filterAccts(request);
                List<Acct> resultList = response.getResultList();
                if (resultList.isEmpty()) {
                    return null;
                } else if (resultList.size() == 1) {
                    return resultList.iterator().next();
                } else {
                    String message = "Multiple accts found for fid=\"" + fid + "\", ofxAcctId=\"" + ofxAcctId + "\".";
                    throw new DoughException(message);
                }
            }

            public String getErrorMessage() {
                return message;
            }
        };
        return doWithEntityManager(function);
    }

    public static List<AcctBalance> getAcctBalances() throws DoughException {
        DaoFunction<List<AcctBalance>> function = new DaoFunction<List<AcctBalance>>() {
            public List<AcctBalance> doFunction(EntityManager entityManager) throws Exception {
                AcctFilterRequest request = new AcctFilterRequest();
                request.setOrderByField(AcctOrderByField.NAME);
                request.setOrderByDirection(OrderByDirection.ASC);
                request.setMax(-1);
                AcctFilterResponse response = filterAccts(request);
                List<Acct> resultList = response.getResultList();
                List<AcctBalance> acctBalances = new ArrayList<>();
                for (Acct result : resultList) {
                    AcctBalance acctBalance = new AcctBalance();
                    acctBalance.setAcctId(result.getId());
                    acctBalance.setAcctName(result.getName());
                    acctBalance.setAcctType(result.getType());
                    BigDecimal balance = result.getBeginBalance() == null ? BigDecimal.ZERO : result.getBeginBalance();
                    Date lastTranDate = result.getBeginDate();
                    for (Tran tran : result.getTrans()) {
                        if (tran.getAmount() != null)
                            balance = balance.add(tran.getAmount());
                        if (lastTranDate == null || lastTranDate.before(tran.getPostDate()))
                            lastTranDate = tran.getPostDate();
                    }
                    acctBalance.setBalance(balance);
                    acctBalance.setLastTranDate(lastTranDate);
                    acctBalances.add(acctBalance);
                }
                return acctBalances;
            }

            public String getErrorMessage() {
                return "Error reading all account balances.";
            }
        };
        return doWithEntityManager(function);
    }
}
