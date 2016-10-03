package name.mutant.dough.servlet;

import name.mutant.dough.DoughException;
import name.mutant.dough.domain.Acct;
import name.mutant.dough.service.AcctService;
import name.mutant.dough.service.UtilService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AcctEditLoad extends HttpServlet {
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

        String acctId = req.getParameter("acctId");
        if (acctId == null) {
            req.setAttribute("id", StringUtils.EMPTY);
            req.setAttribute("version", StringUtils.EMPTY);
            req.setAttribute("acctNbr", StringUtils.EMPTY);
            req.setAttribute("name", StringUtils.EMPTY);
            req.setAttribute("organization", StringUtils.EMPTY);
            req.setAttribute("fid", StringUtils.EMPTY);
            req.setAttribute("ofxBankId", StringUtils.EMPTY);
            req.setAttribute("ofxAcctId", StringUtils.EMPTY);
            req.setAttribute("type", StringUtils.EMPTY);
            DateFormat dateFormat = UtilService.getDateFormat();
            req.setAttribute("beginDate", dateFormat.format(new Date()));
            req.setAttribute("beginBalance", BigDecimal.ZERO);
            req.setAttribute("importInProgress", Boolean.FALSE);
        } else {
            try {
                Acct acct = AcctService.readAcct(Long.parseLong(acctId));
                if (acct == null) {
                    String msg = "Acct not found for acctId=\"" + acctId + "\".";
                    errors.add(msg);
                } else {
                    req.setAttribute("id", acct.getId());
                    req.setAttribute("version", acct.getVersion());
                    req.setAttribute("acctNbr", StringUtils.trimToEmpty(acct.getAcctNbr()));
                    req.setAttribute("name", StringUtils.trimToEmpty(acct.getName()));
                    req.setAttribute("organization", StringUtils.trimToEmpty(acct.getOrganization()));
                    req.setAttribute("fid", StringUtils.trimToEmpty(acct.getFid()));
                    req.setAttribute("ofxBankId", StringUtils.trimToEmpty(acct.getOfxBankId()));
                    req.setAttribute("ofxAcctId", StringUtils.trimToEmpty(acct.getOfxAcctId()));
                    req.setAttribute("type", acct.getType().name());
                    DateFormat dateFormat = UtilService.getDateFormat();
                    req.setAttribute("beginDate", dateFormat.format(acct.getBeginDate()));
                    req.setAttribute("beginBalance", acct.getBeginBalance());
                    req.setAttribute("importInProgress", Boolean.FALSE);
                }
            } catch (DoughException e) {
                errors.add(StringEscapeUtils.escapeHtml4(e.getMessage()));
            }
        }

        // Go to next page.
        req.setAttribute("errors", errors);
        req.setAttribute("messages", messages);
        if (errors.isEmpty()) {
            getServletContext().getRequestDispatcher("/AcctEdit.jsp").forward(req, resp);
        } else {
            getServletContext().getRequestDispatcher("/Dashboard.jsp").forward(req, resp);
        }
    }
}
