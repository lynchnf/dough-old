package name.mutant.dough.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lynchnf on 11/8/17.
 */
@Controller
public class DashboardController {
    @RequestMapping("/")
    public String view() {
        return "dashboard";
    }
}
