package name.mutant.dough.service;

import name.mutant.dough.DoughInconceivableException;
import name.mutant.dough.DoughNotFoundException;
import name.mutant.dough.DoughOptimisticLockingException;
import name.mutant.dough.domain.Acct;
import name.mutant.dough.domain.AcctNbr;
import name.mutant.dough.domain.Tran;
import name.mutant.dough.repository.AcctNbrRepository;
import name.mutant.dough.repository.AcctRepository;
import name.mutant.dough.repository.TranRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AcctService {
    private static final Logger logger = LoggerFactory.getLogger(AcctService.class);
    @Autowired
    private AcctRepository acctRepository;
    @Autowired
    private AcctNbrRepository acctNbrRepository;
    @Autowired
    private TranRepository tranRepository;

    public Iterable<Acct> findAllAccts() {
        return acctRepository.findAll();
    }

    public Acct findAcctById(Long acctId) throws DoughNotFoundException {
        Optional<Acct> optional = acctRepository.findById(acctId);
        if (!optional.isPresent()) {
            throw new DoughNotFoundException("Acct not found, acctId=\"" + acctId + "\"");
        }
        return optional.get();
    }

    public Acct saveAcct(Acct acct) throws DoughOptimisticLockingException {
        try {
            return acctRepository.save(acct);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new DoughOptimisticLockingException(
                    "Optimistic locking failure while saving acct, acctId=\"" + acct.getId() + "\"", e);
        }
    }

    public List<Acct> findAcctsByFid(String fid) {
        return acctRepository.findByFid(fid);
    }

    public AcctNbr findCurrentAcctNbrByAcctId(Long acctId) {
        List<AcctNbr> acctNbrList = acctNbrRepository.findTopByAcct_IdOrderByEffDateDesc(acctId);
        // This should never happen. If we have an account, we should have at least one account number.
        if (acctNbrList.isEmpty()) {
            String msg = "No acctNbrs found, acctId=\"" + acctId + "\"";
            logger.error(msg);
            throw new DoughInconceivableException(msg);
        }
        return acctNbrList.iterator().next();
    }

    public List<AcctNbr> findAcctNbrsByFidAndNumber(String fid, String number) {
        return acctNbrRepository.findByAcct_FidAndNumber(fid, number);
    }

    public List<Tran> findTransByAcctIdAndFitId(Long acctId, String fitId) {
        return tranRepository.findByAcct_IdAndFitId(acctId, fitId);
    }

    public List<TranBalanceBean> findTranBalancesByAcctId(Long acctId) throws DoughNotFoundException {
        List<TranBalanceBean> tranBalances = new ArrayList<>();
        Acct acct = findAcctById(acctId);
        BigDecimal balance = acct.getBeginBalance();
        List<Tran> trans = acct.getTrans();
        trans.sort(Comparator.comparing(Tran::getPostDate));
        for (Tran tran : trans) {
            BigDecimal amount = tran.getAmount();
            balance = balance.add(amount);
            String categoryName = null;
            if (tran.getCategory() != null)
                categoryName = tran.getCategory().getName();
            TranBalanceBean tranBalance = new TranBalanceBean(tran.getId(), tran.getType(), tran.getPostDate(),
                    tran.getCheckNumber(), tran.getName(), tran.getReconciled(), amount, balance, categoryName);
            tranBalances.add(tranBalance);
        }
        Collections.reverse(tranBalances);
        return tranBalances;
    }

    public List<AcctSummaryBean> findAllAcctSummaries() {
        List<AcctSummaryBean> acctSummaries = new ArrayList<>();
        Iterable<Acct> accts = findAllAccts();
        for (Acct acct : accts) {
            BigDecimal balance = acct.getBeginBalance();
            Date lastTranDate = acct.getBeginDate();
            List<Tran> trans = acct.getTrans();
            for (Tran tran : trans) {
                balance = balance.add(tran.getAmount());
                if (lastTranDate.before(tran.getPostDate())) {
                    lastTranDate = tran.getPostDate();
                }
            }
            AcctSummaryBean acctSummary = new AcctSummaryBean(acct.getId(), acct.getName(), acct.getType(),
                    acct.getCreditLimit(), balance, lastTranDate);
            acctSummaries.add(acctSummary);
        }
        return acctSummaries;
    }
}