package name.mutant.dough.controller;

import name.mutant.dough.DoughNotFoundException;
import name.mutant.dough.DoughOptimisticLockingException;
import name.mutant.dough.domain.Pattern;
import name.mutant.dough.domain.Tran;
import name.mutant.dough.service.PatternService;
import name.mutant.dough.service.TranService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static name.mutant.dough.controller.MessagesConstants.*;

@Controller
public class TranController {
    private static final Logger logger = LoggerFactory.getLogger(TranController.class);
    @Autowired
    private TranService tranService;
    @Autowired
    private PatternService patternService;

    @RequestMapping("/tran")
    public String loadView(@RequestParam("tranId") Long tranId, Model model, RedirectAttributes redirectAttributes) {
        try {
            Tran tran = tranService.findTranById(tranId);
            model.addAttribute("tran", tran);
            return "tranView";
        } catch (DoughNotFoundException e) {
            String msg = String.format(NOT_FOUND_ERROR, "Tran", tranId);
            logger.warn(msg, e);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/tranList";
        }
    }

    @RequestMapping("/tranAssign")
    public String assignCategories(RedirectAttributes redirectAttributes) {
        List<Pattern> patterns = patternService.findAllPatterns();
        List<Tran> trans = tranService.findAllNonAssigned();
        List<Tran> assignUs = new ArrayList<>();
        for (Tran tran : trans) {
            boolean found = false;
            for (int i = 0; i < patterns.size() && !found; i++) {
                Pattern pattern = patterns.get(i);
                if (tran.getName().matches(pattern.getTranName())) {
                    tran.setCategory(pattern.getCategory());
                    assignUs.add(tran);
                    found = true;
                }
            }
        }
        try {
            tranService.saveAllTrans(assignUs);
            String successMessage = String.format(SUCCESSFULLY_ASSIGNED_CATEGORIES, assignUs.size());
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            return "redirect:/";
        } catch (DoughOptimisticLockingException e) {
            String msg = String.format(MULTI_OPTIMISTIC_LOCK_ERROR, "Transactions");
            logger.warn(msg, e);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/";
        }
    }
}