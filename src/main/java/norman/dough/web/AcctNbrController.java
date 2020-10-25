package norman.dough.web;

import norman.dough.domain.AcctNbr;
    import norman.dough.domain.Acct;
import norman.dough.exception.NotFoundException;
import norman.dough.exception.OptimisticLockingException;
import norman.dough.service.AcctNbrService;
    import norman.dough.service.AcctService;
import norman.dough.web.view.AcctNbrEditForm;
import norman.dough.web.view.AcctNbrListForm;
import norman.dough.web.view.AcctNbrView;
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
public class AcctNbrController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AcctNbrController.class);
    private static final String defaultSortColumn = "id";
    private static final String[] sortableColumns = {"acct", "number", "effDate"};
    @Autowired
    private AcctNbrService service;
    @Autowired
    private AcctService acctService;

    @GetMapping("/acctNbrList")
    public String loadAcctNbrList(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortColumn", required = false, defaultValue = "number") String sortColumn,
            @RequestParam(value = "sortDirection", required = false, defaultValue = "ASC") Sort.Direction sortDirection,
            Model model) {

        // Convert sort column from string to an array of strings.
        String[] sortColumns = {defaultSortColumn};
        if (Arrays.asList(sortableColumns).contains(sortColumn)) {
            sortColumns = new String[]{sortColumn, defaultSortColumn};
        }

        // Get a page of records.
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sortDirection, sortColumns);
        Page<AcctNbr> page = service.findAll(pageable);

        // Display the page of records.
        AcctNbrListForm listForm = new AcctNbrListForm(page);
        model.addAttribute("listForm", listForm);
        return "acctNbrList";
    }

    @GetMapping("/acctNbr")
    public String loadAcctNbrView(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            AcctNbr entity = service.findById(id);
            AcctNbrView view = new AcctNbrView(entity);
            model.addAttribute("view", view);
            return "acctNbrView";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Account number not found.");
            return "redirect:/acctNbrList";
        }
    }

    @GetMapping("/acctNbrEdit")
    public String loadAcctNbrEdit(@RequestParam(value = "id", required = false) Long id, Model model,
            RedirectAttributes redirectAttributes) {

        // If no id, add new record.
        if (id == null) {
            model.addAttribute("editForm", new AcctNbrEditForm());
            return "acctNbrEdit";
        }

        // Otherwise, edit existing record.
        try {
            AcctNbr entity = service.findById(id);
            AcctNbrEditForm editForm = new AcctNbrEditForm(entity);
            model.addAttribute("editForm", editForm);
            return "acctNbrEdit";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Account number not found.");
            return "redirect:/acctNbrList";
        }
    }

    @PostMapping("/acctNbrEdit")
    public String processAcctNbrEdit(@Valid @ModelAttribute("editForm") AcctNbrEditForm editForm, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "acctNbrEdit";
        }

        // Convert form to entity.
        Long id = editForm.getId();
        try {
            editForm.setAcctService(acctService);
            AcctNbr entity = editForm.toEntity();

            // Save entity.
            AcctNbr save = service.save(entity);
            String successMessage = "Account number successfully added.";
            if (id != null) {
                successMessage = "Account number successfully updated";
            }
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            redirectAttributes.addAttribute("id", save.getId());
            return "redirect:/acctNbr?id={id}";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getEntityName() + " not found.");
            return "redirect:/acctNbrList";
        } catch (OptimisticLockingException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Account number was updated by another user.");
            return "redirect:/acctNbrList";
        }
    }

    @ModelAttribute("allAcct")
    public Iterable<Acct> loadAcctDropDown() {
        return acctService.findAll();
    }
}
