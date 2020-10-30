package norman.dough.web.view;

import norman.dough.domain.Acct;
import norman.dough.domain.Stmt;
import norman.dough.exception.NotFoundException;
import norman.dough.service.AcctService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class StmtEditForm {
    private AcctService acctService;
    private Long id;
    private Integer version = 0;
    @NotNull(message = "Account may not be blank.")
    private Long acctId;
    @Digits(integer = 9, fraction = 2, message = "Open Balance value out of bounds. (<{integer} digits>.<{fraction} digits> expected)")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal openBalance;
    @Digits(integer = 9, fraction = 2, message = "Credits value out of bounds. (<{integer} digits>.<{fraction} digits> expected)")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal credits;
    @Digits(integer = 9, fraction = 2, message = "Debits value out of bounds. (<{integer} digits>.<{fraction} digits> expected)")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal debits;
    @Digits(integer = 9, fraction = 2, message = "Fees value out of bounds. (<{integer} digits>.<{fraction} digits> expected)")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal fees;
    @Digits(integer = 9, fraction = 2, message = "Interest value out of bounds. (<{integer} digits>.<{fraction} digits> expected)")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal interest;
    @NotNull(message = "Close Balance may not be blank.")
    @Digits(integer = 9, fraction = 2, message = "Close Balance value out of bounds. (<{integer} digits>.<{fraction} digits> expected)")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal closeBalance;
    @Digits(integer = 9, fraction = 2, message = "Minimum Due value out of bounds. (<{integer} digits>.<{fraction} digits> expected)")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal minimumDue;
    @DateTimeFormat(pattern = "M/d/yyyy")
    private Date dueDate;
    @NotNull(message = "Close Date may not be blank.")
    @DateTimeFormat(pattern = "M/d/yyyy")
    private Date closeDate;

    public StmtEditForm() {
    }

    public StmtEditForm(Stmt entity) {
        id = entity.getId();
        version = entity.getVersion();
        if (entity.getAcct() != null) {
            acctId = entity.getAcct().getId();
        }
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

    public Stmt toEntity() throws NotFoundException {
        Stmt entity = new Stmt();
        entity.setId(id);
        entity.setVersion(version);
        if (acctId != null) {
            Acct acct = acctService.findById(acctId);
            entity.setAcct(acct);
        }
        entity.setOpenBalance(openBalance);
        entity.setCredits(credits);
        entity.setDebits(debits);
        entity.setFees(fees);
        entity.setInterest(interest);
        entity.setCloseBalance(closeBalance);
        entity.setMinimumDue(minimumDue);
        entity.setDueDate(dueDate);
        entity.setCloseDate(closeDate);
        return entity;
    }

    public void setAcctService(AcctService acctService) {
        this.acctService = acctService;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public BigDecimal getOpenBalance() {
        return openBalance;
    }

    public void setOpenBalance(BigDecimal openBalance) {
        this.openBalance = openBalance;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public BigDecimal getDebits() {
        return debits;
    }

    public void setDebits(BigDecimal debits) {
        this.debits = debits;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public void setFees(BigDecimal fees) {
        this.fees = fees;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getCloseBalance() {
        return closeBalance;
    }

    public void setCloseBalance(BigDecimal closeBalance) {
        this.closeBalance = closeBalance;
    }

    public BigDecimal getMinimumDue() {
        return minimumDue;
    }

    public void setMinimumDue(BigDecimal minimumDue) {
        this.minimumDue = minimumDue;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }
}
