package name.mutant.dough.service;

import name.mutant.dough.DoughNotFoundException;
import name.mutant.dough.DoughOptimisticLockingException;
import name.mutant.dough.domain.Category;
import name.mutant.dough.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Iterable<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findCategoryById(Long categoryId) throws DoughNotFoundException {
        Optional<Category> optional = categoryRepository.findById(categoryId);
        if (!optional.isPresent()) {
            throw new DoughNotFoundException("Category not found, categoryId=\"" + categoryId + "\"");
        }
        return optional.get();
    }

    public Category saveCategory(Category category) throws DoughOptimisticLockingException {
        try {
            return categoryRepository.save(category);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new DoughOptimisticLockingException(
                    "Optimistic locking failure while saving category, categoryId=\"" + category.getId() + "\"", e);
        }
    }
}