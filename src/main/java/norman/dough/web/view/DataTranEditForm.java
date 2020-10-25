package norman.dough.web.view;

import norman.dough.domain.DataFile;
import norman.dough.domain.DataTran;
import norman.dough.exception.NotFoundException;
import norman.dough.service.DataFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

public class DataTranEditForm {
    private DataFileService dataFileService;
    private Long id;
    private Integer version = 0;
    @NotNull(message = "Data File may not be blank.")
    private Long dataFileId;
    @Size(max = 10, message = "Type may not be over {max} characters long.")
    private String ofxType;
    @NotNull(message = "Post Date may not be blank.")
    @DateTimeFormat(pattern = "M/d/yyyy")
    private Date ofxPostDate;
    @DateTimeFormat(pattern = "M/d/yyyy")
    private Date ofxUserDate;
    @NotNull(message = "Amount may not be blank.")
    @Digits(integer = 9, fraction = 2, message = "Amount value out of bounds. (<{integer} digits>.<{fraction} digits> expected)")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal ofxAmount;
    @Size(max = 50, message = "Fit Id may not be over {max} characters long.")
    private String ofxFitId;
    @Size(max = 10, message = "Sic may not be over {max} characters long.")
    private String ofxSic;
    @Size(max = 10, message = "Check Number may not be over {max} characters long.")
    private String ofxCheckNumber;
    @Size(max = 10, message = "Correct Fit Id may not be over {max} characters long.")
    private String ofxCorrectFitId;
    @Size(max = 10, message = "Correct Action may not be over {max} characters long.")
    private String ofxCorrectAction;
    @Size(max = 100, message = "Name may not be over {max} characters long.")
    private String ofxName;
    @Size(max = 10, message = "Category may not be over {max} characters long.")
    private String ofxCategory;
    @Size(max = 100, message = "Memo may not be over {max} characters long.")
    private String ofxMemo;

    public DataTranEditForm() {
    }

    public DataTranEditForm(DataTran entity) {
        id = entity.getId();
        version = entity.getVersion();
        if (entity.getDataFile() != null) {
            dataFileId = entity.getDataFile().getId();
        }
        ofxType = entity.getOfxType();
        ofxPostDate = entity.getOfxPostDate();
        ofxUserDate = entity.getOfxUserDate();
        ofxAmount = entity.getOfxAmount();
        ofxFitId = entity.getOfxFitId();
        ofxSic = entity.getOfxSic();
        ofxCheckNumber = entity.getOfxCheckNumber();
        ofxCorrectFitId = entity.getOfxCorrectFitId();
        ofxCorrectAction = entity.getOfxCorrectAction();
        ofxName = entity.getOfxName();
        ofxCategory = entity.getOfxCategory();
        ofxMemo = entity.getOfxMemo();
    }

    public DataTran toEntity() throws NotFoundException {
        DataTran entity = new DataTran();
        entity.setId(id);
        entity.setVersion(version);
        if (dataFileId != null) {
            DataFile dataFile = dataFileService.findById(dataFileId);
            entity.setDataFile(dataFile);
        }
        entity.setOfxType(StringUtils.trimToNull(ofxType));
        entity.setOfxPostDate(ofxPostDate);
        entity.setOfxUserDate(ofxUserDate);
        entity.setOfxAmount(ofxAmount);
        entity.setOfxFitId(StringUtils.trimToNull(ofxFitId));
        entity.setOfxSic(StringUtils.trimToNull(ofxSic));
        entity.setOfxCheckNumber(StringUtils.trimToNull(ofxCheckNumber));
        entity.setOfxCorrectFitId(StringUtils.trimToNull(ofxCorrectFitId));
        entity.setOfxCorrectAction(StringUtils.trimToNull(ofxCorrectAction));
        entity.setOfxName(StringUtils.trimToNull(ofxName));
        entity.setOfxCategory(StringUtils.trimToNull(ofxCategory));
        entity.setOfxMemo(StringUtils.trimToNull(ofxMemo));
        return entity;
    }

    public void setDataFileService(DataFileService dataFileService) {
        this.dataFileService = dataFileService;
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

    public Long getDataFileId() {
        return dataFileId;
    }

    public void setDataFileId(Long dataFileId) {
        this.dataFileId = dataFileId;
    }

    public String getOfxType() {
        return ofxType;
    }

    public void setOfxType(String ofxType) {
        this.ofxType = ofxType;
    }

    public Date getOfxPostDate() {
        return ofxPostDate;
    }

    public void setOfxPostDate(Date ofxPostDate) {
        this.ofxPostDate = ofxPostDate;
    }

    public Date getOfxUserDate() {
        return ofxUserDate;
    }

    public void setOfxUserDate(Date ofxUserDate) {
        this.ofxUserDate = ofxUserDate;
    }

    public BigDecimal getOfxAmount() {
        return ofxAmount;
    }

    public void setOfxAmount(BigDecimal ofxAmount) {
        this.ofxAmount = ofxAmount;
    }

    public String getOfxFitId() {
        return ofxFitId;
    }

    public void setOfxFitId(String ofxFitId) {
        this.ofxFitId = ofxFitId;
    }

    public String getOfxSic() {
        return ofxSic;
    }

    public void setOfxSic(String ofxSic) {
        this.ofxSic = ofxSic;
    }

    public String getOfxCheckNumber() {
        return ofxCheckNumber;
    }

    public void setOfxCheckNumber(String ofxCheckNumber) {
        this.ofxCheckNumber = ofxCheckNumber;
    }

    public String getOfxCorrectFitId() {
        return ofxCorrectFitId;
    }

    public void setOfxCorrectFitId(String ofxCorrectFitId) {
        this.ofxCorrectFitId = ofxCorrectFitId;
    }

    public String getOfxCorrectAction() {
        return ofxCorrectAction;
    }

    public void setOfxCorrectAction(String ofxCorrectAction) {
        this.ofxCorrectAction = ofxCorrectAction;
    }

    public String getOfxName() {
        return ofxName;
    }

    public void setOfxName(String ofxName) {
        this.ofxName = ofxName;
    }

    public String getOfxCategory() {
        return ofxCategory;
    }

    public void setOfxCategory(String ofxCategory) {
        this.ofxCategory = ofxCategory;
    }

    public String getOfxMemo() {
        return ofxMemo;
    }

    public void setOfxMemo(String ofxMemo) {
        this.ofxMemo = ofxMemo;
    }
}
