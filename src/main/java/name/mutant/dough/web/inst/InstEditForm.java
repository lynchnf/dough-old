package name.mutant.dough.web.inst;

import name.mutant.dough.data.Acct;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lynchnf on 9/15/17.
 */
public class InstEditForm {
    private Long id;
    private Integer version;
    private String organization;
    private String fid;

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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    @Override
    public String toString() {
        return "InstEditForm{" + "id=" + id + ", version=" + version + ", organization='" + organization + '\'' + ", " +
                "fid='" + fid + '\'' + '}';
    }
}