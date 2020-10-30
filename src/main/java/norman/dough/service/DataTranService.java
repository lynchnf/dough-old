package norman.dough.service;

import norman.dough.domain.DataTran;
import norman.dough.domain.repository.DataTranRepository;
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
public class DataTranService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataTranService.class);
    @Autowired
    private DataTranRepository repository;

    public Iterable<DataTran> findAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "ofxPostDate", "id");
        return repository.findAll(sort);
    }

    public Page<DataTran> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public DataTran findById(Long id) throws NotFoundException {
        Optional<DataTran> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new NotFoundException(LOGGER, "Data transaction", id);
        }
        return optional.get();
    }

    public DataTran save(DataTran entity) throws OptimisticLockingException {
        try {
            return repository.save(entity);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new OptimisticLockingException(LOGGER, "Data transaction", entity.getId(), e);
        }
    }
}
