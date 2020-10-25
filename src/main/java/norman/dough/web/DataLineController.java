package norman.dough.web;

import norman.dough.domain.DataFile;
import norman.dough.domain.DataLine;
import norman.dough.exception.NotFoundException;
import norman.dough.exception.OptimisticLockingException;
import norman.dough.service.DataFileService;
import norman.dough.service.DataLineService;
import norman.dough.web.view.DataLineEditForm;
import norman.dough.web.view.DataLineListForm;
import norman.dough.web.view.DataLineView;
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
public class DataLineController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataLineController.class);
    private static final String defaultSortColumn = "id";
    private static final String[] sortableColumns = {"dataFile", "seq", "text"};
    @Autowired
    private DataLineService service;
    @Autowired
    private DataFileService dataFileService;

    @GetMapping("/dataLineList")
    public String loadDataLineList(
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
        Page<DataLine> page = service.findAll(pageable);

        // Display the page of records.
        DataLineListForm listForm = new DataLineListForm(page);
        model.addAttribute("listForm", listForm);
        return "dataLineList";
    }

    @GetMapping("/dataLine")
    public String loadDataLineView(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            DataLine entity = service.findById(id);
            DataLineView view = new DataLineView(entity);
            model.addAttribute("view", view);
            return "dataLineView";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Data line not found.");
            return "redirect:/dataLineList";
        }
    }

    @GetMapping("/dataLineEdit")
    public String loadDataLineEdit(@RequestParam(value = "id", required = false) Long id, Model model,
            RedirectAttributes redirectAttributes) {

        // If no id, add new record.
        if (id == null) {
            model.addAttribute("editForm", new DataLineEditForm());
            return "dataLineEdit";
        }

        // Otherwise, edit existing record.
        try {
            DataLine entity = service.findById(id);
            DataLineEditForm editForm = new DataLineEditForm(entity);
            model.addAttribute("editForm", editForm);
            return "dataLineEdit";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Data line not found.");
            return "redirect:/dataLineList";
        }
    }

    @PostMapping("/dataLineEdit")
    public String processDataLineEdit(@Valid @ModelAttribute("editForm") DataLineEditForm editForm,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "dataLineEdit";
        }

        // Convert form to entity.
        Long id = editForm.getId();
        try {
            editForm.setDataFileService(dataFileService);
            DataLine entity = editForm.toEntity();

            // Save entity.
            DataLine save = service.save(entity);
            String successMessage = "Data line successfully added.";
            if (id != null) {
                successMessage = "Data line successfully updated";
            }
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            redirectAttributes.addAttribute("id", save.getId());
            return "redirect:/dataLine?id={id}";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getEntityName() + " not found.");
            return "redirect:/dataLineList";
        } catch (OptimisticLockingException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Data line was updated by another user.");
            return "redirect:/dataLineList";
        }
    }

    @ModelAttribute("allDataFile")
    public Iterable<DataFile> loadDataFileDropDown() {
        return dataFileService.findAll();
    }
}
