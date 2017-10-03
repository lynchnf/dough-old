package name.mutant.dough.web.acct;

import name.mutant.dough.data.Acct;
import name.mutant.dough.data.AcctType;
import name.mutant.dough.data.Inst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lynchnf on 7/14/17.
 */
public class AcctEditForm {
    private static final Logger logger = LoggerFactory.getLogger(AcctEditForm.class);
    private Long id;
    private Integer version;
    private Long instId;
    private String acctNbr;
    private String name;
    private String ofxBankId;
    private String ofxAcctId;
    private AcctType type;
    private Date beginDate;
    private BigDecimal beginBalance;
    private List<Inst> allInsts = new ArrayList<>();

    public AcctEditForm() {
    }

    public AcctEditForm(Acct acct) {
        id = acct.getId();
        version = acct.getVersion();
        instId = acct.getInst() == null ? null : acct.getInst().getId();
        acctNbr = acct.getAcctNbr();
        name = acct.getName();
        ofxBankId = acct.getOfxBankId();
        ofxAcctId = acct.getOfxAcctId();
        type = acct.getType();
        beginDate = acct.getBeginDate();
        beginBalance = acct.getBeginBalance();
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

    public Long getInstId() {
        return instId;
    }

    public void setInstId(Long instId) {
        this.instId = instId;
    }

    public String getAcctNbr() {
        return acctNbr;
    }

    public void setAcctNbr(String acctNbr) {
        this.acctNbr = acctNbr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfxBankId() {
        return ofxBankId;
    }

    public void setOfxBankId(String ofxBankId) {
        this.ofxBankId = ofxBankId;
    }

    public String getOfxAcctId() {
        return ofxAcctId;
    }

    public void setOfxAcctId(String ofxAcctId) {
        this.ofxAcctId = ofxAcctId;
    }

    public AcctType getType() {
        return type;
    }

    public void setType(AcctType type) {
        this.type = type;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public BigDecimal getBeginBalance() {
        return beginBalance;
    }

    public void setBeginBalance(BigDecimal beginBalance) {
        this.beginBalance = beginBalance;
    }

    public List<Inst> getAllInsts() {
        return allInsts;
    }
}