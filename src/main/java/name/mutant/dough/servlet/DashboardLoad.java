package name.mutant.dough.servlet;

import name.mutant.dough.DoughException;
import name.mutant.dough.domain.Payable;
import name.mutant.dough.service.PayableService;
import name.mutant.dough.service.filter.request.PayableFilterRequest;
import name.mutant.dough.service.filter.request.PayableOrderByField;
import name.mutant.dough.service.filter.response.PayableFilterResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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

        PayableFilterRequest payableFilterRequest = new PayableFilterRequest();
        payableFilterRequest.setWherePaid(false);
        payableFilterRequest.setOrderByField(PayableOrderByField.DUE_DATE);
        PayableFilterResponse payableFilterResponse = null;
        try {
            payableFilterResponse = PayableService.filterPayables(payableFilterRequest);
        } catch (DoughException e) {
            errors.add(e.getMessage());
        }
        req.setAttribute("payableResultList", payableFilterResponse.getResultList());

        // Go to next page.
        req.setAttribute("errors", errors);
        req.setAttribute("messages", messages);
        getServletContext().getRequestDispatcher("/Dashboard.jsp").forward(req, resp);
    }
}
