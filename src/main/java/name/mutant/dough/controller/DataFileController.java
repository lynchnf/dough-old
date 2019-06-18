package name.mutant.dough.controller;

import name.mutant.dough.DoughNotFoundException;
import name.mutant.dough.domain.DataFile;
import name.mutant.dough.service.DataFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static name.mutant.dough.controller.MessagesConstants.NOT_FOUND_ERROR;

@Controller
public class DataFileController {
    private static final Logger logger = LoggerFactory.getLogger(DataFileController.class);
    @Autowired
    private DataFileService dataFileService;

    @RequestMapping("/dataFileList")
    public String loadList(Model model) {
        Iterable<DataFile> dataFiles = dataFileService.findAllDataFiles();
        model.addAttribute("dataFiles", dataFiles);
        return "dataFileList";
    }

    @RequestMapping("/dataFile")
    public String loadView(@RequestParam("dataFileId") Long dataFileId, Model model,
                           RedirectAttributes redirectAttributes) {
        try {
            DataFile dataFile = dataFileService.findDataFileById(dataFileId);
            model.addAttribute("dataFile", dataFile);
            return "dataFileView";
        } catch (DoughNotFoundException e) {
            String msg = String.format(NOT_FOUND_ERROR, "DataFile", dataFileId);
            logger.warn(msg, e);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/dataFileList";
        }
    }
}