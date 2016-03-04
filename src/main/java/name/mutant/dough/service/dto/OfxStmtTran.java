package name.mutant.dough.service.dto;

import name.mutant.dough.domain.TranType;

import java.math.BigDecimal;
import java.util.Date;

public class OfxStmtTran {
    private TranType type;
    private Date postDate;
    private BigDecimal amount;
    private String fitId;
    private String checkNumber;
    private String correctFitId;
    private CorrectAction correctAction;
    private String name;
    private String memo;

    public TranType getType() {
        return type;
    }

    public void setType(TranType type) {
        this.type = type;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFitId() {
        return fitId;
    }

    public void setFitId(String fitId) {
        this.fitId = fitId;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getCorrectFitId() {
        return correctFitId;
    }

    public void setCorrectFitId(String correctFitId) {
        this.correctFitId = correctFitId;
    }

    public CorrectAction getCorrectAction() {
        return correctAction;
    }

    public void setCorrectAction(CorrectAction correctAction) {
        this.correctAction = correctAction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
