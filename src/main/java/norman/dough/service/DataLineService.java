package norman.dough.service;

import norman.dough.domain.DataLine;
import norman.dough.domain.repository.DataLineRepository;
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
public class DataLineService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataLineService.class);
    @Autowired
    private DataLineRepository repository;

    public Iterable<DataLine> findAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "seq", "id");
        return repository.findAll(sort);
    }

    public Page<DataLine> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public DataLine findById(Long id) throws NotFoundException {
        Optional<DataLine> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new NotFoundException(LOGGER, "Data line", id);
        }
        return optional.get();
    }

    public DataLine save(DataLine entity) throws OptimisticLockingException {
        try {
            return repository.save(entity);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new OptimisticLockingException(LOGGER, "Data line", entity.getId(), e);
        }
    }
}
