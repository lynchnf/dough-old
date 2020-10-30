package norman.dough.web.view;

import norman.dough.domain.AcctNbr;

import java.util.Date;

public class AcctNbrListRow {
    private Long id;
    private String number;
    private Date effDate;

    public AcctNbrListRow(AcctNbr entity) {
        id = entity.getId();
        number = entity.getNumber();
        effDate = entity.getEffDate();
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Date getEffDate() {
        return effDate;
    }
}
