package name.mutant.dough.controller;

import name.mutant.dough.DoughNotFoundException;
import name.mutant.dough.DoughOptimisticLockingException;
import name.mutant.dough.domain.Category;
import name.mutant.dough.service.CategoryService;
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
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/categoryList")
    public String loadList(Model model) {
        Iterable<Category> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);
        return "categoryList";
    }

    @RequestMapping("/category")
    public String loadView(@RequestParam("categoryId") Long categoryId, Model model,
                           RedirectAttributes redirectAttributes) {
        try {
            Category category = categoryService.findCategoryById(categoryId);
            model.addAttribute("category", category);
            return "categoryView";
        } catch (DoughNotFoundException e) {
            String msg = String.format(NOT_FOUND_ERROR, "Category", categoryId);
            logger.warn(msg, e);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/categoryList";
        }
    }

    @GetMapping("/categoryEdit")
    public String loadEdit(@RequestParam(value = "categoryId", required = false) Long categoryId, Model model,
                           RedirectAttributes redirectAttributes) {
        // If no category id, new category.
        if (categoryId == null) {
            model.addAttribute("categoryForm", new CategoryForm());
            return "categoryEdit";
        }
        // Otherwise, edit existing category.
        try {
            Category category = categoryService.findCategoryById(categoryId);
            CategoryForm categoryForm = new CategoryForm(category);
            model.addAttribute("categoryForm", categoryForm);
            return "categoryEdit";
        } catch (DoughNotFoundException e) {
            String msg = String.format(NOT_FOUND_ERROR, "Category", categoryId);
            logger.warn(msg, e);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/categoryList";
        }
    }

    @PostMapping("/categoryEdit")
    public String processEdit(@Valid CategoryForm categoryForm, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "categoryEdit";
        }
        // Convert form to entity and save.
        Long categoryId = categoryForm.getId();
        Category category = categoryForm.toCategory();
        try {
            Category save = categoryService.saveCategory(category);
            String successMessage = String.format(SUCCESSFULLY_ADDED, "Category", save.getId());
            if (categoryId != null)
                successMessage = String.format(SUCCESSFULLY_UPDATED, "Category", save.getId());
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            redirectAttributes.addAttribute("categoryId", save.getId());
            return "redirect:/category?categoryId={categoryId}";
        } catch (DoughOptimisticLockingException e) {
            String msg = String.format(OPTIMISTIC_LOCK_ERROR, "Category", categoryId);
            logger.warn(msg, e);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            redirectAttributes.addAttribute("categoryId", categoryId);
            return "redirect:/category?categoryId={categoryId}";
        }
    }
}