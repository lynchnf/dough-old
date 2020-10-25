package norman.dough.web.view;

import norman.dough.domain.AcctNbr;
import norman.dough.domain.Acct;

import java.math.BigDecimal;
import java.util.Date;

public class AcctNbrView {
    private Long id;
    private Acct acct;
    private String number;
    private Date effDate;

    public AcctNbrView(AcctNbr entity) {
        id = entity.getId();
        acct = entity.getAcct();
        number = entity.getNumber();
        effDate = entity.getEffDate();
    }

    public Long getId() {
        return id;
    }

    public Acct getAcct() {
        return acct;
    }

    public String getNumber() {
        return number;
    }

    public Date getEffDate() {
        return effDate;
    }
}
