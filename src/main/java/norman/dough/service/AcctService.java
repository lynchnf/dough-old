package norman.dough.service;

import norman.dough.domain.Acct;
import norman.dough.domain.repository.AcctRepository;
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
public class AcctService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AcctService.class);
    @Autowired
    private AcctRepository repository;

    public Iterable<Acct> findAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "name", "id");
        return repository.findAll(sort);
    }

    public Page<Acct> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Acct findById(Long id) throws NotFoundException {
        Optional<Acct> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new NotFoundException(LOGGER, "Account", id);
        }
        return optional.get();
    }

    public Acct save(Acct entity) throws OptimisticLockingException {
        try {
            return repository.save(entity);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new OptimisticLockingException(LOGGER, "Account", entity.getId(), e);
        }
    }
}
