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
    private static DateFormat dateFormat;

    private UtilService() {
    }

    public static DateFormat getDateFormat() {
        if (dateFormat == null) {
            ResourceBundle appBundle = ResourceBundle.getBundle("application");
            String dateFormatPattern = null;
            try {
                dateFormatPattern = appBundle.getString("date.format.pattern");
            } catch (MissingResourceException e) {
                LOG.warn("Application property date.format.pattern is missing.");
                dateFormatPattern = DEFAULT_DATE_FORMAT_PATTERN;
            }
            try {
                dateFormat = new SimpleDateFormat(dateFormatPattern);
            } catch (IllegalArgumentException e) {
                LOG.warn("Application property date.format.pattern is invalid.");
                dateFormat = new SimpleDateFormat("DEFAULT_DATE_FORMAT_PATTERN");
            }

        }
        return dateFormat;
    }
}
