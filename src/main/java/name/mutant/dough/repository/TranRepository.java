package name.mutant.dough.repository;

import name.mutant.dough.domain.Tran;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TranRepository extends CrudRepository<Tran, Long> {
    List<Tran> findByAcct_IdAndFitId(Long acctId, String fitId);

    List<Tran> findByCategoryIsNull();
}