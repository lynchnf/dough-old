package norman.dough.web;

import norman.dough.domain.Acct;
import norman.dough.exception.NotFoundException;
import norman.dough.exception.OptimisticLockingException;
import norman.dough.service.AcctService;
import norman.dough.web.view.AcctEditForm;
import norman.dough.web.view.AcctListForm;
import norman.dough.web.view.AcctView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
public class AcctController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AcctController.class);
    private static final String defaultSortColumn = "id";
    private static final String[] sortableColumns = {"name", "nickname", "type", "addressName", "address1", "address2", "city", "state", "zipCode", "phoneNumber", "creditLimit", "active"};
    @Autowired
    private AcctService service;

    @GetMapping("/acctList")
    public String loadAcctList(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortColumn", required = false, defaultValue = "name") String sortColumn,
            @RequestParam(value = "sortDirection", required = false, defaultValue = "ASC") Sort.Direction sortDirection,
            Model model) {

        // Convert sort column from string to an array of strings.
        String[] sortColumns = {defaultSortColumn};
        if (Arrays.asList(sortableColumns).contains(sortColumn)) {
            sortColumns = new String[]{sortColumn, defaultSortColumn};
        }

        // Get a page of records.
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sortDirection, sortColumns);
        Page<Acct> page = service.findAll(pageable);

        // Display the page of records.
        AcctListForm listForm = new AcctListForm(page);
        model.addAttribute("listForm", listForm);
        return "acctList";
    }

    @GetMapping("/acct")
    public String loadAcctView(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Acct entity = service.findById(id);
            AcctView view = new AcctView(entity);
            model.addAttribute("view", view);
            return "acctView";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Account not found.");
            return "redirect:/acctList";
        }
    }

    @GetMapping("/acctEdit")
    public String loadAcctEdit(@RequestParam(value = "id", required = false) Long id, Model model,
            RedirectAttributes redirectAttributes) {

        // If no id, add new record.
        if (id == null) {
            model.addAttribute("editForm", new AcctEditForm());
            return "acctEdit";
        }

        // Otherwise, edit existing record.
        try {
            Acct entity = service.findById(id);
            AcctEditForm editForm = new AcctEditForm(entity);
            model.addAttribute("editForm", editForm);
            return "acctEdit";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Account not found.");
            return "redirect:/acctList";
        }
    }

    @PostMapping("/acctEdit")
    public String processAcctEdit(@Valid @ModelAttribute("editForm") AcctEditForm editForm, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "acctEdit";
        }

        // Convert form to entity.
        Long id = editForm.getId();
        try {
            Acct entity = editForm.toEntity();

            // Save entity.
            Acct save = service.save(entity);
            String successMessage = "Account successfully added.";
            if (id != null) {
                successMessage = "Account successfully updated";
            }
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            redirectAttributes.addAttribute("id", save.getId());
            return "redirect:/acct?id={id}";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getEntityName() + " not found.");
            return "redirect:/acctList";
        } catch (OptimisticLockingException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Account was updated by another user.");
            return "redirect:/acctList";
        }
    }
}
