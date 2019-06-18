package name.mutant.dough.service;

import name.mutant.dough.DoughNotFoundException;
import name.mutant.dough.DoughOptimisticLockingException;
import name.mutant.dough.domain.Tran;
import name.mutant.dough.repository.TranRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TranService {
    @Autowired
    TranRepository tranRepository;

    public Tran findTranById(Long tranId) throws DoughNotFoundException {
        Optional<Tran> optional = tranRepository.findById(tranId);
        if (!optional.isPresent()) {
            throw new DoughNotFoundException("Tran not found, tranId=\"" + tranId + "\"");
        }
        return optional.get();
    }

    public Iterable<Tran> saveAllTrans(Iterable<Tran> trans) throws DoughOptimisticLockingException {
        try {
            return tranRepository.saveAll(trans);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new DoughOptimisticLockingException("Optimistic locking failure while saving trans", e);
        }
    }

    public List<Tran> findAllNonAssigned() {
        return tranRepository.findByCategoryIsNull();
    }
}