package norman.dough.web;

import norman.dough.domain.Cat;
import norman.dough.domain.Pattern;
import norman.dough.exception.NotFoundException;
import norman.dough.exception.OptimisticLockingException;
import norman.dough.service.CatService;
import norman.dough.service.PatternService;
import norman.dough.web.view.PatternEditForm;
import norman.dough.web.view.PatternListForm;
import norman.dough.web.view.PatternView;
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
public class PatternController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatternController.class);
    private static final String defaultSortColumn = "id";
    private static final String[] sortableColumns = {"seq", "regex", "cat"};
    @Autowired
    private PatternService service;
    @Autowired
    private CatService catService;

    @GetMapping("/patternList")
    public String loadPatternList(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortColumn", required = false, defaultValue = "seq") String sortColumn,
            @RequestParam(value = "sortDirection", required = false, defaultValue = "ASC") Sort.Direction sortDirection,
            Model model) {

        // Convert sort column from string to an array of strings.
        String[] sortColumns = {defaultSortColumn};
        if (Arrays.asList(sortableColumns).contains(sortColumn)) {
            sortColumns = new String[]{sortColumn, defaultSortColumn};
        }

        // Get a page of records.
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sortDirection, sortColumns);
        Page<Pattern> page = service.findAll(pageable);

        // Display the page of records.
        PatternListForm listForm = new PatternListForm(page);
        model.addAttribute("listForm", listForm);
        return "patternList";
    }

    @GetMapping("/pattern")
    public String loadPatternView(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Pattern entity = service.findById(id);
            PatternView view = new PatternView(entity);
            model.addAttribute("view", view);
            return "patternView";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Pattern not found.");
            return "redirect:/patternList";
        }
    }

    @GetMapping("/patternEdit")
    public String loadPatternEdit(@RequestParam(value = "id", required = false) Long id, Model model,
            RedirectAttributes redirectAttributes) {

        // If no id, add new record.
        if (id == null) {
            model.addAttribute("editForm", new PatternEditForm());
            return "patternEdit";
        }

        // Otherwise, edit existing record.
        try {
            Pattern entity = service.findById(id);
            PatternEditForm editForm = new PatternEditForm(entity);
            model.addAttribute("editForm", editForm);
            return "patternEdit";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Pattern not found.");
            return "redirect:/patternList";
        }
    }

    @PostMapping("/patternEdit")
    public String processPatternEdit(@Valid @ModelAttribute("editForm") PatternEditForm editForm,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "patternEdit";
        }

        // Convert form to entity.
        Long id = editForm.getId();
        try {
            editForm.setCatService(catService);
            Pattern entity = editForm.toEntity();

            // Save entity.
            Pattern save = service.save(entity);
            String successMessage = "Pattern successfully added.";
            if (id != null) {
                successMessage = "Pattern successfully updated.";
            }
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            redirectAttributes.addAttribute("id", save.getId());
            return "redirect:/pattern?id={id}";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getEntityName() + " not found.");
            return "redirect:/patternList";
        } catch (OptimisticLockingException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Pattern was updated by another user.");
            return "redirect:/patternList";
        }
    }

    @ModelAttribute("allCat")
    public Iterable<Cat> loadCatDropDown() {
        return catService.findAll();
    }
}
