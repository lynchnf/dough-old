package norman.dough.domain.repository;

import norman.dough.domain.Stmt;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StmtRepository extends PagingAndSortingRepository<Stmt, Long> {
}
