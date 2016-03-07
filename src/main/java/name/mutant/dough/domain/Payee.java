package name.mutant.dough.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PAYEE")
public class Payee implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Integer version;
    private String name;
    private String acctNbr;
    private PayeeType type;
    private String cronExpression;
    private Integer nbrEstToCreate;
    private BigDecimal estAmount;
    private List<Payable> payables = new ArrayList<>();

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

    @Column(name = "NAME", length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ACCT_NBR", length = 20)
    public String getAcctNbr() {
        return acctNbr;
    }

    public void setAcctNbr(String acctNbr) {
        this.acctNbr = acctNbr;
    }

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    public PayeeType getType() {
        return type;
    }

    public void setType(PayeeType type) {
        this.type = type;
    }

    @Column(name = "CRON_EXPRESSION", length = 20)
    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @Column(name = "NBR_EST_TO_CREATE")
    public Integer getNbrEstToCreate() {
        return nbrEstToCreate;
    }

    public void setNbrEstToCreate(Integer nbrEstToCreate) {
        this.nbrEstToCreate = nbrEstToCreate;
    }

    @Column(name = "EST_AMOUNT", precision = 9, scale = 2)
    public BigDecimal getEstAmount() {
        return estAmount;
    }

    public void setEstAmount(BigDecimal estAmount) {
        this.estAmount = estAmount;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "payee")
    public List<Payable> getPayables() {
        return payables;
    }

    public void setPayables(List<Payable> payables) {
        this.payables = payables;
    }
}
