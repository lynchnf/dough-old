package name.mutant.dough.servlet;

import name.mutant.dough.DoughException;
import name.mutant.dough.service.AcctService;
import name.mutant.dough.service.PayableService;
import name.mutant.dough.service.dto.AcctBalance;
import name.mutant.dough.service.dto.BillToPay;
import name.mutant.dough.service.filter.request.PayableFilterRequest;
import name.mutant.dough.service.filter.request.PayableOrderByField;
import name.mutant.dough.service.filter.response.PayableFilterResponse;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardLoad extends HttpServlet {
    private static final long serialVersionUID = 1L;

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

        List<BillToPay> billsToPay = new ArrayList<>();
        try {
            billsToPay.addAll(PayableService.getBillsToPay(new Date()));
        } catch (DoughException e) {
            errors.add(StringEscapeUtils.escapeHtml4(e.getMessage()));
        }
        req.setAttribute("billsToPay", billsToPay);

        List<AcctBalance> acctBalances = new ArrayList<>();
        try {
            acctBalances.addAll(AcctService.getAcctBalances());
        } catch (DoughException e) {
            errors.add(StringEscapeUtils.escapeHtml4(e.getMessage()));
        }
        req.setAttribute("acctBalances", acctBalances);

        // Go to next page.
        req.setAttribute("errors", errors);
        req.setAttribute("messages", messages);
        getServletContext().getRequestDispatcher("/Dashboard.jsp").forward(req, resp);
    }
}
