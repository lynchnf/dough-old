package name.mutant.dough.controller;

import name.mutant.dough.DoughOptimisticLockingException;
import name.mutant.dough.domain.Category;
import name.mutant.dough.domain.Pattern;
import name.mutant.dough.service.CategoryService;
import name.mutant.dough.service.PatternService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

import static name.mutant.dough.controller.MessagesConstants.MULTI_OPTIMISTIC_LOCK_ERROR;
import static name.mutant.dough.controller.MessagesConstants.SUCCESSFULLY_UPDATED_MULTI;

@Controller
public class PatternController {
    private static final Logger logger = LoggerFactory.getLogger(PatternController.class);
    @Autowired
    private PatternService patternService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/patternList")
    public String loadList(Model model) {
        Iterable<Pattern> patterns = patternService.findAllPatterns();
        model.addAttribute("patterns", patterns);
        return "patternList";
    }

    @GetMapping("/patternEdit")
    public String loadEdit(Model model) {
        Iterable<Pattern> patterns = patternService.findAllPatterns();
        PatternForm patternForm = new PatternForm(patterns);
        model.addAttribute("patternForm", patternForm);
        return "patternEdit";
    }

    @PostMapping("/patternEdit")
    public String processEdit(@Valid PatternForm patternForm, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "patternEdit";
        }
        // Convert form to entities and save.
        List<Pattern> patterns = patternForm.toPatterns(categoryService);
        try {
            patternService.saveAllPatterns(patterns);
            String successMessage = String.format(SUCCESSFULLY_UPDATED_MULTI, "Patterns");
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            return "redirect:/patternList";
        } catch (DoughOptimisticLockingException e) {
            String msg = String.format(MULTI_OPTIMISTIC_LOCK_ERROR, "Patterns");
            logger.warn(msg, e);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/patternList";
        }
    }

    @ModelAttribute("allCategories")
    public Iterable<Category> loadCategoryDropDown() {
        return categoryService.findAllCategories();
    }
}