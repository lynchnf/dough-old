package name.mutant.dough.web.acct;

import name.mutant.dough.data.Acct;
import name.mutant.dough.data.AcctRepository;
import name.mutant.dough.data.AcctType;
import name.mutant.dough.data.Inst;
import name.mutant.dough.data.InstRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by lynchnf on 7/14/17.
 */
@Controller
public class AcctController {
    private static final Logger logger = LoggerFactory.getLogger(AcctController.class);
    private static final String defaultSortColumn = "id";
    // Does not include default sort column.
    private static final String[] sortableColumns = {"inst.organization", "name", "type", "acctNbr"};
    @Autowired
    private AcctRepository acctRepository;
    @Autowired
    private InstRepository instRepository;

    @GetMapping("/acctListAction")
    public String listGet(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                          @RequestParam(value = "sortColumn", required = false, defaultValue = "id") String sortColumn,
                          @RequestParam(value = "sortDirection", required = false, defaultValue = "ASC") Sort.Direction sortDirection,
                          Model model,
                          @RequestParam(value = "whereOrganization", required = false) String whereOrganization,
                          @RequestParam(value = "whereName", required = false) String whereName,
                          @RequestParam(value = "whereType", required = false) AcctType whereType,
                          @RequestParam(value = "whereAcctNbr", required = false) String whereAcctNbr) {
        logger.debug("Starting");
        return list(pageNumber, pageSize, sortColumn, sortDirection, model, whereOrganization, whereName, whereType, whereAcctNbr);
    }

    @PostMapping("/acctListAction")
    public String listPost(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                           @RequestParam(value = "sortColumn", required = false, defaultValue = "id") String sortColumn,
                           @RequestParam(value = "sortDirection", required = false, defaultValue = "ASC") Sort.Direction sortDirection,
                           Model model,
                           @RequestParam(value = "whereOrganization", required = false) String whereOrganization,
                           @RequestParam(value = "whereName", required = false) String whereName,
                           @RequestParam(value = "whereType", required = false) AcctType whereType,
                           @RequestParam(value = "whereAcctNbr", required = false) String whereAcctNbr) {
        logger.debug("Starting");
        return list(pageNumber, pageSize, sortColumn, sortDirection, model, whereOrganization, whereName, whereType, whereAcctNbr);
    }

