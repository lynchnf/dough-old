package name.mutant.dough.repository;

import name.mutant.dough.domain.Payable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PayableRepository extends CrudRepository<Payable, Long> {
    List<Payable> findAllByOrderByDueDate();
}