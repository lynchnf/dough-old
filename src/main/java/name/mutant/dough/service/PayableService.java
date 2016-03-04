package name.mutant.dough.service;

import name.mutant.dough.DoughException;
import name.mutant.dough.domain.Acct;
import name.mutant.dough.domain.Payable;
import name.mutant.dough.domain.Payable_;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronExpression;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PayableService extends BaseService {
    private static final Logger LOG = LogManager.getLogger();

    private PayableService() {
    }

    public static Payable readPayable(Long id) throws DoughException {
        DaoFunction<Payable> function = new DaoFunction<Payable>() {
            public Payable doFunction(EntityManager entityManager) throws Exception {
                return entityManager.find(Payable.class, id);
            }

            public String getErrorMessage() {
                return "Error reading payable id=\"" + id + "\".";
            }
        };
        return doWithEntityManager(function);
    }

    public static List<String> validateSavePayable(Payable payable) {
        List<String> errors = new ArrayList<>();

        // TODO Business validation rules here.

        return errors;
    }

    public static Payable savePayable(Payable payable) throws DoughException {
        DaoFunction<Payable> function = new DaoFunction<Payable>() {
            public Payable doFunction(EntityManager entityManager) throws Exception {
                return entityManager.merge(payable);
            }

            public String getErrorMessage() {
                return "Error saving payable=\"" + payable + "\".";
            }
        };
        return doInTransaction(function);
    }

    public static List<Payable> readAllPayablesForAcct(Long acctId) throws DoughException {
        DaoFunction<List<Payable>> function = new DaoFunction<List<Payable>>() {
            public List<Payable> doFunction(EntityManager entityManager) throws Exception {
                List<Payable> payables = new ArrayList<>();
                Acct acct = AcctService.readAcct(acctId);
                for (Payable payable : acct.getPayables()) {
                    payables.add(payable);
                }
                return payables;
            }

            public String getErrorMessage() {
                return "Error reading all payables for acct id=\"" + acctId + "\".";
            }
        };
        return doWithEntityManager(function);
    }

    private static List<Payable> readPayablesByAcctAndEstDueDate(Long acctId, Date estDueDate) throws DoughException {
        DaoFunction<List<Payable>> function = new DaoFunction<List<Payable>>() {
            private String message = "Error reading all payables for acct id=\"" + acctId + "\", estimated due date=\"" + estDueDate + "\",";

            public List<Payable> doFunction(EntityManager entityManager) throws Exception {
                Acct acct = AcctService.readAcct(acctId);
                if (acct == null) {
                    String message = "Acct not found for acctId=\"" + acctId + "\".";
                    throw new DoughException(message);
                }

                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Payable> cq = cb.createQuery(Payable.class);
                Root<Payable> payable = cq.from(Payable.class);
                cq.select(payable);
                Predicate acctEquals = cb.equal(payable.get(Payable_.acct), acct);
                Predicate estDueDateEquals = cb.equal(payable.get(Payable_.estDueDate), estDueDate);
                cq.where(acctEquals, estDueDateEquals);
                TypedQuery<Payable> query = entityManager.createQuery(cq);
                List<Payable> resultList = query.getResultList();
                return resultList;
            }

            public String getErrorMessage() {
                return message;
            }
        };
        return doWithEntityManager(function);
    }

    public static void createEstimatedPayables(Long acctId, Date afterDate) throws DoughException {
        DaoFunction<Void> function = new DaoFunction<Void>() {
            public Void doFunction(EntityManager entityManager) throws Exception {
                Acct acct = AcctService.readAcct(acctId);
                acct.getPayables().size(); // Instantiate payables list.
                CronExpression cronExpression = new CronExpression(acct.getCronExpression());
                Date nextDueDate = afterDate;
                for (int i = 0; i < acct.getNbrEstToCreate(); i++) {
                    nextDueDate = cronExpression.getNextValidTimeAfter(nextDueDate);
                    List<Payable> nextPayables = PayableService.readPayablesByAcctAndEstDueDate(acctId, nextDueDate);
                    if (nextPayables == null || nextPayables.isEmpty()) {
                        Payable newPayable = new Payable();
                        newPayable.setAcct(acct);
                        newPayable.setEstDueDate(nextDueDate);
                        newPayable.setEstAmount(acct.getEstAmount());
                        newPayable.setPaid(false);
                        acct.getPayables().add(newPayable);
                    }
                }
                AcctService.saveAcct(acct);
                return null;
            }

            public String getErrorMessage() {
                return "Error creating estimated payables for acct id=\"" + acctId + "\" after=\"" + afterDate + "\".";
            }
        };
        doInTransaction(function);
    }
}
