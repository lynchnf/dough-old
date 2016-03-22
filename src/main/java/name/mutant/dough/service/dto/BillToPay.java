package name.mutant.dough.service.dto;

import java.math.BigDecimal;
import java.util.Date;

public class BillToPay {
    private Long payableId;
    private String payeeName;
    private Date dueDate;
    private BigDecimal amount;
    private boolean actual;
    private boolean overDue;
    private boolean almostDue;

    public Long getPayableId() {
        return payableId;
    }

    public void setPayableId(Long payableId) {
        this.payableId = payableId;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isActual() {
        return actual;
    }

    public void setActual(boolean actual) {
        this.actual = actual;
    }

    public boolean isOverDue() {
        return overDue;
    }

    public void setOverDue(boolean overDue) {
        this.overDue = overDue;
    }

    public boolean isAlmostDue() {
        return almostDue;
    }

    public void setAlmostDue(boolean almostDue) {
        this.almostDue = almostDue;
    }
}
