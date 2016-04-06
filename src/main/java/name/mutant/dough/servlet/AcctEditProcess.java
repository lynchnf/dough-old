package name.mutant.dough.servlet;

import name.mutant.dough.DoughException;
import name.mutant.dough.domain.Acct;
import name.mutant.dough.domain.AcctType;
import name.mutant.dough.service.AcctService;
import name.mutant.dough.service.UtilService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class AcctEditProcess extends HttpServlet {
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

        String idStr = StringUtils.trimToNull(req.getParameter("id"));
        String versionStr = StringUtils.trimToNull(req.getParameter("version"));
        String acctNbr = StringUtils.trimToNull(req.getParameter("acctNbr"));
        String name = StringUtils.trimToNull(req.getParameter("name"));
        String organization = StringUtils.trimToNull(req.getParameter("organization"));
        String fid = StringUtils.trimToNull(req.getParameter("fid"));
        String ofxBankId = StringUtils.trimToNull(req.getParameter("ofxBankId"));
        String ofxAcctId = StringUtils.trimToNull(req.getParameter("ofxAcctId"));
        String typeStr = StringUtils.trimToNull(req.getParameter("type"));
        String beginDateStr = StringUtils.trimToNull(req.getParameter("beginDate"));
        String beginBalanceStr = StringUtils.trimToNull(req.getParameter("beginBalance"));

        Acct acct = new Acct();

        if (idStr != null) {
            acct.setId(Long.valueOf(idStr));
            acct.setVersion(Integer.valueOf(versionStr));
        }
        if (acctNbr == null) {
            errors.add("Acct Nbr is required.");
        } else {
            acct.setAcctNbr(acctNbr);
        }
        if (name == null) {
            errors.add("Name is required.");
        } else {
            acct.setName(name);
        }
        acct.setOrganization(organization);

        // FID is required (for now).
        if (fid == null) {
            errors.add("FID is required.");
        } else {
            acct.setFid(fid);
        }
        acct.setOfxBankId(ofxBankId);

        // OFX Acct ID is required (for now).
        if (ofxAcctId == null) {
            errors.add("OFX Acct ID is required.");
        } else {
            acct.setOfxAcctId(ofxAcctId);
        }
        if (typeStr == null) {
            errors.add("Type is required.");
        } else {
            LOG.debug("typeStr=\"" + typeStr + "\"");
            acct.setType(AcctType.valueOf(typeStr));
        }
        if (beginBalanceStr == null) {
            errors.add("Begin Date is required.");
        } else {
            DateFormat dateFormat = UtilService.getDateFormat();
            try {
                acct.setBeginDate(dateFormat.parse(beginDateStr));
            } catch (ParseException e) {
                errors.add("Begin Date is not a valid date in " + UtilService.getDateFormatPattern() + " format.");
            }
        }
        if (beginBalanceStr == null) {
            errors.add("Begin Balance is required.");
        } else {
            String symbol = Currency.getInstance(Locale.getDefault()).getSymbol();
            if (beginBalanceStr.startsWith(symbol) && beginBalanceStr.length() > 1)
                beginBalanceStr = beginBalanceStr.substring(1);
            NumberFormat numberFormat = NumberFormat.getInstance();
            try {
                Number number = numberFormat.parse(beginBalanceStr);
                acct.setBeginBalance(BigDecimal.valueOf(number.doubleValue()).setScale(2, RoundingMode.HALF_UP));
            } catch (ParseException e) {
                errors.add("Begin Balance is not a valid amount.");
            }
        }

        if (errors.isEmpty()) {
            errors.addAll(AcctService.validateSaveAcct(acct));
        }
        if (errors.isEmpty()) {
            try {
                AcctService.saveAcct(acct);
                messages.add("Acct successfully saved.");
            } catch (DoughException e) {
                errors.add(e.getMessage());
            }
        }

        // Go to next page.
        req.setAttribute("errors", errors);
        req.setAttribute("messages", messages);
        if (errors.isEmpty()) {
            // TODO Put success message in "flash" somehow.
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/DashboardLoad"));
        } else {
            req.setAttribute("id", StringUtils.trimToEmpty(idStr));
            req.setAttribute("version", StringUtils.trimToEmpty(versionStr));
            req.setAttribute("acctNbr", StringUtils.trimToEmpty(acctNbr));
            req.setAttribute("name", StringUtils.trimToEmpty(name));
            req.setAttribute("organization", StringUtils.trimToEmpty(organization));
            req.setAttribute("fid", StringUtils.trimToEmpty(fid));
            req.setAttribute("ofxBankId", StringUtils.trimToEmpty(ofxBankId));
            req.setAttribute("ofxAcctId", StringUtils.trimToEmpty(ofxAcctId));
            req.setAttribute("type", StringUtils.trimToEmpty(typeStr));
            req.setAttribute("beginDate", StringUtils.trimToEmpty(beginDateStr));
            req.setAttribute("beginBalance", StringUtils.trimToEmpty(beginBalanceStr));

            getServletContext().getRequestDispatcher("/AcctEdit.jsp").forward(req, resp);
        }
    }
}
