package name.mutant.dough.controller;

import name.mutant.dough.DoughNotFoundException;
import name.mutant.dough.DoughOptimisticLockingException;
import name.mutant.dough.domain.Payee;
import name.mutant.dough.service.PayeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static name.mutant.dough.controller.MessagesConstants.*;

@Controller
public class PayeeController {
    private static final Logger logger = LoggerFactory.getLogger(PayeeController.class);
    @Autowired
    private PayeeService payeeService;

    @RequestMapping("/payeeList")
    public String loadList(Model model) {
        Iterable<Payee> payees = payeeService.findAllPayees();
        model.addAttribute("payees", payees);
        return "payeeList";
    }

    @RequestMapping("/payee")
    public String loadView(@RequestParam("payeeId") Long payeeId, Model model, RedirectAttributes redirectAttributes) {
        try {
            Payee payee = payeeService.findPayeeById(payeeId);
            model.addAttribute("payee", payee);
            return "payeeView";
        } catch (DoughNotFoundException e) {
            String msg = String.format(NOT_FOUND_ERROR, "Payee", payeeId);
            logger.warn(msg, e);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/payeeList";
        }
    }

    @GetMapping("/payeeEdit")
    public String loadEdit(@RequestParam(value = "payeeId", required = false) Long payeeId, Model model,
                           RedirectAttributes redirectAttributes) {
        // If no payee id, new payee.
        if (payeeId == null) {
            model.addAttribute("payeeForm", new PayeeForm());
            return "payeeEdit";
        }
        // Otherwise, edit existing payee.
        try {
            Payee payee = payeeService.findPayeeById(payeeId);
            PayeeForm payeeForm = new PayeeForm(payee);
            model.addAttribute("payeeForm", payeeForm);
            return "payeeEdit";
        } catch (DoughNotFoundException e) {
            String msg = String.format(NOT_FOUND_ERROR, "Payee", payeeId);
            logger.warn(msg, e);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/payeeList";
        }
    }

    @PostMapping("/payeeEdit")
    public String processEdit(@Valid PayeeForm payeeForm, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "payeeEdit";
        }
        // Convert form to entity and save.
        Long payeeId = payeeForm.getId();
        Payee payee = payeeForm.toPayee();
        try {
            Payee save = payeeService.savePayee(payee);
            String successMessage = String.format(SUCCESSFULLY_ADDED, "Payee", save.getId());
            if (payeeId != null)
                successMessage = String.format(SUCCESSFULLY_UPDATED, "Payee", save.getId());
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            redirectAttributes.addAttribute("payeeId", save.getId());
            return "redirect:/payee?payeeId={payeeId}";
        } catch (DoughOptimisticLockingException e) {
            String msg = String.format(OPTIMISTIC_LOCK_ERROR, "Payee", payeeId);
            logger.warn(msg, e);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            redirectAttributes.addAttribute("payeeId", payeeId);
            return "redirect:/payee?payeeId={payeeId}";
        }
    }
}