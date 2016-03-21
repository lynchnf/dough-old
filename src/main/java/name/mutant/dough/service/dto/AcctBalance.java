package name.mutant.dough.service.dto;

import name.mutant.dough.domain.AcctType;

import java.math.BigDecimal;
import java.util.Date;

public class AcctBalance {
    private Long acctId;
    private String acctName;
    private AcctType acctType;
    private BigDecimal balance;
    private Date lastTranDate;

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public AcctType getAcctType() {
        return acctType;
    }

    public void setAcctType(AcctType acctType) {
        this.acctType = acctType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getLastTranDate() {
        return lastTranDate;
    }

    public void setLastTranDate(Date lastTranDate) {
        this.lastTranDate = lastTranDate;
    }
}
