package norman.dough.service;

import norman.dough.domain.Pattern;
import norman.dough.domain.repository.PatternRepository;
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
public class PatternService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatternService.class);
    @Autowired
    private PatternRepository repository;

    public Iterable<Pattern> findAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "seq", "id");
        return repository.findAll(sort);
    }

    public Page<Pattern> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Pattern findById(Long id) throws NotFoundException {
        Optional<Pattern> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new NotFoundException(LOGGER, "Pattern", id);
        }
        return optional.get();
    }

    public Pattern save(Pattern entity) throws OptimisticLockingException {
        try {
            return repository.save(entity);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new OptimisticLockingException(LOGGER, "Pattern", entity.getId(), e);
        }
    }
}
