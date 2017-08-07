package name.mutant.dough.web;

import name.mutant.dough.data.Acct;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by lynchnf on 7/14/17.
 */
public class AcctEditForm {
    private Long id;
    private Integer version;
    private String name;

    public AcctEditForm() {
    }

    public AcctEditForm(Acct acct) {
        id = acct.getId();
        version = acct.getVersion();
        name = acct.getName();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Acct toAcct() {
        Acct acct = new Acct();
        acct.setId(id);
        acct.setVersion(version);
        acct.setName(StringUtils.trimToNull(name));
        return acct;
    }
}