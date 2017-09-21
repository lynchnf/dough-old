package name.mutant.dough.web;

import name.mutant.dough.data.Acct;
import name.mutant.dough.data.AcctRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

/**
 * Created by lynchnf on 7/14/17.
 */
@Controller
public class AcctController {
    private static final Logger logger = LoggerFactory.getLogger(AcctController.class);
    private static final String defaultSortColumn = "id";
    // Does not include default sort column.
    private static final String[] sortableColumns = {"acctNbr", "inst.organization", "name", "ofxBankId",
            "ofxAcctId", "type", "beginDate", "beginBalance"};
    @Autowired
    private AcctRepository acctRepository;

    @RequestMapping("/acctList")
    public String list(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                       @RequestParam(value = "sortColumn", required = false, defaultValue = "id") String sortColumn,
                       @RequestParam(value = "sortDirection", required = false, defaultValue = "ASC") Sort.Direction
                                   sortDirection, Model model) {

        // Convert sort column from string to an array of strings.
        String[] sortColumns = {defaultSortColumn};
        if (Arrays.asList(sortableColumns).contains(sortColumn)) {
            sortColumns = new String[]{sortColumn, defaultSortColumn};
        }

        // Get a page of records.
        Pageable pageable = new PageRequest(pageNumber, pageSize, sortDirection, sortColumns);
        Page<Acct> page = acctRepository.findAll(pageable);
        AcctListForm acctListForm = new AcctListForm(page);

        // Add objects to model and display the webpage.
        model.addAttribute("acctListForm", acctListForm);
        return "acctList";
    }
}