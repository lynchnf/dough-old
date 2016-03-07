package name.mutant.dough.domain;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;

@StaticMetamodel(Payee.class)
public abstract class Payee_ {
    public static volatile SingularAttribute<Payee, Long> id;
    public static volatile SingularAttribute<Payee, Integer> version;
    public static volatile SingularAttribute<Payee, String> name;
    public static volatile SingularAttribute<Payee, String> acctNbr;
    public static volatile SingularAttribute<Payee, PayeeType> type;
    public static volatile SingularAttribute<Payee, String> cronExpression;
    public static volatile SingularAttribute<Payee, Integer> nbrEstToCreate;
    public static volatile SingularAttribute<Payee, BigDecimal> estAmount;
    public static volatile ListAttribute<Payee, Payable> payables;
}
