package name.mutant.dough.service;

import name.mutant.dough.DoughException;
import name.mutant.dough.domain.Acct;
import name.mutant.dough.domain.Acct_;
import name.mutant.dough.domain.Tran;
import name.mutant.dough.service.dto.AcctBalance;
import name.mutant.dough.service.filter.request.OrderByDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

    public static Acct readAcctByFidAndOfxAcctId(String fid, String ofxAcctId) throws DoughException {
        DaoFunction<Acct> function = new DaoFunction<Acct>() {
            private String message = "Error reading acct fid=\"" + fid + "\", ofxAcctId=\"" + ofxAcctId + "\".";

            public Acct doFunction(EntityManager entityManager) throws Exception {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Acct> cq = cb.createQuery(Acct.class);
                Root<Acct> acct = cq.from(Acct.class);
                cq.select(acct);
                Predicate fidEquals = cb.equal(acct.get(Acct_.fid), fid);
                Predicate ofxAcctIdEquals = cb.equal(acct.get(Acct_.ofxAcctId), ofxAcctId);
                cq.where(fidEquals, ofxAcctIdEquals);
                TypedQuery<Acct> query = entityManager.createQuery(cq);
                List<Acct> resultList = query.getResultList();
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
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Acct> cq = cb.createQuery(Acct.class);
                Root<Acct> acct = cq.from(Acct.class);
                cq.select(acct);
                List<Expression<?>> orderByPathList = new ArrayList<>();
                orderByPathList.add(acct.get(Acct_.name));
                orderByPathList.add(acct.get(Acct_.id));
                attachOrderByToQuery(cb, orderByPathList, OrderByDirection.ASC, cq);
                TypedQuery<Acct> query = entityManager.createQuery(cq);
                List<AcctBalance> acctBalances = new ArrayList<>();
                for (Acct result : query.getResultList()) {
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
