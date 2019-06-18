package name.mutant.dough;

public class DoughNotFoundException extends DoughException {
    public DoughNotFoundException(String message) {
        super(message);
    }

    public DoughNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}