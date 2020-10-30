package norman.dough.web.view;

import norman.dough.domain.Stmt;

import java.math.BigDecimal;
import java.util.Date;

public class StmtListRow {
    private Long id;
    private BigDecimal closeBalance;
    private BigDecimal minimumDue;
    private Date dueDate;
    private Date closeDate;

    public StmtListRow(Stmt entity) {
        id = entity.getId();
        closeBalance = entity.getCloseBalance();
        minimumDue = entity.getMinimumDue();
        dueDate = entity.getDueDate();
        closeDate = entity.getCloseDate();
    }

    public Long getId() {
        return id;
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
