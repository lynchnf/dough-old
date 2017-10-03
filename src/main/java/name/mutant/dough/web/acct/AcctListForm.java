package name.mutant.dough.web.acct;

import name.mutant.dough.data.Acct;
import name.mutant.dough.data.AcctType;
import name.mutant.dough.web.ListForm;
import org.springframework.data.domain.Page;

/**
 * Created by lynchnf on 9/21/17.
 */
public class AcctListForm extends ListForm<Acct> {
    private String whereOrganization;
    private String whereName;
    private AcctType whereType;
    private String whereAcctNbr;

    public AcctListForm(Page<Acct> innerPage, String whereOrganization, String whereName, AcctType whereType, String
            whereAcctNbr) {
        super(innerPage);
        this.whereOrganization = whereOrganization;
        this.whereName = whereName;
        this.whereType = whereType;
        this.whereAcctNbr = whereAcctNbr;
    }

    public String getWhereOrganization() {
        return whereOrganization;
    }

    public String getWhereName() {
        return whereName;
    }

    public AcctType getWhereType() {
        return whereType;
    }

    public String getWhereAcctNbr() {
        return whereAcctNbr;
    }
}