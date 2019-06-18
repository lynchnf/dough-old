package name.mutant.dough.repository;

import name.mutant.dough.domain.Acct;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AcctRepository extends CrudRepository<Acct, Long> {
    List<Acct> findByFid(String fid);
}