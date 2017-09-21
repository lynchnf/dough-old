package name.mutant.dough.service.response;

import name.mutant.dough.data.AcctType;

/**
 * Created by lynchnf on 9/15/17.
 */
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