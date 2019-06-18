package name.mutant.dough;

public class DoughOptimisticLockingException extends DoughException {
    public DoughOptimisticLockingException(String message) {
        super(message);
    }

    public DoughOptimisticLockingException(String message, Throwable throwable) {
        super(message, throwable);
    }
}