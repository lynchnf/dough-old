package name.mutant.dough.servlet;

import name.mutant.dough.DoughException;
import name.mutant.dough.service.UtilService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AcctUpload extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LogManager.getLogger();
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
    private static final DateFormat FILE_NAME_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> errors = (List<String>) req.getAttribute("errors");
        if (errors == null) errors = new ArrayList<>();
        List<String> messages = (List<String>) req.getAttribute("messages");
        if (messages == null) messages = new ArrayList<>();

        if (!ServletFileUpload.isMultipartContent(req)) {
            String msg = "Form must be enctype=multipart/form-data.";
            LOG.error(msg);
            errors.add(msg);
        } else {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            String javaIoTmpdir = System.getProperty("java.io.tmpdir");
            LOG.debug("javaIoTmpdir=\"" + javaIoTmpdir + "\"");
            factory.setRepository(new File(javaIoTmpdir));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(MAX_FILE_SIZE);
            upload.setSizeMax(MAX_REQUEST_SIZE);

            try {
                List<FileItem> formItems = upload.parseRequest(req);
                if (formItems != null && formItems.size() > 0) {
                    long fileNumber = System.currentTimeMillis();
                    String fileNamePrefix = FILE_NAME_DATE_FORMAT.format(new Date());
                    File importDir = UtilService.getImportDir();
                    for (FileItem item : formItems) {

                        // Processes only fields that are not form fields.
                        if (!item.isFormField()) {
                            String fileName = fileNamePrefix + "-" + fileNumber++ + ".ofx";
                            File importFile = new File(importDir, fileName);
                            LOG.debug("importFile=\"" + importFile + "\"");
                            item.write(importFile);
                            messages.add("File " + item.getName() + " has been successfully uploaded.");
                        }
                    }
                }
            } catch (Exception e) {
                if (e instanceof DoughException) {
                    errors.add(StringEscapeUtils.escapeHtml4(e.getMessage()));
                } else {
                    String msg = "Error uploading file(s).";
                    LOG.error(msg, e);
                    errors.add(StringEscapeUtils.escapeHtml4(msg));
                }
            }
        }

        // Go to next page.
        req.setAttribute("errors", errors);
        req.setAttribute("messages", messages);
        getServletContext().getRequestDispatcher("/AcctUploadProcess").forward(req, resp);
    }
}
