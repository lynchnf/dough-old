package name.mutant.dough.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by lynchnf on 7/14/17.
 */
public interface AcctRepository extends PagingAndSortingRepository<Acct, Long> {
    Page<Acct> findByInstOrganizationContainingAndNameContainingAndTypeAndAcctNbrContaining(String organization,
                                                                                            String name, AcctType
                                                                                                    type, String
                                                                                                    acctNbr, Pageable
                                                                                                    pageable);

    Page<Acct> findByInstOrganizationContainingAndNameContainingAndType(String organization, String name, AcctType
            type, Pageable pageable);

    Page<Acct> findByInstOrganizationContainingAndNameContainingAndAcctNbrContaining(String organization, String
            name, String acctNbr, Pageable pageable);

    Page<Acct> findByInstOrganizationContainingAndNameContaining(String organization, String name, Pageable pageable);

    Page<Acct> findByInstOrganizationContainingAndTypeAndAcctNbrContaining(String organization, AcctType type, String
            acctNbr, Pageable pageable);

    Page<Acct> findByInstOrganizationContainingAndType(String organization, AcctType type, Pageable pageable);

    Page<Acct> findByInstOrganizationContainingAndAcctNbrContaining(String organization, String acctNbr, Pageable
            pageable);

    Page<Acct> findByInstOrganizationContaining(String organization, Pageable pageable);

    Page<Acct> findByNameContainingAndTypeAndAcctNbrContaining(String name, AcctType type, String acctNbr, Pageable
            pageable);

    Page<Acct> findByNameContainingAndType(String name, AcctType type, Pageable pageable);

    Page<Acct> findByNameContainingAndAcctNbrContaining(String name, String acctNbr, Pageable pageable);

    Page<Acct> findByNameContaining(String name, Pageable pageable);

    Page<Acct> findByTypeAndAcctNbrContaining(AcctType type, String acctNbr, Pageable pageable);

    Page<Acct> findByType(AcctType type, Pageable pageable);

    Page<Acct> findByAcctNbrContaining(String acctNbr, Pageable pageable);

    List<Acct> findByOfxAcctIdAndInstId(String ofxAcctId, Long instId);
}