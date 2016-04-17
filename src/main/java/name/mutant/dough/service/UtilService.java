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
    private static String importDir;
    private static String processedDir;

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

    public static String getImportDir() {
        if (importDir == null) {
            ResourceBundle appBundle = ResourceBundle.getBundle("application");
            importDir = appBundle.getString("import.dir");
        }
        return importDir;
    }

    public static String getProcessedDir() {
        if (processedDir == null) {
            ResourceBundle appBundle = ResourceBundle.getBundle("application");
            processedDir = appBundle.getString("processed.dir");
        }
        return processedDir;
    }
}
