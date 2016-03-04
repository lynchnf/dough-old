package name.mutant.dough.service;

import name.mutant.dough.DoughException;
import name.mutant.dough.domain.Acct;
import name.mutant.dough.domain.Acct_;
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
}
