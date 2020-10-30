package norman.dough.web.view;

import norman.dough.domain.Acct;
import norman.dough.domain.DataFile;
import norman.dough.domain.DataFileStatus;
import norman.dough.exception.NotFoundException;
import norman.dough.service.AcctService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class DataFileEditForm {
    private AcctService acctService;
    private Long id;
    private Integer version = 0;
    @NotBlank(message = "Original File Name may not be blank.")
    @Size(max = 100, message = "Original File Name may not be over {max} characters long.")
    private String originalFilename;
    @NotBlank(message = "Content Type may not be blank.")
    @Size(max = 100, message = "Content Type may not be over {max} characters long.")
    private String contentType;
    @NotNull(message = "Size may not be blank.")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Long size;
    @NotNull(message = "Upload Timestamp may not be blank.")
    @DateTimeFormat(pattern = "M/d/yyyy h:m a")
    private Date uploadTimestamp;
    @Size(max = 10, message = "Status may not be over {max} characters long.")
    private DataFileStatus status;
    @Size(max = 50, message = "Organization may not be over {max} characters long.")
    private String ofxOrganization;
    @Size(max = 10, message = "Fid may not be over {max} characters long.")
    private String ofxFid;
    @Size(max = 10, message = "Bank Id may not be over {max} characters long.")
    private String ofxBankId;
    @Size(max = 20, message = "Account Id may not be over {max} characters long.")
    private String ofxAcctId;
    @Size(max = 10, message = "Type may not be over {max} characters long.")
    private String ofxType;
    private Long acctId;

    public DataFileEditForm() {
    }

    public DataFileEditForm(DataFile entity) {
        id = entity.getId();
        version = entity.getVersion();
        originalFilename = entity.getOriginalFilename();
        contentType = entity.getContentType();
        size = entity.getSize();
        uploadTimestamp = entity.getUploadTimestamp();
        status = entity.getStatus();
        ofxOrganization = entity.getOfxOrganization();
        ofxFid = entity.getOfxFid();
        ofxBankId = entity.getOfxBankId();
        ofxAcctId = entity.getOfxAcctId();
        ofxType = entity.getOfxType();
        if (entity.getAcct() != null) {
            acctId = entity.getAcct().getId();
        }
    }

    public DataFile toEntity() throws NotFoundException {
        DataFile entity = new DataFile();
        entity.setId(id);
        entity.setVersion(version);
        entity.setOriginalFilename(StringUtils.trimToNull(originalFilename));
        entity.setContentType(StringUtils.trimToNull(contentType));
        entity.setSize(size);
        entity.setUploadTimestamp(uploadTimestamp);
        entity.setStatus(status);
        entity.setOfxOrganization(StringUtils.trimToNull(ofxOrganization));
        entity.setOfxFid(StringUtils.trimToNull(ofxFid));
        entity.setOfxBankId(StringUtils.trimToNull(ofxBankId));
        entity.setOfxAcctId(StringUtils.trimToNull(ofxAcctId));
        entity.setOfxType(StringUtils.trimToNull(ofxType));
        if (acctId != null) {
            Acct acct = acctService.findById(acctId);
            entity.setAcct(acct);
        }
        return entity;
    }

    public void setAcctService(AcctService acctService) {
        this.acctService = acctService;
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

    public DataFileStatus getStatus() {
        return status;
    }

    public void setStatus(DataFileStatus status) {
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

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }
}
