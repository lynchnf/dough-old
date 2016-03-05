package name.mutant.dough.domain;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import java.util.Date;

@StaticMetamodel(Acct.class)
public abstract class Acct_ {
    public static volatile SingularAttribute<Acct, Long> id;
    public static volatile SingularAttribute<Acct, Integer> version;
    public static volatile SingularAttribute<Acct, String> acctNbr;
    public static volatile SingularAttribute<Acct, String> name;
    public static volatile SingularAttribute<Acct, String> organization;
    public static volatile SingularAttribute<Acct, String> fid;
    public static volatile SingularAttribute<Acct, String> ofxBankId;
    public static volatile SingularAttribute<Acct, String> ofxAcctId;
    public static volatile SingularAttribute<Acct, AcctType> type;
    public static volatile SingularAttribute<Acct, Date> beginDate;
    public static volatile SingularAttribute<Acct, BigDecimal> beginBalance;
    public static volatile ListAttribute<Acct, Tran> trans;
}
