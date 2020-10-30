package norman.dough.web.view;

import norman.dough.domain.Stmt;
import norman.dough.domain.Tran;
import norman.dough.exception.NotFoundException;
import norman.dough.service.StmtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

public class TranEditForm {
    private StmtService stmtService;
    private Long id;
    private Integer version = 0;
    @NotNull(message = "Statement may not be blank.")
    private Long stmtId;
    @Size(max = 10, message = "Status may not be over {max} characters long.")
    private String status;
    @NotNull(message = "Post Date may not be blank.")
    @DateTimeFormat(pattern = "M/d/yyyy")
    private Date postDate;
    @DateTimeFormat(pattern = "M/d/yyyy")
    private Date manualPostDate;
    @DateTimeFormat(pattern = "M/d/yyyy")
    private Date uploadedPostDate;
    @NotNull(message = "Amount may not be blank.")
    @Digits(integer = 9, fraction = 2, message = "Amount value out of bounds. (<{integer} digits>.<{fraction} digits> expected)")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal amount;
    @Digits(integer = 9, fraction = 2, message = "Manual Amount value out of bounds. (<{integer} digits>.<{fraction} digits> expected)")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal manualAmount;
    @Digits(integer = 9, fraction = 2, message = "Uploaded Amount value out of bounds. (<{integer} digits>.<{fraction} digits> expected)")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal uploadedAmount;
    @Size(max = 10, message = "Check Number may not be over {max} characters long.")
    private String checkNumber;
    @Size(max = 10, message = "Manual Check Number may not be over {max} characters long.")
    private String manualCheckNumber;
    @Size(max = 10, message = "Uploaded Check Number may not be over {max} characters long.")
    private String uploadedCheckNumber;
    @NotBlank(message = "Name may not be blank.")
    @Size(max = 100, message = "Name may not be over {max} characters long.")
    private String name;
    @Size(max = 100, message = "Manual Name may not be over {max} characters long.")
    private String manualName;
    @Size(max = 100, message = "Uploaded Name may not be over {max} characters long.")
    private String uploadedName;
    @Size(max = 100, message = "Memo may not be over {max} characters long.")
    private String memo;
    @Size(max = 100, message = "Manual Memo may not be over {max} characters long.")
    private String manualMemo;
    @Size(max = 100, message = "Uploaded Memo may not be over {max} characters long.")
    private String uploadedMemo;
    @NotNull(message = "Void may not be blank.")
    private Boolean voided;

    public TranEditForm() {
    }

    public TranEditForm(Tran entity) {
        id = entity.getId();
        version = entity.getVersion();
        if (entity.getStmt() != null) {
            stmtId = entity.getStmt().getId();
        }
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

    public Tran toEntity() throws NotFoundException {
        Tran entity = new Tran();
        entity.setId(id);
        entity.setVersion(version);
        if (stmtId != null) {
            Stmt stmt = stmtService.findById(stmtId);
            entity.setStmt(stmt);
        }
        entity.setStatus(StringUtils.trimToNull(status));
        entity.setPostDate(postDate);
        entity.setManualPostDate(manualPostDate);
        entity.setUploadedPostDate(uploadedPostDate);
        entity.setAmount(amount);
        entity.setManualAmount(manualAmount);
        entity.setUploadedAmount(uploadedAmount);
        entity.setCheckNumber(StringUtils.trimToNull(checkNumber));
        entity.setManualCheckNumber(StringUtils.trimToNull(manualCheckNumber));
        entity.setUploadedCheckNumber(StringUtils.trimToNull(uploadedCheckNumber));
        entity.setName(StringUtils.trimToNull(name));
        entity.setManualName(StringUtils.trimToNull(manualName));
        entity.setUploadedName(StringUtils.trimToNull(uploadedName));
        entity.setMemo(StringUtils.trimToNull(memo));
        entity.setManualMemo(StringUtils.trimToNull(manualMemo));
        entity.setUploadedMemo(StringUtils.trimToNull(uploadedMemo));
        entity.setVoided(voided);
        return entity;
    }

    public void setStmtService(StmtService stmtService) {
        this.stmtService = stmtService;
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

    public Long getStmtId() {
        return stmtId;
    }

    public void setStmtId(Long stmtId) {
        this.stmtId = stmtId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Date getManualPostDate() {
        return manualPostDate;
    }

    public void setManualPostDate(Date manualPostDate) {
        this.manualPostDate = manualPostDate;
    }

    public Date getUploadedPostDate() {
        return uploadedPostDate;
    }

    public void setUploadedPostDate(Date uploadedPostDate) {
        this.uploadedPostDate = uploadedPostDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getManualAmount() {
        return manualAmount;
    }

    public void setManualAmount(BigDecimal manualAmount) {
        this.manualAmount = manualAmount;
    }

    public BigDecimal getUploadedAmount() {
        return uploadedAmount;
    }

    public void setUploadedAmount(BigDecimal uploadedAmount) {
        this.uploadedAmount = uploadedAmount;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getManualCheckNumber() {
        return manualCheckNumber;
    }

    public void setManualCheckNumber(String manualCheckNumber) {
        this.manualCheckNumber = manualCheckNumber;
    }

    public String getUploadedCheckNumber() {
        return uploadedCheckNumber;
    }

    public void setUploadedCheckNumber(String uploadedCheckNumber) {
        this.uploadedCheckNumber = uploadedCheckNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManualName() {
        return manualName;
    }

    public void setManualName(String manualName) {
        this.manualName = manualName;
    }

    public String getUploadedName() {
        return uploadedName;
    }

    public void setUploadedName(String uploadedName) {
        this.uploadedName = uploadedName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getManualMemo() {
        return manualMemo;
    }

    public void setManualMemo(String manualMemo) {
        this.manualMemo = manualMemo;
    }

    public String getUploadedMemo() {
        return uploadedMemo;
    }

    public void setUploadedMemo(String uploadedMemo) {
        this.uploadedMemo = uploadedMemo;
    }

    public Boolean getVoided() {
        return voided;
    }

    public void setVoided(Boolean voided) {
        this.voided = voided;
    }
}
