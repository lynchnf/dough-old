package norman.dough.domain.repository;

import norman.dough.domain.Acct;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AcctRepository extends PagingAndSortingRepository<Acct, Long> {
}
