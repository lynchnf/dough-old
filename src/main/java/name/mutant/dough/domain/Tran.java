package name.mutant.dough.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "TRAN", uniqueConstraints = {@UniqueConstraint(columnNames = {"ACCT_ID", "FITID"})})
public class Tran implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Integer version;
    private Acct acct;
    private TranType type;
    private Date postDate;
    private Date userDate;
    private BigDecimal amount;
    private String fitId;
    private String sic;
    private String checkNumber;
    private String name;
    private String memo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCT_ID", nullable = false)
    public Acct getAcct() {
        return acct;
    }

    public void setAcct(Acct acct) {
        this.acct = acct;
    }

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    public TranType getType() {
        return type;
    }

    public void setType(TranType type) {
        this.type = type;
    }

    @Column(name = "POST_DATE")
    @Temporal(TemporalType.DATE)
    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    @Column(name = "USER_DATE")
    @Temporal(TemporalType.DATE)
    public Date getUserDate() {
        return userDate;
    }

    public void setUserDate(Date userDate) {
        this.userDate = userDate;
    }

    @Column(name = "AMOUNT", precision = 9, scale = 2)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column(name = "FITID", length = 100, nullable = false)
    public String getFitId() {
        return fitId;
    }

    public void setFitId(String fitId) {
        this.fitId = fitId;
    }

    @Column(name = "SIC", length = 10)
    public String getSic() {
        return sic;
    }

    public void setSic(String sic) {
        this.sic = sic;
    }

    @Column(name = "CHECK_NUMBER", length = 10)
    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    @Column(name = "NAME", length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "MEMO", length = 255)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
