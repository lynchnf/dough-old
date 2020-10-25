package norman.dough.domain.repository;

import norman.dough.domain.Tran;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TranRepository extends PagingAndSortingRepository<Tran, Long> {
}
