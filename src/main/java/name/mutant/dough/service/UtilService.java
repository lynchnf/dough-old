package name.mutant.dough.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileFilter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.io.File;
import java.net.URL;

public class UtilService {
    private static final Logger LOG = LogManager.getLogger();
    private static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private static final String IMPORT_DIR = "fileupload/import/";
    private static final String PROCESSED_DIR = "fileupload/processed/";
    private static final String MARKER_FILE = "do-not-delete.txt";
    private static String dateFormatPattern;
    private static DateFormat dateFormat;
    private static File importDir;
    private static File processedDir;

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

    public static File getImportDir() {
        if (importDir == null) {
            importDir = getParentDirFromMarkerFile(IMPORT_DIR + MARKER_FILE);
        }
        return importDir;
    }

    public static File[] getImportFiles() {
        return getImportDir().listFiles(file -> !MARKER_FILE.equals(file.getName()));
    }

    public static File getProcessedDir() {
        if (processedDir == null) {
            processedDir = getParentDirFromMarkerFile(PROCESSED_DIR + MARKER_FILE);
        }
        return processedDir;
    }

    private static File getParentDirFromMarkerFile(String markerFilePath) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(markerFilePath);
        File file = new File(url.getPath());
        return file.getParentFile();
    }
}
