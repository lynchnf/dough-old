package name.mutant.dough.service;

import name.mutant.dough.DoughException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ResourceBundle;

/**
 * This abstract class extends the service classes so that their methods can do data access stuff without <em>you</em>
 * having to worry about all that tedious boilerplate code.
 * <p>
 * Typically, every time you write a data access method you have a bunch of boilerplate code at the beginning to create
 * an {@code EntityManager} and begin an {@code EntityTransaction}. Then you do the stuff you're really interested in.
 * Finally you have more boilerplate code to commit your stuff, catch and re-throw any exception that your stuff might
 * have thrown, rollback (if there was an exception), and (in a finally block) close your {@code EntityManager}. If you
 * want to detect and join a {@code EntityTransaction} that's already active, your boilerplate code gets even more
 * complicated.
 * <p>
 * You <em>could</em> copy your boilerplate code into each data access method, but that would be a <em>Bad Thing</em>
 * &trade;. This abstract class allows you to code your stuff in a function object which you pass into the
 * {@code doInTransaction()} or {@code doWithEntityManager()} methods. It then executes the beginning boilerplate code,
 * your function, and finally the ending boilerplate code.
 *
 * @author LYNCHNF
 * @see DaoFunction
 */
public abstract class BaseService {
    private static final Logger LOG = LogManager.getLogger();
    private static ResourceBundle appBundle = ResourceBundle.getBundle("application");
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(appBundle.getString("persistence.unit.name"));
    private static ThreadLocal<EntityManager> currentEntityManager = new ThreadLocal<>();

    protected static <T> T doWithEntityManager(DaoFunction<T> function) throws DoughException {
        boolean entityManagerCreated = false;
        EntityManager entityManager = currentEntityManager.get();
        try {
            if (entityManager == null) {
                entityManager = entityManagerFactory.createEntityManager();
                currentEntityManager.set(entityManager);
                entityManagerCreated = true;
            }
            return function.doFunction(entityManager);
        } catch (Exception e) {
            if (e instanceof DoughException) {
                throw (DoughException) e;
            } else {
                LOG.error(function.getErrorMessage(), e);
                throw new DoughException(function.getErrorMessage(), e);
            }
        } finally {
            if (entityManagerCreated) {
                try {
                    currentEntityManager.remove();
                    entityManager.close();
                } catch (Exception ignored) {
                    LOG.warn("Ignoring error while closing entityManager.", ignored);
                }
            }
        }
    }

    protected static <T> T doInTransaction(final DaoFunction<T> function) throws DoughException {
        DaoFunction<T> function2 = new DaoFunction<T>() {
            public T doFunction(EntityManager entityManager) throws Exception {
                boolean transactionBegun = false;
                EntityTransaction transaction = entityManager.getTransaction();
                try {
                    if (!transaction.isActive()) {
                        transaction.begin();
                        transactionBegun = true;
                    }
                    T result = function.doFunction(entityManager);
                    if (transactionBegun) {
                        transaction.commit();
                    }
                    return result;
                } catch (Exception e) {
                    if (transaction.isActive()) {
                        transaction.rollback();
                    }
                    if (e instanceof DoughException) {
                        throw e;
                    } else {
                        LOG.error(function.getErrorMessage(), e);
                        throw new DoughException(function.getErrorMessage(), e);
                    }
                }
            }

            public String getErrorMessage() {
                return function.getErrorMessage();
            }
        };
        return doWithEntityManager(function2);
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
