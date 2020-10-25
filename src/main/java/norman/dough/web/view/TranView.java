package norman.dough.web.view;

import norman.dough.domain.Tran;
import norman.dough.domain.Stmt;

import java.math.BigDecimal;
import java.util.Date;

public class TranView {
    private Long id;
    private Stmt stmt;
    private String status;
    private Date postDate;
    private Date manualPostDate;
    private Date uploadedPostDate;
    private BigDecimal amount;
    private BigDecimal manualAmount;
    private BigDecimal uploadedAmount;
    private String checkNumber;
    private String manualCheckNumber;
    private String uploadedCheckNumber;
    private String name;
    private String manualName;
    private String uploadedName;
    private String memo;
    private String manualMemo;
    private String uploadedMemo;
    private Boolean voided;

    public TranView(Tran entity) {
        id = entity.getId();
        stmt = entity.getStmt();
        status = entity.getStatus();
        postDate = entity.getPostDate();
        manualPostDate = entity.getManualPostDate();
        uploadedPostDate = entity.getUploadedPostDate();
        amount = entity.getAmount();
        manualAmount = entity.getManualAmount();
        uploadedAmount = entity.getUploadedAmount();
        checkNumber = entity.getCheckNumber();
        manualCheckNumber = entity.getManualCheckNumber();
        uploadedCheckNumber = entity.getUploadedCheckNumber();
        name = entity.getName();
        manualName = entity.getManualName();
        uploadedName = entity.getUploadedName();
        memo = entity.getMemo();
        manualMemo = entity.getManualMemo();
        uploadedMemo = entity.getUploadedMemo();
        voided = entity.getVoided();
    }

    public Long getId() {
        return id;
    }

    public Stmt getStmt() {
        return stmt;
    }

    public String getStatus() {
        return status;
    }

    public Date getPostDate() {
        return postDate;
    }

    public Date getManualPostDate() {
        return manualPostDate;
    }

    public Date getUploadedPostDate() {
        return uploadedPostDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getManualAmount() {
        return manualAmount;
    }

    public BigDecimal getUploadedAmount() {
        return uploadedAmount;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public String getManualCheckNumber() {
        return manualCheckNumber;
    }

    public String getUploadedCheckNumber() {
        return uploadedCheckNumber;
    }

    public String getName() {
        return name;
    }

    public String getManualName() {
        return manualName;
    }

    public String getUploadedName() {
        return uploadedName;
    }

    public String getMemo() {
        return memo;
    }

    public String getManualMemo() {
        return manualMemo;
    }

    public String getUploadedMemo() {
        return uploadedMemo;
    }

    public Boolean getVoided() {
        return voided;
    }
}
