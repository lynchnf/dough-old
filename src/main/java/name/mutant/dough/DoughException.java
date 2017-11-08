package name.mutant.dough;

/**
 * Created by lynchnf on 11/8/17.
 */
public class DoughException extends Exception {
    public DoughException(String message) {
        super(message);
    }

    public DoughException(String message, Throwable cause) {
        super(message, cause);
    }
}