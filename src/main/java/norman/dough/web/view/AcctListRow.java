package norman.dough.web.view;

import norman.dough.domain.Acct;

import java.math.BigDecimal;
import java.util.Date;

public class AcctListRow {
    private Long id;
    private String name;
    private String nickname;
    private String type;
    private BigDecimal creditLimit;
    private Boolean active;

    public AcctListRow(Acct entity) {
        id = entity.getId();
        name = entity.getName();
        nickname = entity.getNickname();
        type = entity.getType();
        creditLimit = entity.getCreditLimit();
        active = entity.getActive();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public Boolean getActive() {
        return active;
    }
}
