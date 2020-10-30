package norman.dough.exception;

import norman.dough.service.OfxParseState;
import org.slf4j.Logger;

public class OfxParseMissingTokenException extends OfxParseException {
    private static final String PARSE_MISSING_TOKEN_ERROR = "No valid token found: state=%s, line=%s.";
    private OfxParseState state;
    private String line;

    public OfxParseMissingTokenException(Logger logger, OfxParseState state, String line) {
        super(logger, String.format(PARSE_MISSING_TOKEN_ERROR, state, line));
        this.state = state;
        this.line = line;
    }

    public OfxParseState getState() {
        return state;
    }

    public String getLine() {
        return line;
    }
}
