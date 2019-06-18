package name.mutant.dough.controller;

import name.mutant.dough.DoughNotFoundException;
import name.mutant.dough.DoughOptimisticLockingException;
import name.mutant.dough.domain.Payable;
import name.mutant.dough.domain.Payee;
import name.mutant.dough.service.PayableService;
import name.mutant.dough.service.PayeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static name.mutant.dough.controller.MessagesConstants.*;

@Controller
public class PayableController {
    private static final Logger logger = LoggerFactory.getLogger(PayableController.class);
    @Autowired
    private PayableService payableService;
    @Autowired
    private PayeeService payeeService;

    @RequestMapping("/payableList")
    public String loadList(Model model) {
        Iterable<Payable> payables = payableService.findAllPayables();
        model.addAttribute("payables", payables);
        return "payableList";
    }

    @RequestMapping("/payable")
    public String loadView(@RequestParam("payableId") Long payableId, Model model,
                           RedirectAttributes redirectAttributes) {
        try {
            Payable payable = payableService.findPayableById(payableId);
            model.addAttribute("payable", payable);
            return "payableView";
        } catch (DoughNotFoundException e) {
            String msg = String.format(NOT_FOUND_ERROR, "Payable", payableId);
            logger.warn(msg, e);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/payableList";
        }
    }

    @GetMapping("/payableEdit")
    public String loadEdit(@RequestParam(value = "payableId", required = false) Long payableId,
                           @RequestParam(value = "payeeId", required = false) Long payeeId, Model model,
                           RedirectAttributes redirectAttributes) {
        // If no payable id, new payable.
        if (payableId == null) {
            PayableForm payableForm = new PayableForm();
            if (payeeId != null)
                payableForm.setPayeeId(payeeId);
            model.addAttribute("payableForm", payableForm);
            return "payableEdit";
        }
        // Otherwise, edit existing payable.
        try {
            Payable payable = payableService.findPayableById(payableId);
            PayableForm payableForm = new PayableForm(payable);
            model.addAttribute("payableForm", payableForm);
            return "payableEdit";
        } catch (DoughNotFoundException e) {
            String msg = String.format(NOT_FOUND_ERROR, "Payable", payableId);
            logger.warn(msg, e);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/payableList";
        }
    }

    @PostMapping("/payableEdit")
    public String processEdit(@Valid PayableForm payableForm, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "payableEdit";
        }
        // Convert form to entity and save.
        Long payableId = payableForm.getId();
        Payable payable = payableForm.toPayable(payeeService);
        try {
            Payable save = payableService.savePayable(payable);
            String successMessage = String.format(SUCCESSFULLY_ADDED, "Payable", save.getId());
            if (payableId != null)
                successMessage = String.format(SUCCESSFULLY_UPDATED, "Payable", save.getId());
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            redirectAttributes.addAttribute("payableId", save.getId());
            return "redirect:/payable?payableId={payableId}";
        } catch (DoughOptimisticLockingException e) {
            String msg = String.format(OPTIMISTIC_LOCK_ERROR, "Payable", payableId);
            logger.warn(msg, e);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            redirectAttributes.addAttribute("payableId", payableId);
            return "redirect:/payable?payableId={payableId}";
        }
    }

    @ModelAttribute("allPayees")
    public Iterable<Payee> loadPayeeDropDown() {
        return payeeService.findAllPayees();
    }
}