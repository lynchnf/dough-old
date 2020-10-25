package norman.dough.web.view;

import norman.dough.domain.Acct;
import norman.dough.domain.Stmt;

import java.math.BigDecimal;
import java.util.Date;

public class StmtView {
    private Long id;
    private Acct acct;
    private BigDecimal openBalance;
    private BigDecimal credits;
    private BigDecimal debits;
    private BigDecimal fees;
    private BigDecimal interest;
    private BigDecimal closeBalance;
    private BigDecimal minimumDue;
    private Date dueDate;
    private Date closeDate;

    public StmtView(Stmt entity) {
        id = entity.getId();
        acct = entity.getAcct();
        openBalance = entity.getOpenBalance();
        credits = entity.getCredits();
        debits = entity.getDebits();
        fees = entity.getFees();
        interest = entity.getInterest();
        closeBalance = entity.getCloseBalance();
        minimumDue = entity.getMinimumDue();
        dueDate = entity.getDueDate();
        closeDate = entity.getCloseDate();
    }

    public Long getId() {
        return id;
    }

    public Acct getAcct() {
        return acct;
    }

    public BigDecimal getOpenBalance() {
        return openBalance;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public BigDecimal getDebits() {
        return debits;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public BigDecimal getCloseBalance() {
        return closeBalance;
    }

    public BigDecimal getMinimumDue() {
        return minimumDue;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }
}
