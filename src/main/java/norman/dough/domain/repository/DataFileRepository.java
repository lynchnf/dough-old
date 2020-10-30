package norman.dough.domain.repository;

import norman.dough.domain.DataFile;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DataFileRepository extends PagingAndSortingRepository<DataFile, Long> {
}
