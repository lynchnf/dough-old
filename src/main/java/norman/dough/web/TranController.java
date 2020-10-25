package norman.dough.web;

import norman.dough.domain.Tran;
    import norman.dough.domain.Stmt;
import norman.dough.exception.NotFoundException;
import norman.dough.exception.OptimisticLockingException;
import norman.dough.service.TranService;
    import norman.dough.service.StmtService;
import norman.dough.web.view.TranEditForm;
import norman.dough.web.view.TranListForm;
import norman.dough.web.view.TranView;
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
public class TranController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TranController.class);
    private static final String defaultSortColumn = "id";
    private static final String[] sortableColumns = {"stmt", "status", "postDate", "manualPostDate", "uploadedPostDate", "amount", "manualAmount", "uploadedAmount", "checkNumber", "manualCheckNumber", "uploadedCheckNumber", "name", "manualName", "uploadedName", "memo", "manualMemo", "uploadedMemo", "voided"};
    @Autowired
    private TranService service;
    @Autowired
    private StmtService stmtService;

    @GetMapping("/tranList")
    public String loadTranList(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortColumn", required = false, defaultValue = "postDate") String sortColumn,
            @RequestParam(value = "sortDirection", required = false, defaultValue = "ASC") Sort.Direction sortDirection,
            Model model) {

        // Convert sort column from string to an array of strings.
        String[] sortColumns = {defaultSortColumn};
        if (Arrays.asList(sortableColumns).contains(sortColumn)) {
            sortColumns = new String[]{sortColumn, defaultSortColumn};
        }

        // Get a page of records.
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sortDirection, sortColumns);
        Page<Tran> page = service.findAll(pageable);

        // Display the page of records.
        TranListForm listForm = new TranListForm(page);
        model.addAttribute("listForm", listForm);
        return "tranList";
    }

    @GetMapping("/tran")
    public String loadTranView(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Tran entity = service.findById(id);
            TranView view = new TranView(entity);
            model.addAttribute("view", view);
            return "tranView";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Transaction not found.");
            return "redirect:/tranList";
        }
    }

    @GetMapping("/tranEdit")
    public String loadTranEdit(@RequestParam(value = "id", required = false) Long id, Model model,
            RedirectAttributes redirectAttributes) {

        // If no id, add new record.
        if (id == null) {
            model.addAttribute("editForm", new TranEditForm());
            return "tranEdit";
        }

        // Otherwise, edit existing record.
        try {
            Tran entity = service.findById(id);
            TranEditForm editForm = new TranEditForm(entity);
            model.addAttribute("editForm", editForm);
            return "tranEdit";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Transaction not found.");
            return "redirect:/tranList";
        }
    }

    @PostMapping("/tranEdit")
    public String processTranEdit(@Valid @ModelAttribute("editForm") TranEditForm editForm, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "tranEdit";
        }

        // Convert form to entity.
        Long id = editForm.getId();
        try {
            editForm.setStmtService(stmtService);
            Tran entity = editForm.toEntity();

            // Save entity.
            Tran save = service.save(entity);
            String successMessage = "Transaction successfully added.";
            if (id != null) {
                successMessage = "Transaction successfully updated";
            }
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            redirectAttributes.addAttribute("id", save.getId());
            return "redirect:/tran?id={id}";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getEntityName() + " not found.");
            return "redirect:/tranList";
        } catch (OptimisticLockingException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Transaction was updated by another user.");
            return "redirect:/tranList";
        }
    }

    @ModelAttribute("allStmt")
    public Iterable<Stmt> loadStmtDropDown() {
        return stmtService.findAll();
    }
}
