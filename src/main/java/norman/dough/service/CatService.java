package norman.dough.service;

import norman.dough.domain.Cat;
import norman.dough.domain.repository.CatRepository;
import norman.dough.exception.NotFoundException;
import norman.dough.exception.OptimisticLockingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CatService.class);
    @Autowired
    private CatRepository repository;

    public Iterable<Cat> findAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "name", "id");
        return repository.findAll(sort);
    }

    public Page<Cat> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Cat findById(Long id) throws NotFoundException {
        Optional<Cat> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new NotFoundException(LOGGER, "Category", id);
        }
        return optional.get();
    }

    public Cat save(Cat entity) throws OptimisticLockingException {
        try {
            return repository.save(entity);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new OptimisticLockingException(LOGGER, "Category", entity.getId(), e);
        }
    }
}
