package name.mutant.dough.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by lynchnf on 7/14/17.
 */
public interface OfxFileRepository extends PagingAndSortingRepository<OfxFile, Long> {
    Page<OfxFile> findByOriginalFilenameContaining(String string, Pageable pageable);
}