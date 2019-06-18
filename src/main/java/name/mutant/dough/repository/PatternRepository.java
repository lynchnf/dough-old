package name.mutant.dough.repository;

import name.mutant.dough.domain.Pattern;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatternRepository extends CrudRepository<Pattern, Long> {
    List<Pattern> findAllByOrderBySeq();
}