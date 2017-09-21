package name.mutant.dough.data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lynchnf on 7/14/17.
 */
@Entity
public class Acct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Integer version;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inst_id")
    private Inst inst;
    private String acctNbr;
    private String name;
    private String ofxBankId;
    private String ofxAcctId;
    @Enumerated(EnumType.STRING)
    private AcctType type;
    @Temporal(TemporalType.DATE)
    private Date beginDate;
    private BigDecimal beginBalance;

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

    public Inst getInst() {
        return inst;
    }

    public void setInst(Inst inst) {
        this.inst = inst;
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
}