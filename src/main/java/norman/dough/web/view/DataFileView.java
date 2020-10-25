package norman.dough.web.view;

import norman.dough.domain.Acct;
import norman.dough.domain.DataFile;

import java.util.Date;

public class DataFileView {
    private Long id;
    private String originalFilename;
    private String contentType;
    private Long size;
    private Date uploadTimestamp;
    private String status;
    private String ofxOrganization;
    private String ofxFid;
    private String ofxBankId;
    private String ofxAcctId;
    private String ofxType;
    private Acct acct;

    public DataFileView(DataFile entity) {
        id = entity.getId();
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
        acct = entity.getAcct();
    }

    public Long getId() {
        return id;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public String getContentType() {
        return contentType;
    }

    public Long getSize() {
        return size;
    }

    public Date getUploadTimestamp() {
        return uploadTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getOfxOrganization() {
        return ofxOrganization;
    }

    public String getOfxFid() {
        return ofxFid;
    }

    public String getOfxBankId() {
        return ofxBankId;
    }

    public String getOfxAcctId() {
        return ofxAcctId;
    }

    public String getOfxType() {
        return ofxType;
    }

    public Acct getAcct() {
        return acct;
    }
}
