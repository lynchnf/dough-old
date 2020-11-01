package norman.dough.web;

import norman.dough.domain.DataFile;
import norman.dough.domain.DataTran;
import norman.dough.exception.NotFoundException;
import norman.dough.exception.OptimisticLockingException;
import norman.dough.service.DataFileService;
import norman.dough.service.DataTranService;
import norman.dough.web.view.DataTranEditForm;
import norman.dough.web.view.DataTranListForm;
import norman.dough.web.view.DataTranView;
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
public class DataTranController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataTranController.class);
    private static final String defaultSortColumn = "id";
    private static final String[] sortableColumns =
            {"ofxType", "ofxPostDate", "ofxUserDate", "ofxAmount", "ofxFitId", "ofxSic", "ofxCheckNumber",
                    "ofxCorrectFitId", "ofxCorrectAction", "ofxName", "ofxCategory", "ofxMemo"};
    @Autowired
    private DataTranService service;
    @Autowired
    private DataFileService dataFileService;

    @GetMapping("/dataTranList")
    public String loadDataTranList(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortColumn", required = false, defaultValue = "ofxPostDate") String sortColumn,
            @RequestParam(value = "sortDirection", required = false, defaultValue = "ASC") Sort.Direction sortDirection,
            Model model) {

        // Convert sort column from string to an array of strings.
        String[] sortColumns = {defaultSortColumn};
        if (Arrays.asList(sortableColumns).contains(sortColumn)) {
            sortColumns = new String[]{sortColumn, defaultSortColumn};
        }

        // Get a page of records.
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sortDirection, sortColumns);
        Page<DataTran> page = service.findAll(pageable);

        // Display the page of records.
        DataTranListForm listForm = new DataTranListForm(page);
        model.addAttribute("listForm", listForm);
        return "dataTranList";
    }

    @GetMapping("/dataTran")
    public String loadDataTranView(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            DataTran entity = service.findById(id);
            DataTranView view = new DataTranView(entity);
            model.addAttribute("view", view);
            return "dataTranView";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Data transaction not found.");
            return "redirect:/dataTranList";
        }
    }

    @GetMapping("/dataTranEdit")
    public String loadDataTranEdit(@RequestParam(value = "id", required = false) Long id, Model model,
            RedirectAttributes redirectAttributes) {

        // If no id, add new record.
        if (id == null) {
            model.addAttribute("editForm", new DataTranEditForm());
            return "dataTranEdit";
        }

        // Otherwise, edit existing record.
        try {
            DataTran entity = service.findById(id);
            DataTranEditForm editForm = new DataTranEditForm(entity);
            model.addAttribute("editForm", editForm);
            return "dataTranEdit";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Data transaction not found.");
            return "redirect:/dataTranList";
        }
    }

    @PostMapping("/dataTranEdit")
    public String processDataTranEdit(@Valid @ModelAttribute("editForm") DataTranEditForm editForm,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "dataTranEdit";
        }

        // Convert form to entity.
        Long id = editForm.getId();
        try {
            editForm.setDataFileService(dataFileService);
            DataTran entity = editForm.toEntity();

            // Save entity.
            DataTran save = service.save(entity);
            String successMessage = "Data transaction successfully added.";
            if (id != null) {
                successMessage = "Data transaction successfully updated.";
            }
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            redirectAttributes.addAttribute("id", save.getId());
            return "redirect:/dataTran?id={id}";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getEntityName() + " not found.");
            return "redirect:/dataTranList";
        } catch (OptimisticLockingException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Data transaction was updated by another user.");
            return "redirect:/dataTranList";
        }
    }

    @ModelAttribute("allDataFile")
    public Iterable<DataFile> loadDataFileDropDown() {
        return dataFileService.findAll();
    }
}
