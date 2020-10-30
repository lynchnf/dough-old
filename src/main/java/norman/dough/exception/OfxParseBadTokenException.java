package norman.dough.exception;

import norman.dough.service.OfxParseState;
import org.slf4j.Logger;

public class OfxParseBadTokenException extends OfxParseException {
    private static final String PARSE_BAD_TOKEN_ERROR = "Invalid token found: state=%s, line=%s.";
    private OfxParseState state;
    private String line;

    public OfxParseBadTokenException(Logger logger, OfxParseState state, String line) {
        super(logger, String.format(PARSE_BAD_TOKEN_ERROR, state, line));
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
