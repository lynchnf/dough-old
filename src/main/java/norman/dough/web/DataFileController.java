package norman.dough.web;

import norman.dough.domain.Acct;
import norman.dough.domain.DataFile;
import norman.dough.exception.NotFoundException;
import norman.dough.exception.OptimisticLockingException;
import norman.dough.service.AcctService;
import norman.dough.service.DataFileService;
import norman.dough.web.view.DataFileEditForm;
import norman.dough.web.view.DataFileListForm;
import norman.dough.web.view.DataFileView;
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
public class DataFileController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataFileController.class);
    private static final String defaultSortColumn = "id";
    private static final String[] sortableColumns =
            {"originalFilename", "contentType", "size", "uploadTimestamp", "status", "ofxOrganization", "ofxFid",
                    "ofxBankId", "ofxAcctId", "ofxType", "acct"};
    @Autowired
    private DataFileService service;
    @Autowired
    private AcctService acctService;

    @GetMapping("/dataFileList")
    public String loadDataFileList(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortColumn", required = false, defaultValue = "originalFilename") String sortColumn,
            @RequestParam(value = "sortDirection", required = false, defaultValue = "ASC") Sort.Direction sortDirection,
            Model model) {

        // Convert sort column from string to an array of strings.
        String[] sortColumns = {defaultSortColumn};
        if (Arrays.asList(sortableColumns).contains(sortColumn)) {
            sortColumns = new String[]{sortColumn, defaultSortColumn};
        }

        // Get a page of records.
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sortDirection, sortColumns);
        Page<DataFile> page = service.findAll(pageable);

        // Display the page of records.
        DataFileListForm listForm = new DataFileListForm(page);
        model.addAttribute("listForm", listForm);
        return "dataFileList";
    }

    @GetMapping("/dataFile")
    public String loadDataFileView(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            DataFile entity = service.findById(id);
            DataFileView view = new DataFileView(entity);
            model.addAttribute("view", view);
            return "dataFileView";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Data file not found.");
            return "redirect:/dataFileList";
        }
    }

    @GetMapping("/dataFileEdit")
    public String loadDataFileEdit(@RequestParam(value = "id", required = false) Long id, Model model,
            RedirectAttributes redirectAttributes) {

        // If no id, add new record.
        if (id == null) {
            model.addAttribute("editForm", new DataFileEditForm());
            return "dataFileEdit";
        }

        // Otherwise, edit existing record.
        try {
            DataFile entity = service.findById(id);
            DataFileEditForm editForm = new DataFileEditForm(entity);
            model.addAttribute("editForm", editForm);
            return "dataFileEdit";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Data file not found.");
            return "redirect:/dataFileList";
        }
    }

    @PostMapping("/dataFileEdit")
    public String processDataFileEdit(@Valid @ModelAttribute("editForm") DataFileEditForm editForm,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "dataFileEdit";
        }

        // Convert form to entity.
        Long id = editForm.getId();
        try {
            editForm.setAcctService(acctService);
            DataFile entity = editForm.toEntity();

            // Save entity.
            DataFile save = service.save(entity);
            String successMessage = "Data file successfully added.";
            if (id != null) {
                successMessage = "Data file successfully updated.";
            }
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            redirectAttributes.addAttribute("id", save.getId());
            return "redirect:/dataFile?id={id}";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getEntityName() + " not found.");
            return "redirect:/dataFileList";
        } catch (OptimisticLockingException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Data file was updated by another user.");
            return "redirect:/dataFileList";
        }
    }

    @ModelAttribute("allAcct")
    public Iterable<Acct> loadAcctDropDown() {
        return acctService.findAll();
    }
}
