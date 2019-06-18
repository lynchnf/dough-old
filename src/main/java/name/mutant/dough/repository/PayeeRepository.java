package name.mutant.dough.repository;

import name.mutant.dough.domain.Payee;
import org.springframework.data.repository.CrudRepository;

public interface PayeeRepository extends CrudRepository<Payee, Long> {
}