    public String list(int pageNumber,
                       int pageSize,
                       String sortColumn,
                       Sort.Direction sortDirection,
                       Model model,
                       String whereOrganization,
                       String whereName,
                       AcctType whereType,
                       String whereAcctNbr) {
        String trimmedOrganization = StringUtils.trimToNull(whereOrganization);
        String trimmedName = StringUtils.trimToNull(whereName);
        String trimmedAcctNbr = StringUtils.trimToNull(whereAcctNbr);

        // Convert sort column from string to an array of strings.
        String[] sortColumns = {defaultSortColumn};
        if (Arrays.asList(sortableColumns).contains(sortColumn)) {
            sortColumns = new String[]{sortColumn, defaultSortColumn};
        }

        // Get a page of records.
        Pageable pageable = new PageRequest(pageNumber, pageSize, sortDirection, sortColumns);
        Page<Acct> page = null;
        if (trimmedOrganization != null && trimmedName != null && whereType != null && trimmedAcctNbr != null) {
            page = acctRepository.findByInstOrganizationContainingAndNameContainingAndTypeAndAcctNbrContaining
                    (trimmedOrganization, trimmedName, whereType, trimmedAcctNbr, pageable);
        } else if (trimmedOrganization != null && trimmedName != null && whereType != null && trimmedAcctNbr == null) {
            page = acctRepository.findByInstOrganizationContainingAndNameContainingAndType(trimmedOrganization,
                    trimmedName, whereType, pageable);
        } else if (trimmedOrganization != null && trimmedName != null && whereType == null && trimmedAcctNbr != null) {
            page = acctRepository.findByInstOrganizationContainingAndNameContainingAndAcctNbrContaining
                    (trimmedOrganization, trimmedName, trimmedAcctNbr, pageable);
        } else if (trimmedOrganization != null && trimmedName != null && whereType == null && trimmedAcctNbr == null) {
            page = acctRepository.findByInstOrganizationContainingAndNameContaining(trimmedOrganization, trimmedName,
                    pageable);
        } else if (trimmedOrganization != null && trimmedName == null && whereType != null && trimmedAcctNbr != null) {
            page = acctRepository.findByInstOrganizationContainingAndTypeAndAcctNbrContaining(trimmedOrganization,
                    whereType, trimmedAcctNbr, pageable);
        } else if (trimmedOrganization != null && trimmedName == null && whereType != null && trimmedAcctNbr == null) {
            page = acctRepository.findByInstOrganizationContainingAndType(trimmedOrganization, whereType, pageable);
        } else if (trimmedOrganization != null && trimmedName == null && whereType == null && trimmedAcctNbr != null) {
            page = acctRepository.findByInstOrganizationContainingAndAcctNbrContaining(trimmedOrganization,
                    trimmedAcctNbr, pageable);
        } else if (trimmedOrganization != null && trimmedName == null && whereType == null && trimmedAcctNbr == null) {
            page = acctRepository.findByInstOrganizationContaining(trimmedOrganization, pageable);
        } else if (trimmedOrganization == null && trimmedName != null && whereType != null && trimmedAcctNbr != null) {
            page = acctRepository.findByNameContainingAndTypeAndAcctNbrContaining(trimmedName, whereType,
                    trimmedAcctNbr, pageable);
        } else if (trimmedOrganization == null && trimmedName != null && whereType != null && trimmedAcctNbr == null) {
            page = acctRepository.findByNameContainingAndType(trimmedName, whereType, pageable);
        } else if (trimmedOrganization == null && trimmedName != null && whereType == null && trimmedAcctNbr != null) {
            page = acctRepository.findByNameContainingAndAcctNbrContaining(trimmedName, trimmedAcctNbr, pageable);
        } else if (trimmedOrganization == null && trimmedName != null && whereType == null && trimmedAcctNbr == null) {
            page = acctRepository.findByNameContaining(trimmedName, pageable);
        } else if (trimmedOrganization == null && trimmedName == null && whereType != null && trimmedAcctNbr != null) {
            page = acctRepository.findByTypeAndAcctNbrContaining(whereType, trimmedAcctNbr, pageable);
        } else if (trimmedOrganization == null && trimmedName == null && whereType != null && trimmedAcctNbr == null) {
            page = acctRepository.findByType(whereType, pageable);
        } else if (trimmedOrganization == null && trimmedName == null && whereType == null && trimmedAcctNbr != null) {
            page = acctRepository.findByAcctNbrContaining(trimmedAcctNbr, pageable);
        } else {
            page = acctRepository.findAll(pageable);
        }
        AcctListForm acctListForm = new AcctListForm(page, trimmedOrganization, trimmedName, whereType, trimmedAcctNbr);

        // Add objects to model and display the webpage.
        model.addAttribute("acctListForm", acctListForm);
        return "acctList";
    }

    @RequestMapping("/acctViewAction")
    public String view(@RequestParam(value = "id", required = true) long id, Model model) {
        logger.debug("Starting");
        Acct acct = acctRepository.findOne(id);
        model.addAttribute("acct", acct);
        return "acctView";
    }

    @GetMapping("/acctEditAction")
    public String loadEdit(@RequestParam(value = "id", required = true) long id, Model model) {
        logger.debug("Starting");
        Acct acct = acctRepository.findOne(id);
        AcctEditForm acctEditForm = new AcctEditForm(acct);
        Iterable<Inst> instIterable = instRepository.findAll();
        acctEditForm.getAllInsts().clear();
        for (Inst inst : instIterable) {
            acctEditForm.getAllInsts().add(inst);
        }
        model.addAttribute("acctEditForm", acctEditForm);
        return "acctEdit";
    }

    @PostMapping("/acctEditAction")
    public String processEdit(@ModelAttribute AcctEditForm acctEditForm) {
        logger.debug("Starting");
        Acct acct;
        if (acctEditForm.getId() == null) {
            acct = new Acct();
        } else {
            acct = acctRepository.findOne(acctEditForm.getId());
            acct.setVersion(acctEditForm.getVersion());
        }
        if (acctEditForm.getInstId() == null) {
            acct.setInst(null);
        } else {
            Inst inst = instRepository.findOne(acctEditForm.getInstId());
            acct.setInst(inst);
        }
        acct.setAcctNbr(acctEditForm.getAcctNbr());
        acct.setName(acctEditForm.getName());
        acct.setOfxBankId(acctEditForm.getOfxBankId());
        acct.setOfxAcctId(acctEditForm.getOfxAcctId());
        acct.setType(acctEditForm.getType());
        acct.setBeginDate(acctEditForm.getBeginDate());
        acct.setBeginBalance(acctEditForm.getBeginBalance());
        Acct savedAcct = acctRepository.save(acct);
        return "redirect:/acctListAction";
    }
}