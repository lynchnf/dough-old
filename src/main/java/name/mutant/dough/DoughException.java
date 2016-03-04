package name.mutant.dough;

public class DoughException extends Exception {
    public DoughException(String message) {
        super(message);
    }

    public DoughException(String message, Throwable cause) {
        super(message, cause);
    }
}
