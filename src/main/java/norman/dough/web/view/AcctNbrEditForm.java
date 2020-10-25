package norman.dough.web.view;

import norman.dough.domain.Acct;
import norman.dough.domain.AcctNbr;
import norman.dough.exception.NotFoundException;
import norman.dough.service.AcctService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class AcctNbrEditForm {
    private AcctService acctService;
    private Long id;
    private Integer version = 0;
    @NotNull(message = "Account may not be blank.")
    private Long acctId;
    @NotBlank(message = "Number may not be blank.")
    @Size(max = 50, message = "Number may not be over {max} characters long.")
    private String number;
    @NotNull(message = "Effective Date may not be blank.")
    @DateTimeFormat(pattern = "M/d/yyyy")
    private Date effDate;

    public AcctNbrEditForm() {
    }

    public AcctNbrEditForm(AcctNbr entity) {
        id = entity.getId();
        version = entity.getVersion();
        if (entity.getAcct() != null) {
            acctId = entity.getAcct().getId();
        }
        number = entity.getNumber();
        effDate = entity.getEffDate();
    }

    public AcctNbr toEntity() throws NotFoundException {
        AcctNbr entity = new AcctNbr();
        entity.setId(id);
        entity.setVersion(version);
        if (acctId != null) {
            Acct acct = acctService.findById(acctId);
            entity.setAcct(acct);
        }
        entity.setNumber(StringUtils.trimToNull(number));
        entity.setEffDate(effDate);
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

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }
}
