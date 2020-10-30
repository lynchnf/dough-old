package norman.dough.web;

import norman.dough.domain.Acct;
import norman.dough.domain.Stmt;
import norman.dough.exception.NotFoundException;
import norman.dough.exception.OptimisticLockingException;
import norman.dough.service.AcctService;
import norman.dough.service.StmtService;
import norman.dough.web.view.StmtEditForm;
import norman.dough.web.view.StmtListForm;
import norman.dough.web.view.StmtView;
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
public class StmtController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StmtController.class);
    private static final String defaultSortColumn = "id";
    private static final String[] sortableColumns = {"closeBalance", "minimumDue", "dueDate", "closeDate"};
    @Autowired
    private StmtService service;
    @Autowired
    private AcctService acctService;

    @GetMapping("/stmtList")
    public String loadStmtList(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortColumn", required = false, defaultValue = "closeDate") String sortColumn,
            @RequestParam(value = "sortDirection", required = false, defaultValue = "ASC") Sort.Direction sortDirection,
            Model model) {

        // Convert sort column from string to an array of strings.
        String[] sortColumns = {defaultSortColumn};
        if (Arrays.asList(sortableColumns).contains(sortColumn)) {
            sortColumns = new String[]{sortColumn, defaultSortColumn};
        }

        // Get a page of records.
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sortDirection, sortColumns);
        Page<Stmt> page = service.findAll(pageable);

        // Display the page of records.
        StmtListForm listForm = new StmtListForm(page);
        model.addAttribute("listForm", listForm);
        return "stmtList";
    }

    @GetMapping("/stmt")
    public String loadStmtView(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Stmt entity = service.findById(id);
            StmtView view = new StmtView(entity);
            model.addAttribute("view", view);
            return "stmtView";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Statement not found.");
            return "redirect:/stmtList";
        }
    }

    @GetMapping("/stmtEdit")
    public String loadStmtEdit(@RequestParam(value = "id", required = false) Long id, Model model,
            RedirectAttributes redirectAttributes) {

        // If no id, add new record.
        if (id == null) {
            model.addAttribute("editForm", new StmtEditForm());
            return "stmtEdit";
        }

        // Otherwise, edit existing record.
        try {
            Stmt entity = service.findById(id);
            StmtEditForm editForm = new StmtEditForm(entity);
            model.addAttribute("editForm", editForm);
            return "stmtEdit";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Statement not found.");
            return "redirect:/stmtList";
        }
    }

    @PostMapping("/stmtEdit")
    public String processStmtEdit(@Valid @ModelAttribute("editForm") StmtEditForm editForm, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "stmtEdit";
        }

        // Convert form to entity.
        Long id = editForm.getId();
        try {
            editForm.setAcctService(acctService);
            Stmt entity = editForm.toEntity();

            // Save entity.
            Stmt save = service.save(entity);
            String successMessage = "Statement successfully added.";
            if (id != null) {
                successMessage = "Statement successfully updated";
            }
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            redirectAttributes.addAttribute("id", save.getId());
            return "redirect:/stmt?id={id}";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getEntityName() + " not found.");
            return "redirect:/stmtList";
        } catch (OptimisticLockingException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Statement was updated by another user.");
            return "redirect:/stmtList";
        }
    }

    @ModelAttribute("allAcct")
    public Iterable<Acct> loadAcctDropDown() {
        return acctService.findAll();
    }
}
