package name.mutant.dough.service.response;

/**
 * Created by lynchnf on 9/15/17.
 */
public class OfxAcct {
    private String bankId;
    private String acctId;
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}