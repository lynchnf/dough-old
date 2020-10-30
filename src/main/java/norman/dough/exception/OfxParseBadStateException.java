package norman.dough.exception;

import norman.dough.service.OfxParseState;
import org.slf4j.Logger;

public class OfxParseBadStateException extends OfxParseException {
    private static final String PARSE_BAD_STATE_ERROR = "Invalid state=%s.";
    private OfxParseState state;

    public OfxParseBadStateException(Logger logger, OfxParseState state) {
        super(logger, String.format(PARSE_BAD_STATE_ERROR, state));
        this.state = state;
    }

    public OfxParseState getState() {
        return state;
    }
}
