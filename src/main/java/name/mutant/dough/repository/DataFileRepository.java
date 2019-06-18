package name.mutant.dough.repository;

import name.mutant.dough.domain.DataFile;
import org.springframework.data.repository.CrudRepository;

public interface DataFileRepository extends CrudRepository<DataFile, Long> {
}