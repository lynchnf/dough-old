package name.mutant.dough.service;

import name.mutant.dough.DoughNotFoundException;
import name.mutant.dough.DoughOptimisticLockingException;
import name.mutant.dough.domain.Payable;
import name.mutant.dough.domain.Payment;
import name.mutant.dough.repository.PayableRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PayableService {
    private static final int ALMOST_DUE_DAYS = 14;
    protected static final String OVERDUE_CLASS = "table-danger";
    protected static final String ALMOST_DUE_CLASS = "table-warning";
    protected static final String NOT_DUE_FOR_AWHILE_YET_CLASS = "table-light";
    protected static final String ALREADY_PAID_CLASS = "table-success";
    private Date today;
    private Date warning;
    @Autowired
    private PayableRepository payableRepository;

    public PayableService() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        today = cal.getTime();
        cal.add(Calendar.DATE, ALMOST_DUE_DAYS);
        warning = cal.getTime();
    }

    public Iterable<Payable> findAllPayables() {
        return payableRepository.findAll();
    }

    public Payable findPayableById(Long payableId) throws DoughNotFoundException {
        Optional<Payable> optional = payableRepository.findById(payableId);
        if (!optional.isPresent()) {
            throw new DoughNotFoundException("Payable not found, payableId=\"" + payableId + "\"");
        }
        return optional.get();
    }

    public Payable savePayable(Payable payable) throws DoughOptimisticLockingException {
        try {
            return payableRepository.save(payable);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new DoughOptimisticLockingException(
                    "Optimistic locking failure while saving payable, payableId=\"" + payable.getId() + "\"", e);
        }
    }

    public List<PayableDueBean> findAllPayableDues() {
        List<PayableDueBean> payableDues = new ArrayList<>();
        Iterable<Payable> payables = payableRepository.findAllByOrderByDueDate();
        for (Payable payable : payables) {
            Long id = payable.getId();
            String payeeDisplayName = payable.getPayee().getNickname();
            if (StringUtils.isBlank(payable.getPayee().getNickname()))
                payeeDisplayName = payable.getPayee().getName();
            Date dueDate = payable.getDueDate();
            BigDecimal amountDue = payable.getAmountDue();
            BigDecimal minimumPayment = payable.getMinimumPayment();
            Date lastPaidDate = null;
            BigDecimal balanceDue = payable.getAmountDue();
            BigDecimal previousBalance = payable.getPreviousBalance();
            if (previousBalance != null) {
                BigDecimal previousPayments = payable.getPreviousPayments();
                if (previousPayments == null)
                    previousPayments = BigDecimal.ZERO;
                BigDecimal previousDue = previousBalance.add(previousPayments);
                if (previousDue.compareTo(BigDecimal.ZERO) > 0)
                    balanceDue = balanceDue.subtract(previousDue);
            }
            List<Payment> payments = payable.getPayments();
            for (Payment payment : payments) {
                if (lastPaidDate == null || lastPaidDate.before(payment.getPaidDate()))
                    lastPaidDate = payment.getPaidDate();
                balanceDue = balanceDue.subtract(payment.getAmountPaid());
            }
            String styleClass = null;
            if (balanceDue.compareTo(BigDecimal.ZERO) > 0) {
                if (dueDate.before(today)) {
                    styleClass = OVERDUE_CLASS;
                } else if (dueDate.before(warning)) {
                    styleClass = ALMOST_DUE_CLASS;
                } else {
                    styleClass = NOT_DUE_FOR_AWHILE_YET_CLASS;
                }
            } else {
                // If the balance is zero (or less) and the due date and the last paid date (if there is one) are both
                // in the past, then we don't need to see this payable any more. Otherwise, show it with the
                // already-paid style.
                if (dueDate.equals(today) || dueDate.after(today) ||
                        lastPaidDate != null && (lastPaidDate.equals(today) || lastPaidDate.after(today))) {
                    styleClass = ALREADY_PAID_CLASS;
                }
            }
            if (styleClass != null) {
                PayableDueBean payableDue = new PayableDueBean(id, payeeDisplayName, dueDate, amountDue, minimumPayment,
                        lastPaidDate, balanceDue, styleClass);
                payableDues.add(payableDue);
            }
        }
        return payableDues;
    }
}