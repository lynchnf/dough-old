package name.mutant.dough.service;

import name.mutant.dough.DoughException;
import name.mutant.dough.domain.Payee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class PayeeService extends BaseService {
    private static final Logger LOG = LogManager.getLogger();

    private PayeeService() {
    }

    public static Payee readPayee(Long id) throws DoughException {
        DaoFunction<Payee> function = new DaoFunction<Payee>() {
            public Payee doFunction(EntityManager entityManager) throws Exception {
                return entityManager.find(Payee.class, id);
            }

            public String getErrorMessage() {
                return "Error reading payee id=\"" + id + "\".";
            }
        };
        return doWithEntityManager(function);
    }

    public static List<String> validateSavePayee(Payee payee) {
        List<String> errors = new ArrayList<>();

        // TODO Business validation rules here.

        return errors;
    }

    public static Payee savePayee(Payee payee) throws DoughException {
        DaoFunction<Payee> function = new DaoFunction<Payee>() {
            public Payee doFunction(EntityManager entityManager) throws Exception {
                return entityManager.merge(payee);
            }

            public String getErrorMessage() {
                return "Error saving payee=\"" + payee + "\".";
            }
        };
        return doInTransaction(function);
    }
}
