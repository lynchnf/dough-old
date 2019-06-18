package name.mutant.dough.service;

import name.mutant.dough.DoughNotFoundException;
import name.mutant.dough.DoughOptimisticLockingException;
import name.mutant.dough.domain.Payment;
import name.mutant.dough.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Iterable<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment findPaymentById(Long paymentId) throws DoughNotFoundException {
        Optional<Payment> optional = paymentRepository.findById(paymentId);
        if (!optional.isPresent()) {
            throw new DoughNotFoundException("Payment not found, paymentId=\"" + paymentId + "\"");
        }
        return optional.get();
    }

    public Payment savePayment(Payment payment) throws DoughOptimisticLockingException {
        try {
            return paymentRepository.save(payment);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new DoughOptimisticLockingException(
                    "Optimistic locking failure while saving payment, paymentId=\"" + payment.getId() + "\"", e);
        }
    }
}