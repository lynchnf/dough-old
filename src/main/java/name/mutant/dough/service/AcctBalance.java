package name.mutant.dough.service;

import name.mutant.dough.data.AcctType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lynchnf on 11/8/17.
 */
public class AcctBalance {
    private Long id;
    private String name;
    private AcctType type;
    private BigDecimal balance;
    private Date lastTransDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AcctType getType() {
        return type;
    }

    public void setType(AcctType type) {
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getLastTransDate() {
        return lastTransDate;
    }

    public void setLastTransDate(Date lastTransDate) {
        this.lastTransDate = lastTransDate;
    }
}