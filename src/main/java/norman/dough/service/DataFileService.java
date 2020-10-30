package norman.dough.service;

import norman.dough.domain.DataFile;
import norman.dough.domain.repository.DataFileRepository;
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
public class DataFileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataFileService.class);
    @Autowired
    private DataFileRepository repository;

    public Iterable<DataFile> findAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "originalFilename", "id");
        return repository.findAll(sort);
    }

    public Page<DataFile> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public DataFile findById(Long id) throws NotFoundException {
        Optional<DataFile> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new NotFoundException(LOGGER, "Data file", id);
        }
        return optional.get();
    }

    public DataFile save(DataFile entity) throws OptimisticLockingException {
        try {
            return repository.save(entity);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new OptimisticLockingException(LOGGER, "Data file", entity.getId(), e);
        }
    }
}
