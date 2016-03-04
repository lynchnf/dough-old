package name.mutant.dough.service;

import name.mutant.dough.DoughException;
import name.mutant.dough.domain.Acct;
import name.mutant.dough.domain.Tran;
import name.mutant.dough.domain.Tran_;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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
