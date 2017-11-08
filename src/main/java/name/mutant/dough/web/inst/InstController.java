package name.mutant.dough.web.inst;

import name.mutant.dough.data.Inst;
import name.mutant.dough.data.InstRepository;
import name.mutant.dough.web.ListForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by lynchnf on 7/14/17.
 */
@Controller
public class InstController {
    private static final Logger logger = LoggerFactory.getLogger(InstController.class);
    private static final String defaultSortColumn = "id";
    @Autowired
    private InstRepository instRepository;

    @RequestMapping("/instListAction")
    public ModelAndView list() {
        logger.debug("start");

        int pageNumber = 0;
        int pageSize = 10;
        Sort.Direction sortDirection = Sort.Direction.ASC;
        String[] sortColumns = {defaultSortColumn};

        // Get a page of records.
        Pageable pageable = new PageRequest(pageNumber, pageSize, sortDirection, sortColumns);
        Page<Inst> page = instRepository.findAll(pageable);
        ListForm listForm = new ListForm(page);

        // Add objects to model and display the webpage.
        return new ModelAndView("instList", "listForm", listForm);
    }

    @GetMapping("/instEditAction")
    public ModelAndView loadEdit(Long id) {
        logger.debug("start");
        InstEditForm editForm = new InstEditForm();
        if (id != null) {
            Inst inst = instRepository.findOne(id);
            editForm.setId(inst.getId());
            editForm.setVersion(inst.getVersion());
            editForm.setOrganization(inst.getOrganization());
            editForm.setFid(inst.getFid());
        }
        return new ModelAndView("instEdit", "editForm", editForm);
    }

    @PostMapping("/instEditAction")
    public ModelAndView processEdit(@Valid InstEditForm editForm, BindingResult bindingResult) {
        logger.debug("start");
        logger.debug("editForm=" + editForm);
        logger.debug("bindingResult=" + bindingResult);
        if (bindingResult.hasErrors()) {
            return new ModelAndView("instEdit", "editForm", editForm);
        }
        Inst inst = new Inst();
        inst.setId(editForm.getId());
        inst.setVersion(editForm.getVersion());
        inst.setOrganization(editForm.getOrganization());
        inst.setFid(editForm.getFid());
        Inst save = instRepository.save(inst);
        return new ModelAndView("redirect:/instListAction");
    }
}