package norman.dough.web;

import norman.dough.domain.DataFile;
import norman.dough.domain.DataFileStatus;
import norman.dough.domain.DataLine;
import norman.dough.exception.OptimisticLockingException;
import norman.dough.service.DataFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

@Controller
public class UploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);
    @Autowired
    private DataFileService dataFileService;

    @PostMapping("/dataUpload")
    public String processDataUpload(@RequestParam(value = "multipartFile") MultipartFile multipartFile, Model model,
            RedirectAttributes redirectAttributes) {
        if (multipartFile.isEmpty()) {
            // No need to log this. I press UPLOAD without selecting a file all the time.
            redirectAttributes.addFlashAttribute("errorMessage", "Upload file is empty or missing.");
            return "redirect:/";
        }

        // Try to upload the file and save it to the database.
        DataFile dataFile = new DataFile();
        dataFile.setOriginalFilename(multipartFile.getOriginalFilename());
        dataFile.setContentType(multipartFile.getContentType());
        dataFile.setSize(multipartFile.getSize());
        Date uploadTimestamp = new Date();
        dataFile.setUploadTimestamp(uploadTimestamp);
        dataFile.setStatus(DataFileStatus.UPLOADED);

        // Read the lines of the file.
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {
            String line;
            int seq = 0;
            while ((line = reader.readLine()) != null) {
                DataLine dataLine = new DataLine();
                dataLine.setDataFile(dataFile);
                dataLine.setSeq(seq++);
                dataLine.setText(line);
                dataFile.getDataLines().add(dataLine);
            }

            // Save the file.
            DataFile save = dataFileService.save(dataFile);
            String successMessage = "Data File successfully added.";
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            redirectAttributes.addAttribute("id", save.getId());
            return "redirect:/dataFile?id={id}";
        } catch (IOException e) {
            String msg = "Uploaded file could not be read.";
            LOGGER.error(msg, e);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/";
        } catch (OptimisticLockingException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Data File was updated by another user.");
            return "redirect:/";
        }
    }
}
