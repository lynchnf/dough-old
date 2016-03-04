package name.mutant.dough.service.dto;

import name.mutant.dough.domain.AcctType;

public class OfxAcct {
    private String bankId;
    private String acctId;
    private AcctType type;

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public AcctType getType() {
        return type;
    }

    public void setType(AcctType type) {
        this.type = type;
    }
}
