package name.mutant.dough.service;

import name.mutant.dough.DoughNotFoundException;
import name.mutant.dough.DoughOptimisticLockingException;
import name.mutant.dough.domain.Payee;
import name.mutant.dough.repository.PayeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PayeeService {
    @Autowired
    private PayeeRepository payeeRepository;

    public Iterable<Payee> findAllPayees() {
        return payeeRepository.findAll();
    }

    public Payee findPayeeById(Long payeeId) throws DoughNotFoundException {
        Optional<Payee> optional = payeeRepository.findById(payeeId);
        if (!optional.isPresent()) {
            throw new DoughNotFoundException("Payee not found, payeeId=\"" + payeeId + "\"");
        }
        return optional.get();
    }

    public Payee savePayee(Payee payee) throws DoughOptimisticLockingException {
        try {
            return payeeRepository.save(payee);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new DoughOptimisticLockingException(
                    "Optimistic locking failure while saving payee, payeeId=\"" + payee.getId() + "\"", e);
        }
    }
}