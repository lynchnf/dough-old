package name.mutant.dough.service;

import name.mutant.dough.DoughNotFoundException;
import name.mutant.dough.DoughOptimisticLockingException;
import name.mutant.dough.FakeDataUtil;
import name.mutant.dough.domain.Payee;
import name.mutant.dough.repository.PayeeRepository;
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

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class PayeeServiceTest {
    private Long payee1Id;
    private Long payee2Id;
    private Payee payee1;
    private String payee1Name;

    @TestConfiguration
    static class PayeeServiceTestConfiguration {
        @Bean
        public PayeeService payeeService() {
            return new PayeeService();
        }
    }

    @Autowired
    private PayeeService payeeService;
    @MockBean
    private PayeeRepository payeeRepository;

    @Before
    public void setUp() throws Exception {
        payee1Id = Long.valueOf(1);
        payee2Id = Long.valueOf(2);
        payee1 = FakeDataUtil.buildPayee(payee1Id);
        payee1Name = payee1.getName();
    }

    @Test
    public void findAllPayees() {
        Mockito.when(payeeRepository.findAll()).thenReturn(new ArrayList<>());
        Iterable<Payee> payees = payeeService.findAllPayees();
        assertFalse(payees.iterator().hasNext());
    }

    @Test
    public void findPayeeById() throws Exception {
        Mockito.when(payeeRepository.findById(payee1Id)).thenReturn(Optional.of(payee1));
        Payee payee = payeeService.findPayeeById(payee1Id);
        assertEquals(payee1Name, payee.getName());
    }

    @Test
    public void findPayeeByIdNotFound() {
        Mockito.when(payeeRepository.findById(payee2Id)).thenReturn(Optional.empty());
        try {
            Payee payee = payeeService.findPayeeById(payee2Id);
            fail();
        } catch (DoughNotFoundException e) {
        }
    }

    @Test
    public void savePayee() throws Exception {
        Mockito.when(payeeRepository.save(Mockito.any(Payee.class))).thenReturn(payee1);
        Payee payee = payeeService.savePayee(payee1);
        assertEquals(payee1Name, payee.getName());
    }

    @Test
    public void savePayeeOptimisticLocking() {
        Mockito.when(payeeRepository.save(Mockito.any(Payee.class)))
                .thenThrow(ObjectOptimisticLockingFailureException.class);
        try {
            Payee payee = payeeService.savePayee(payee1);
            fail();
        } catch (DoughOptimisticLockingException e) {
        }
    }
}