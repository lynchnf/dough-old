package norman.dough.web;

import norman.dough.domain.Cat;
import norman.dough.exception.NotFoundException;
import norman.dough.exception.OptimisticLockingException;
import norman.dough.service.CatService;
import norman.dough.web.view.CatEditForm;
import norman.dough.web.view.CatListForm;
import norman.dough.web.view.CatView;
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
public class CatController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CatController.class);
    private static final String defaultSortColumn = "id";
    private static final String[] sortableColumns = {"name"};
    @Autowired
    private CatService service;

    @GetMapping("/catList")
    public String loadCatList(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
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
        Page<Cat> page = service.findAll(pageable);

        // Display the page of records.
        CatListForm listForm = new CatListForm(page);
        model.addAttribute("listForm", listForm);
        return "catList";
    }

    @GetMapping("/cat")
    public String loadCatView(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Cat entity = service.findById(id);
            CatView view = new CatView(entity);
            model.addAttribute("view", view);
            return "catView";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Category not found.");
            return "redirect:/catList";
        }
    }

    @GetMapping("/catEdit")
    public String loadCatEdit(@RequestParam(value = "id", required = false) Long id, Model model,
            RedirectAttributes redirectAttributes) {

        // If no id, add new record.
        if (id == null) {
            model.addAttribute("editForm", new CatEditForm());
            return "catEdit";
        }

        // Otherwise, edit existing record.
        try {
            Cat entity = service.findById(id);
            CatEditForm editForm = new CatEditForm(entity);
            model.addAttribute("editForm", editForm);
            return "catEdit";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Category not found.");
            return "redirect:/catList";
        }
    }

    @PostMapping("/catEdit")
    public String processCatEdit(@Valid @ModelAttribute("editForm") CatEditForm editForm, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "catEdit";
        }

        // Convert form to entity.
        Long id = editForm.getId();
        try {
            Cat entity = editForm.toEntity();

            // Save entity.
            Cat save = service.save(entity);
            String successMessage = "Category successfully added.";
            if (id != null) {
                successMessage = "Category successfully updated";
            }
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            redirectAttributes.addAttribute("id", save.getId());
            return "redirect:/cat?id={id}";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getEntityName() + " not found.");
            return "redirect:/catList";
        } catch (OptimisticLockingException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Category was updated by another user.");
            return "redirect:/catList";
        }
    }
}
