package name.mutant.dough.service;

import name.mutant.dough.DoughException;
import name.mutant.dough.domain.Payable;
import name.mutant.dough.domain.Payable_;
import name.mutant.dough.domain.Payee;
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

    public static List<Payable> readAllPayablesForPayee(Long payeeId) throws DoughException {
        DaoFunction<List<Payable>> function = new DaoFunction<List<Payable>>() {
            public List<Payable> doFunction(EntityManager entityManager) throws Exception {
                List<Payable> payables = new ArrayList<>();
                Payee payee = PayeeService.readPayee(payeeId);
                for (Payable payable : payee.getPayables()) {
                    payables.add(payable);
                }
                return payables;
            }

            public String getErrorMessage() {
                return "Error reading all payables for payee id=\"" + payeeId + "\".";
            }
        };
        return doWithEntityManager(function);
    }

    private static List<Payable> readPayablesByPayeeAndEstDueDate(Long payeeId, Date estDueDate) throws DoughException {
        DaoFunction<List<Payable>> function = new DaoFunction<List<Payable>>() {
            private String message = "Error reading all payables for payee id=\"" + payeeId + "\", estimated due date=\"" + estDueDate + "\",";

            public List<Payable> doFunction(EntityManager entityManager) throws Exception {
                Payee payee = PayeeService.readPayee(payeeId);
                if (payee == null) {
                    String message = "Payee not found for id=\"" + payeeId + "\".";
                    throw new DoughException(message);
                }

                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Payable> cq = cb.createQuery(Payable.class);
                Root<Payable> payable = cq.from(Payable.class);
                cq.select(payable);
                Predicate payeeEquals = cb.equal(payable.get(Payable_.payee), payee);
                Predicate estDueDateEquals = cb.equal(payable.get(Payable_.estDueDate), estDueDate);
                cq.where(payeeEquals, estDueDateEquals);
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

    public static void createEstimatedPayables(Long payeeId, Date afterDate) throws DoughException {
        DaoFunction<Void> function = new DaoFunction<Void>() {
            public Void doFunction(EntityManager entityManager) throws Exception {
                Payee payee = PayeeService.readPayee(payeeId);
                payee.getPayables().size(); // Instantiate payables list.
                CronExpression cronExpression = new CronExpression(payee.getCronExpression());
                Date nextDueDate = afterDate;
                for (int i = 0; i < payee.getNbrEstToCreate(); i++) {
                    nextDueDate = cronExpression.getNextValidTimeAfter(nextDueDate);
                    List<Payable> nextPayables = PayableService.readPayablesByPayeeAndEstDueDate(payeeId, nextDueDate);
                    if (nextPayables == null || nextPayables.isEmpty()) {
                        Payable newPayable = new Payable();
                        newPayable.setPayee(payee);
                        newPayable.setEstDueDate(nextDueDate);
                        newPayable.setEstAmount(payee.getEstAmount());
                        newPayable.setPaid(false);
                        payee.getPayables().add(newPayable);
                    }
                }
                PayeeService.savePayee(payee);
                return null;
            }

            public String getErrorMessage() {
                return "Error creating estimated payables for payee id=\"" + payeeId + "\" after=\"" + afterDate + "\".";
            }
        };
        doInTransaction(function);
    }
}
