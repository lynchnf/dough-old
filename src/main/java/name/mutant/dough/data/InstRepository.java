package name.mutant.dough.data;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by lynchnf on 7/14/17.
 */
public interface InstRepository extends PagingAndSortingRepository<Inst, Long> {
    List<Inst> findByFid(String fid);
}