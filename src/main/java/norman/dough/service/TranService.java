package norman.dough.service;

import norman.dough.domain.Tran;
import norman.dough.domain.repository.TranRepository;
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
public class TranService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TranService.class);
    @Autowired
    private TranRepository repository;

    public Iterable<Tran> findAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "postDate", "id");
        return repository.findAll(sort);
    }

    public Page<Tran> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Tran findById(Long id) throws NotFoundException {
        Optional<Tran> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new NotFoundException(LOGGER, "Transaction", id);
        }
        return optional.get();
    }

    public Tran save(Tran entity) throws OptimisticLockingException {
        try {
            return repository.save(entity);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new OptimisticLockingException(LOGGER, "Transaction", entity.getId(), e);
        }
    }
}
