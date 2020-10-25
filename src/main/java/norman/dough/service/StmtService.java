package norman.dough.service;

import norman.dough.domain.Stmt;
import norman.dough.domain.repository.StmtRepository;
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
public class StmtService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StmtService.class);
    @Autowired
    private StmtRepository repository;

    public Iterable<Stmt> findAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "closeDate", "id");
        return repository.findAll(sort);
    }

    public Page<Stmt> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Stmt findById(Long id) throws NotFoundException {
        Optional<Stmt> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new NotFoundException(LOGGER, "Statement", id);
        }
        return optional.get();
    }

    public Stmt save(Stmt entity) throws OptimisticLockingException {
        try {
            return repository.save(entity);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new OptimisticLockingException(LOGGER, "Statement", entity.getId(), e);
        }
    }
}
