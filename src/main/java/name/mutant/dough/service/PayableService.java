package name.mutant.dough.service;

import name.mutant.dough.DoughException;
import name.mutant.dough.domain.Payable;
import name.mutant.dough.domain.Payable_;
import name.mutant.dough.domain.Payee;
import name.mutant.dough.domain.Payee_;
import name.mutant.dough.service.dto.BillToPay;
import name.mutant.dough.service.filter.request.OrderByDirection;
import name.mutant.dough.service.filter.request.PayableFilterRequest;
import name.mutant.dough.service.filter.request.PayableOrderByField;
import name.mutant.dough.service.filter.response.PayableFilterResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronExpression;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

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

    public static PayableFilterResponse filterPayables(PayableFilterRequest request) throws DoughException {
        DaoFunction<PayableFilterResponse> function = new DaoFunction<PayableFilterResponse>() {
            public PayableFilterResponse doFunction(EntityManager entityManager) throws Exception {
                // Select ...
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Payable> cq = cb.createQuery(Payable.class);
                CriteriaQuery<Long> cq2 = cb.createQuery(Long.class);
                Root<Payable> payable = cq.from(Payable.class);
                cq2.from(Payable.class);
                Join<Payable, Payee> payee = payable.join(Payable_.payee);
                cq.select(payable);
                cq2.select(cb.count(payable));

                // Where ...
                Collection<Predicate> whereCollection = new HashSet<Predicate>();
                if (request.getWherePayeeIdEq() != null) {
                    Predicate payeeIdEq = cb.equal(payee.get(Payee_.id), request.getWherePayeeIdEq());
                    whereCollection.add(payeeIdEq);
                }
                if (request.getWhereMemoLike() != null) {
                    Expression<String> lowerMemo = cb.lower(payable.get(Payable_.memo));
                    String pattern = "%" + request.getWhereMemoLike().toLowerCase() + "%";
                    Predicate memoLike = cb.like(lowerMemo, pattern);
                    whereCollection.add(memoLike);
                }
                if (request.getWhereDueDateAfter() != null) {
                    Path<Date> actDueDatePath = payable.get(Payable_.actDueDate);
                    Path<Date> estDueDatePath = payable.get(Payable_.estDueDate);
                    Expression<Date> coalesce = cb.coalesce(actDueDatePath, estDueDatePath);
                    Predicate dueDateAfter = cb.greaterThan(coalesce, request.getWhereDueDateAfter());
                    whereCollection.add(dueDateAfter);
                }
                if (request.getWhereDueDateBefore() != null) {
                    Path<Date> actDueDatePath = payable.get(Payable_.actDueDate);
                    Path<Date> estDueDatePath = payable.get(Payable_.estDueDate);
                    Expression<Date> coalesce = cb.coalesce(actDueDatePath, estDueDatePath);
                    Predicate dueDateBefore = cb.lessThan(coalesce, request.getWhereDueDateBefore());
                    whereCollection.add(dueDateBefore);
                }
                if (request.getWhereActual() != null) {
                    Predicate actual = null;
                    if (request.getWhereActual()) {
                        actual = cb.isNotNull(payable.get(Payable_.actDueDate));
                    } else {
                        actual = cb.isNull(payable.get(Payable_.actDueDate));
                    }
                    whereCollection.add(actual);
                }
                if (request.getWherePaid() != null) {
                    Predicate paid = null;
                    if (request.getWherePaid()) {
                        paid = cb.isNotNull(payable.get(Payable_.paidDate));
                    } else {
                        paid = cb.isNull(payable.get(Payable_.paidDate));
                    }
                    whereCollection.add(paid);
                }
                if (request.getWhereNoBill() != null) {
                    Predicate noBillEq = cb.equal(payable.get(Payable_.noBill), request.getWhereNoBill());
                    whereCollection.add(noBillEq);
                }
                attachWhereToQueries(whereCollection, cq, cq2);

                // Order by ...
                List<Expression<?>> orderByPathList = new ArrayList<>();
                if (request.getOrderByField() == PayableOrderByField.PAYEE_NAME) {
                    orderByPathList.add(payee.get(Payee_.name));
                }
                if (request.getOrderByField() == PayableOrderByField.DUE_DATE) {
                    Path<Date> actDueDatePath = payable.get(Payable_.actDueDate);
                    Path<Date> estDueDatePath = payable.get(Payable_.estDueDate);
                    Expression<Date> coalesce = cb.coalesce(actDueDatePath, estDueDatePath);
                    orderByPathList.add(coalesce);
                }
                // Always order by id.
                orderByPathList.add(payable.get(Payable_.id));
                // Ascending or Descending?
                attachOrderByToQuery(cb, orderByPathList, request.getOrderByDirection(), cq);

                PayableFilterResponse response = new PayableFilterResponse();

                // Get a page of records.
                TypedQuery<Payable> query = entityManager.createQuery(cq);
                if (request.getFirst() > 0) query.setFirstResult(request.getFirst());
                if (request.getMax() >= 0) query.setMaxResults(request.getMax());
                List<Payable> resultList = query.getResultList();
                response.setResultList(resultList);

                // Get total record count.
                TypedQuery<Long> query2 = entityManager.createQuery(cq2);
                Long count = query2.getSingleResult();
                response.setCount(count);
                return response;
            }

            public String getErrorMessage() {
                return "Error reading payables with filter request=\"" + request + "\".";
            }
        };
        return doWithEntityManager(function);
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
                        newPayable.setNoBill(Boolean.FALSE);
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

    public static List<BillToPay> getBillsToPay(Date today) throws DoughException {
        DaoFunction<List<BillToPay>> function = new DaoFunction<List<BillToPay>>() {
            public List<BillToPay> doFunction(EntityManager entityManager) throws Exception {
                Calendar cal = Calendar.getInstance();
                cal.setTime(today);
                try {
                    cal.add(Calendar.DATE, Integer.parseInt(appBundle.getString("almost.due.days")));
                } catch (NumberFormatException e) {
                    cal.add(Calendar.DATE, 10);
                } catch (MissingResourceException e) {
                    cal.add(Calendar.DATE, 10);
                }
                Date almost = cal.getTime();
                PayableFilterRequest request = new PayableFilterRequest();
                request.setWherePaid(Boolean.FALSE);
                request.setWhereNoBill(Boolean.FALSE);
                request.setOrderByField(PayableOrderByField.DUE_DATE);
                request.setOrderByDirection(OrderByDirection.ASC);
                request.setMax(-1);
                PayableFilterResponse response = filterPayables(request);
                List<BillToPay> billsToPay = new ArrayList<>();
                for (Payable result : response.getResultList()) {
                    BillToPay billToPay = new BillToPay();
                    billToPay.setPayableId(result.getId());
                    billToPay.setPayeeName(result.getPayee().getName());
                    billToPay.setDueDate(result.getActDueDate() == null ? result.getEstDueDate() : result.getActDueDate());
                    billToPay.setAmount(result.getActAmount() == null ? result.getEstAmount() : result.getActAmount());
                    billToPay.setActual(result.getActDueDate() != null);
                    billToPay.setOverDue(billToPay.getDueDate().before(today));
                    billToPay.setAlmostDue(billToPay.getDueDate().before(almost));
                    billsToPay.add(billToPay);
                }
                return billsToPay;
            }

            public String getErrorMessage() {
                return "Error getting bills to pay with date =\"" + today + "\".";
            }
        };
        return doWithEntityManager(function);
    }
}
