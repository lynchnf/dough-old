package name.mutant.dough;

public class DoughInconceivableException extends RuntimeException {
    public DoughInconceivableException(String message) {
        super(message);
    }

    public DoughInconceivableException(String message, Throwable throwable) {
        super(message, throwable);
    }
}