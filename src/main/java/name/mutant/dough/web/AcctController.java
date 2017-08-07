package name.mutant.dough.web;

import name.mutant.dough.data.Acct;
import name.mutant.dough.data.AcctRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * Created by lynchnf on 7/14/17.
 */
@Controller
public class AcctController {
    private static final Logger logger = LoggerFactory.getLogger(AcctController.class);
    private static final String[] sortableColumns = {"name"}; // Does not include default sort column.
    private static final String defaultSortColumn = "id";
    @Autowired
    private AcctRepository acctRepository;

    @RequestMapping("/acctList")
    public String list(@RequestParam(value = "whereNameContains", required = false) String whereNameContains,
                       @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                       @RequestParam(value = "sortColumn", required = false, defaultValue = "id") String sortColumn,
                       @RequestParam(value = "sortDirection", required = false, defaultValue = "ASC") String sortDirection,
                       @RequestParam(value = "errorMessage", required = false) String errorMessage,
                       @RequestParam(value = "successMessage", required = false) String successMessage,
                       Model model) {
        Messages messages = new Messages();
        if (StringUtils.isNotBlank(errorMessage))
            messages.getErrorMessages().add(errorMessage);
        if (StringUtils.isNotBlank(successMessage))
            messages.getSuccessMessages().add(successMessage);

        // Convert sort column from string to an array of strings.
        String[] sortColumns = {defaultSortColumn};
        if (Arrays.asList(sortableColumns).contains(sortColumn)) {
            sortColumns = new String[]{sortColumn, defaultSortColumn};
        }

        // Convert sort direction from string to enum. TODO Can we do this in the request parm?
        Sort.Direction direction = Sort.Direction.ASC;
        try {
            direction = Sort.Direction.valueOf(sortDirection);
        } catch (IllegalArgumentException ignored) {
            String msg = "Invalid value for sortDirection=\"" + sortDirection + "\".";
            logger.warn(msg, ignored);
        }

        // Get a page of records.
        Pageable pageable = new PageRequest(pageNumber, pageSize, direction, sortColumns);
        String trimmedName = StringUtils.trimToNull(whereNameContains);
        Page<Acct> page;
        if (trimmedName == null) {
            page = acctRepository.findAll(pageable);
        } else {
            page = acctRepository.findByNameContaining(trimmedName, pageable);
        }
        AcctListForm acctListForm = new AcctListForm(trimmedName, page);

        // Add objects to model and display the webpage.
        model.addAttribute("acctListForm", acctListForm);
        model.addAttribute("messages", messages);
        return "acctList";
    }

    @GetMapping("/acctEdit")
    public String loadEdit(@RequestParam(value = "id", required = false) Long id, Model model) {
        Messages messages = new Messages();

        //If this is an update, get the record.
        AcctEditForm acctEditForm = new AcctEditForm();
        if (id != null) {
            Acct acct = acctRepository.findOne(id);
            if (acct != null) {
                acctEditForm = new AcctEditForm(acct);
            } else {
                String msg = "Acct not found id=\"" + id + "\".";
                messages.getErrorMessages().add(msg);
            }
        }

        // Add objects to model and display the webpage.
        model.addAttribute("acctEditForm", acctEditForm);
        model.addAttribute("messages", messages);
        return "acctEdit";
    }

    @PostMapping("/acctEdit")
    public String processEdit(@ModelAttribute AcctEditForm acctEditForm, @ModelAttribute Messages messages) {
        messages.getErrorMessages().clear();
        messages.getSuccessMessages();

        // Validate data from screen.
        if (StringUtils.isBlank(acctEditForm.getName())) {
            String errorMessage = "Name may not be blank.";
            messages.getErrorMessages().add(errorMessage);
        }

        // If no errors, try to save.
        Acct savedAcct = null;
        if (messages.getErrorMessages().isEmpty()) {
            Acct acct = acctEditForm.toAcct();
            try {
                savedAcct = acctRepository.save(acct);
            } catch (ObjectOptimisticLockingFailureException e) {
                String msg = "Acct updated by another process, id=\"" + acct.getId() + "\".";
                messages.getErrorMessages().add(msg);
            }
        }

        // If no errors, display success message.
        if (messages.getErrorMessages().isEmpty()) {
            String successMessage = "Successfully saved Acct id=\"" + savedAcct.getId() + "\".";
            try {
                successMessage = URLEncoder.encode(successMessage, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("Error encoding parameter successMessage=\"" + successMessage + "\".", e);
            }
            return "redirect:/acctList?successMessage=" + successMessage;
        } else {
            return "acctEdit";
        }
    }
}