package norman.dough.exception;

import org.slf4j.Logger;

import java.text.ParseException;

public class OfxParseException extends Exception {
    public static final String PARSE_ERROR = "Parse error: %s in line=%s.";
    private String fieldName;
    private String line;

    public OfxParseException(Logger logger, String message) {
        super(message);
        logger.warn(message);
    }

    public OfxParseException(Logger logger, String fieldName, String line, ParseException e) {
        super(String.format(PARSE_ERROR, fieldName, line), e);
        this.fieldName = fieldName;
        this.line = line;
        logger.warn(this.getMessage(), e);
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getLine() {
        return line;
    }
}
