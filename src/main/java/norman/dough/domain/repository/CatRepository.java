package norman.dough.domain.repository;

import norman.dough.domain.Cat;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CatRepository extends PagingAndSortingRepository<Cat, Long> {
}
