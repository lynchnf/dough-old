package name.mutant.dough;

public class DoughOfxParseException extends DoughException {
    public DoughOfxParseException(String message) {
        super(message);
    }

    public DoughOfxParseException(String message, Throwable throwable) {
        super(message, throwable);
    }
}