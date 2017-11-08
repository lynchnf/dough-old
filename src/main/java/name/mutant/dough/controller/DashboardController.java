package name.mutant.dough.controller;

import name.mutant.dough.service.AcctBalance;
import name.mutant.dough.service.AcctService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by lynchnf on 11/8/17.
 */
@Controller
public class DashboardController {
    @Autowired
    private AcctService acctService;

    @RequestMapping("/")
    public String view(Model model) {
        List<AcctBalance> accountBalances = acctService.getAccountBalances();
        model.addAttribute("accountBalances", accountBalances);
        return "dashboard";
    }
}