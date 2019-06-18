package name.mutant.dough.repository;

import name.mutant.dough.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}