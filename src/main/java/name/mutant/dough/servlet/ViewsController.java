package name.mutant.dough.servlet;

import name.mutant.dough.DoughException;
import name.mutant.dough.service.AcctService;
import name.mutant.dough.service.PayableService;
import name.mutant.dough.service.dto.AcctBalance;
import name.mutant.dough.service.dto.BillToPay;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ViewsController {
    @RequestMapping(value = "/loadDashboard")
    public String loadDashboard(Model model) {
        List<BillToPay> billsToPay = new ArrayList<>();
        try {
            billsToPay.addAll(PayableService.getBillsToPay(new Date()));
        } catch (DoughException e) {
            throw new RuntimeException(e.getMessage(), e); // TODO Add real error handling.
        }
        model.addAttribute("billsToPay", billsToPay);

        List<AcctBalance> acctBalances = new ArrayList<>();
        try {
            acctBalances.addAll(AcctService.getAcctBalances());
        } catch (DoughException e) {
            throw new RuntimeException(e.getMessage(), e); // TODO Add real error handling.
        }
        model.addAttribute("acctBalances", acctBalances);

        return "/WEB-INF/views/dashboard.jsp";
    }
}
