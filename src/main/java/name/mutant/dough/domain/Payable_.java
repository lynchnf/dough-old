package name.mutant.dough.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import java.util.Date;

@StaticMetamodel(Payable.class)
public class Payable_ {
    public static volatile SingularAttribute<Payable, Long> id;
    public static volatile SingularAttribute<Payable, Integer> version;
    public static volatile SingularAttribute<Payable, Acct> acct;
    public static volatile SingularAttribute<Payable, Date> estDueDate;
    public static volatile SingularAttribute<Payable, BigDecimal> estAmount;
    public static volatile SingularAttribute<Payable, Date> actDueDate;
    public static volatile SingularAttribute<Payable, BigDecimal> actAmount;
    public static volatile SingularAttribute<Payable, String> memo;
    public static volatile SingularAttribute<Payable, Boolean> paid;
}
