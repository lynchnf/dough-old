package norman.dough.service;

import norman.dough.domain.AcctNbr;
import norman.dough.domain.repository.AcctNbrRepository;
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
public class AcctNbrService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AcctNbrService.class);
    @Autowired
    private AcctNbrRepository repository;

    public Iterable<AcctNbr> findAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "number", "id");
        return repository.findAll(sort);
    }

    public Page<AcctNbr> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public AcctNbr findById(Long id) throws NotFoundException {
        Optional<AcctNbr> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new NotFoundException(LOGGER, "Account number", id);
        }
        return optional.get();
    }

    public AcctNbr save(AcctNbr entity) throws OptimisticLockingException {
        try {
            return repository.save(entity);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new OptimisticLockingException(LOGGER, "Account number", entity.getId(), e);
        }
    }
}
