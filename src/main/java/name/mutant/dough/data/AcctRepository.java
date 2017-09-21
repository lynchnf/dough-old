package name.mutant.dough.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by lynchnf on 7/14/17.
 */
public interface AcctRepository extends PagingAndSortingRepository<Acct, Long> {
    Page<Acct> findByNameContaining(String string, Pageable pageable);

    List<Acct> findByOfxAcctIdAndInstId(String ofxAcctId, Long instId);
}