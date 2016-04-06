package name.mutant.dough.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class UtilService {
    private static final Logger LOG = LogManager.getLogger();
    private static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private static String dateFormatPattern;
    private static DateFormat dateFormat;

    private UtilService() {
    }

    public static String getDateFormatPattern() {
        if (dateFormatPattern == null) {
            ResourceBundle appBundle = ResourceBundle.getBundle("application");
            try {
                dateFormatPattern = appBundle.getString("date.format.pattern");
            } catch (MissingResourceException e) {
                LOG.warn("Application property date.format.pattern is missing.");
                dateFormatPattern = DEFAULT_DATE_FORMAT_PATTERN;
            }
        }
        return dateFormatPattern;
    }

    public static DateFormat getDateFormat() {
        if (dateFormat == null) {
            try {
                dateFormat = new SimpleDateFormat(getDateFormatPattern());
            } catch (IllegalArgumentException e) {
                LOG.warn("Application property date.format.pattern is invalid.");
                dateFormatPattern = DEFAULT_DATE_FORMAT_PATTERN;
                dateFormat = new SimpleDateFormat("DEFAULT_DATE_FORMAT_PATTERN");
            }
        }
        return dateFormat;
    }
}
