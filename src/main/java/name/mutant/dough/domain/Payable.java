package name.mutant.dough.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "payable")
public class Payable implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Integer version;
    private Payee payee;
    private Date estDueDate;
    private BigDecimal estAmount;
    private Date actDueDate;
    private BigDecimal actAmount;
    private String memo;
    private Date paidDate;
    private BigDecimal paidAmount;
    private String confirmCode;
    private Boolean noBill;
    private Boolean missedBill;

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
    @JoinColumn(name = "PAYEE_ID", nullable = false)
    public Payee getPayee() {
        return payee;
    }

    public void setPayee(Payee payee) {
        this.payee = payee;
    }

    @Column(name = "EST_DUE_DATE")
    @Temporal(TemporalType.DATE)
    public Date getEstDueDate() {
        return estDueDate;
    }

    public void setEstDueDate(Date estDueDate) {
        this.estDueDate = estDueDate;
    }

    @Column(name = "EST_AMOUNT", precision = 9, scale = 2)
    public BigDecimal getEstAmount() {
        return estAmount;
    }

    public void setEstAmount(BigDecimal estAmount) {
        this.estAmount = estAmount;
    }

    @Column(name = "ACT_DUE_DATE")
    @Temporal(TemporalType.DATE)
    public Date getActDueDate() {
        return actDueDate;
    }

    public void setActDueDate(Date actDueDate) {
        this.actDueDate = actDueDate;
    }

    @Column(name = "ACT_AMOUNT", precision = 9, scale = 2)
    public BigDecimal getActAmount() {
        return actAmount;
    }

    public void setActAmount(BigDecimal actAmount) {
        this.actAmount = actAmount;
    }

    @Column(name = "MEMO", length = 255)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "PAID_DATE")
    @Temporal(TemporalType.DATE)
    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    @Column(name = "PAID_AMOUNT", precision = 9, scale = 2)
    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    @Column(name = "CONFIRM_CODE", length = 20)
    public String getConfirmCode() {
        return confirmCode;
    }

    public void setConfirmCode(String confirmCode) {
        this.confirmCode = confirmCode;
    }

    @Column(name = "NO_BILL")
    public Boolean getNoBill() {
        return noBill;
    }

    public void setNoBill(Boolean noBill) {
        this.noBill = noBill;
    }

    @Column(name = "MISSED_BILL")
    public Boolean getMissedBill() {
        return missedBill;
    }

    public void setMissedBill(Boolean missedBill) {
        this.missedBill = missedBill;
    }
}
