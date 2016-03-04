package name.mutant.dough.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import java.util.Date;

@StaticMetamodel(Tran.class)
public abstract class Tran_ {
    public static volatile SingularAttribute<Tran, Long> id;
    public static volatile SingularAttribute<Tran, Integer> version;
    public static volatile SingularAttribute<Tran, Acct> acct;
    public static volatile SingularAttribute<Tran, TranType> type;
    public static volatile SingularAttribute<Tran, Date> postDate;
    public static volatile SingularAttribute<Tran, BigDecimal> amount;
    public static volatile SingularAttribute<Tran, String> fitId;
    public static volatile SingularAttribute<Tran, String> checkNumber;
    public static volatile SingularAttribute<Tran, String> name;
    public static volatile SingularAttribute<Tran, String> memo;
}
