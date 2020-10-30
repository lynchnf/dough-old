package norman.dough.web.view;

import norman.dough.domain.Tran;

import java.math.BigDecimal;
import java.util.Date;

public class TranListRow {
    private Long id;
    private String status;
    private Date postDate;
    private BigDecimal amount;
    private String checkNumber;
    private String name;
    private Boolean voided;

    public TranListRow(Tran entity) {
        id = entity.getId();
        status = entity.getStatus();
        postDate = entity.getPostDate();
        amount = entity.getAmount();
        checkNumber = entity.getCheckNumber();
        name = entity.getName();
        voided = entity.getVoided();
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Date getPostDate() {
        return postDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public String getName() {
        return name;
    }

    public Boolean getVoided() {
        return voided;
    }
}
