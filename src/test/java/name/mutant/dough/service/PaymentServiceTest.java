package name.mutant.dough.service;

import name.mutant.dough.DoughNotFoundException;
import name.mutant.dough.DoughOptimisticLockingException;
import name.mutant.dough.FakeDataUtil;
import name.mutant.dough.domain.Payable;
import name.mutant.dough.domain.Payee;
import name.mutant.dough.domain.Payment;
import name.mutant.dough.repository.PaymentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class PaymentServiceTest {
    private Long payment1Id;
    private Long payment2Id;
    private Payee payee1;
    private Payable payable1;
    private Payment payment1;
    private BigDecimal payment1AmountPaid;

    @TestConfiguration
    static class PaymentServiceTestConfiguration {
        @Bean
        public PaymentService paymentService() {
            return new PaymentService();
        }
    }

    @Autowired
    private PaymentService paymentService;
    @MockBean
    private PaymentRepository paymentRepository;

    @Before
    public void setUp() throws Exception {
        payment1Id = Long.valueOf(1);
        payment2Id = Long.valueOf(2);
        payee1 = FakeDataUtil.buildPayee(3);
        payable1 = FakeDataUtil.buildPayable(payee1, 4, null);
        payment1 = FakeDataUtil.buildPartialPayment(payable1, payment1Id);
        payment1AmountPaid = payment1.getAmountPaid();
    }

    @Test
    public void findAllPayments() {
        Mockito.when(paymentRepository.findAll()).thenReturn(new ArrayList<>());
        Iterable<Payment> payments = paymentService.findAllPayments();
        assertFalse(payments.iterator().hasNext());
    }

    @Test
    public void findPaymentById() throws Exception {
        Mockito.when(paymentRepository.findById(payment1Id)).thenReturn(Optional.of(payment1));
        Payment payment = paymentService.findPaymentById(payment1Id);
        assertEquals(0, payment1AmountPaid.compareTo(payment.getAmountPaid()));
    }

    @Test
    public void findPaymentByIdNotFound() {
        Mockito.when(paymentRepository.findById(payment2Id)).thenReturn(Optional.empty());
        try {
            Payment payment = paymentService.findPaymentById(payment2Id);
            fail();
        } catch (DoughNotFoundException e) {
        }
    }

    @Test
    public void savePayment() throws Exception {
        Mockito.when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(payment1);
        Payment payment = paymentService.savePayment(payment1);
        assertEquals(0, payment1AmountPaid.compareTo(payment.getAmountPaid()));
    }

    @Test
    public void savePaymentOptimisticLocking() {
        Mockito.when(paymentRepository.save(Mockito.any(Payment.class)))
                .thenThrow(ObjectOptimisticLockingFailureException.class);
        try {
            Payment payment = paymentService.savePayment(payment1);
            fail();
        } catch (DoughOptimisticLockingException e) {
        }
    }
}