package name.mutant.dough.data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lynchnf on 9/15/17.
 */
@Entity
public class Inst {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Integer version;
    private String organization;
    private String fid;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Acct> accts = new ArrayList<>();

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

    public List<Acct> getAccts() {
        return accts;
    }

    public void setAccts(List<Acct> accts) {
        this.accts = accts;
    }
}