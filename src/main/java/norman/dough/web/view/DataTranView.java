package norman.dough.web.view;

import norman.dough.domain.DataTran;
import norman.dough.domain.DataFile;

import java.math.BigDecimal;
import java.util.Date;

public class DataTranView {
    private Long id;
    private DataFile dataFile;
    private String ofxType;
    private Date ofxPostDate;
    private Date ofxUserDate;
    private BigDecimal ofxAmount;
    private String ofxFitId;
    private String ofxSic;
    private String ofxCheckNumber;
    private String ofxCorrectFitId;
    private String ofxCorrectAction;
    private String ofxName;
    private String ofxCategory;
    private String ofxMemo;

    public DataTranView(DataTran entity) {
        id = entity.getId();
        dataFile = entity.getDataFile();
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

    public Long getId() {
        return id;
    }

    public DataFile getDataFile() {
        return dataFile;
    }

    public String getOfxType() {
        return ofxType;
    }

    public Date getOfxPostDate() {
        return ofxPostDate;
    }

    public Date getOfxUserDate() {
        return ofxUserDate;
    }

    public BigDecimal getOfxAmount() {
        return ofxAmount;
    }

    public String getOfxFitId() {
        return ofxFitId;
    }

    public String getOfxSic() {
        return ofxSic;
    }

    public String getOfxCheckNumber() {
        return ofxCheckNumber;
    }

    public String getOfxCorrectFitId() {
        return ofxCorrectFitId;
    }

    public String getOfxCorrectAction() {
        return ofxCorrectAction;
    }

    public String getOfxName() {
        return ofxName;
    }

    public String getOfxCategory() {
        return ofxCategory;
    }

    public String getOfxMemo() {
        return ofxMemo;
    }
}
