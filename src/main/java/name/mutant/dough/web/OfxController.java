package name.mutant.dough.web;

import name.mutant.dough.DoughException;
import name.mutant.dough.data.OfxFile;
import name.mutant.dough.service.OfxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by lynchnf on 7/18/17.
 */
@Controller
public class OfxController {
    private static final Logger logger = LoggerFactory.getLogger(OfxController.class);
    @Autowired
    private OfxService ofxService;

    @GetMapping("/ofxList")
    public String list(Model model) {
        Messages messages = new Messages();

        model.addAttribute("messages", messages);
        return "ofxList";
    }

    @PostMapping("/ofxUpload")
    public String upload(@RequestParam("ofxFile") MultipartFile ofxFile) {
        OfxFile savedOfxFile = null;
        try {
            savedOfxFile = ofxService.upload(ofxFile);
            String successMessage = "Successfully saved OfxFile id=\"" + savedOfxFile.getId() + "\".";
        } catch (DoughException e) {
            String errorMessage = e.getMessage();
        }
        return "redirect:/ofxUpload";
    }
}