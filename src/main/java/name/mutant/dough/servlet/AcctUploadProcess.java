package name.mutant.dough.servlet;

import name.mutant.dough.DoughException;
import name.mutant.dough.domain.Acct;
import name.mutant.dough.domain.Tran;
import name.mutant.dough.service.AcctService;
import name.mutant.dough.service.OfxService;
import name.mutant.dough.service.TranService;
import name.mutant.dough.service.UtilService;
import name.mutant.dough.service.response.OfxParseFileResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AcctUploadProcess extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LogManager.getLogger();

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

        File importDir = new File(UtilService.getImportDir());
        File[] importFiles = importDir.listFiles();
        boolean needToCreateAcct = false;
        OfxParseFileResponse response = null;
        try {
            for (int i = 0; i < importFiles.length && !needToCreateAcct; i++) {
                response = OfxService.parseFile(importFiles[i]);
                String fid = response.getOfxInst().getFid();
                String ofxAcctId = response.getOfxAcct().getAcctId();
                Acct existingAcct = AcctService.readAcctByFidAndOfxAcctId(fid, ofxAcctId);
                if (existingAcct == null) {
                    needToCreateAcct = true;
                } else {
                    List<Tran> trans = OfxService.createTransFromOfx(existingAcct, response.getOfxStmtTrans());
                    List<Tran> transToSave = new ArrayList<>();
                    for (Tran tran : trans) {
                        Long acctId = existingAcct.getId();
                        String fitId = tran.getFitId();
                        Tran existingTran = TranService.readTranByAcctAndFitId(acctId, fitId);
                        if (existingTran == null) {
                            List<String> tranErrors = TranService.validateSaveTran(tran);
                            if (tranErrors.isEmpty()) {
                                transToSave.add(tran);
                            }
                        }
                    }
                    TranService.saveTrans(transToSave);
                    messages.add("" + transToSave.size() + " trans successfully saved.");
                    File processedDir = new File(UtilService.getProcessedDir());
                    if (!processedDir.exists()) processedDir.mkdirs();
                    File processedFile = new File(processedDir, importFiles[i].getName());
                    if (!importFiles[i].renameTo(processedFile)) {
                        String msg = "Error moving file " + importFiles[i] + " to directory " + processedDir + ".";
                        LOG.error(msg);
                        errors.add(msg);
                    }
                }
            }
        } catch (DoughException e) {
            errors.add(e.getMessage());
        }

        // Go to next page.
        req.setAttribute("errors", errors);
        req.setAttribute("messages", messages);
        if (needToCreateAcct) {
            Acct acct = OfxService.createAcctFromOfx(response.getOfxInst(), response.getOfxAcct());
            req.setAttribute("id", StringUtils.EMPTY);
            req.setAttribute("version", StringUtils.EMPTY);
            req.setAttribute("acctNbr", StringUtils.trimToEmpty(acct.getAcctNbr()));
            req.setAttribute("name", StringUtils.trimToEmpty(acct.getName()));
            req.setAttribute("organization", StringUtils.trimToEmpty(acct.getOrganization()));
            req.setAttribute("fid", StringUtils.trimToEmpty(acct.getFid()));
            req.setAttribute("ofxBankId", StringUtils.trimToEmpty(acct.getOfxBankId()));
            req.setAttribute("ofxAcctId", StringUtils.trimToEmpty(acct.getOfxAcctId()));
            req.setAttribute("type", acct.getType().name());
            DateFormat dateFormat = UtilService.getDateFormat();
            req.setAttribute("beginDate", dateFormat.format(new Date()));
            req.setAttribute("beginBalance", BigDecimal.ZERO);
            req.setAttribute("importInProgress", Boolean.TRUE);
            getServletContext().getRequestDispatcher("/AcctEdit.jsp").forward(req, resp);
        } else {
            getServletContext().getRequestDispatcher("/DashboardLoad").forward(req, resp);
        }
    }
}
