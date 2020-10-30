package norman.dough.domain.repository;

import norman.dough.domain.Pattern;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PatternRepository extends PagingAndSortingRepository<Pattern, Long> {
}
