package norman.dough.domain;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;

@Entity
public class DataFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Integer version = 0;
    @Column(length = 100, nullable = false)
    private String originalFilename;
    @Column(length = 100, nullable = false)
    private String contentType;
    @Column(nullable = false)
    private Long size;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date uploadTimestamp;
    @Column(length = 10)
    private String status;
    @Column(length = 50)
    private String ofxOrganization;
    @Column(length = 10)
    private String ofxFid;
    @Column(length = 10)
    private String ofxBankId;
    @Column(length = 20)
    private String ofxAcctId;
    @Column(length = 10)
    private String ofxType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acct_id")
    private Acct acct;

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

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getUploadTimestamp() {
        return uploadTimestamp;
    }

    public void setUploadTimestamp(Date uploadTimestamp) {
        this.uploadTimestamp = uploadTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOfxOrganization() {
        return ofxOrganization;
    }

    public void setOfxOrganization(String ofxOrganization) {
        this.ofxOrganization = ofxOrganization;
    }

    public String getOfxFid() {
        return ofxFid;
    }

    public void setOfxFid(String ofxFid) {
        this.ofxFid = ofxFid;
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

    public String getOfxType() {
        return ofxType;
    }

    public void setOfxType(String ofxType) {
        this.ofxType = ofxType;
    }

    public Acct getAcct() {
        return acct;
    }

    public void setAcct(Acct acct) {
        this.acct = acct;
    }

    @Override
    public String toString() {
        return originalFilename;
    }
}
