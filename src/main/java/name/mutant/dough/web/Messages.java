package name.mutant.dough.web;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lynchnf on 7/14/17.
 */
public class Messages {
    private List<String> successMessages = new ArrayList<>();
    private List<String> errorMessages = new ArrayList<>();

    public List<String> getSuccessMessages() {
        return successMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}