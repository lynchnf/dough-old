package name.mutant.dough.service;

import name.mutant.dough.data.AcctType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lynchnf on 11/8/17.
 */
@Service
public class AcctService {
    public List<AcctBalance> getAccountBalances() {
        AcctType[] acctTypes = AcctType.values();

        List<AcctBalance> acctBalances = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AcctBalance acctBalance = new AcctBalance();
            acctBalance.setId((long) i);
            acctBalance.setName("Some Bank Account");
            int at = i % acctTypes.length;
            acctBalance.setType(acctTypes[at]);
            acctBalance.setBalance(BigDecimal.valueOf(12345, 1));
            acctBalance.setLastTransDate(new Date());
            acctBalances.add(acctBalance);
        }
        return acctBalances;
    }
}