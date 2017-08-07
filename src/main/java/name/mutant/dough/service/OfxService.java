package name.mutant.dough.service;

import name.mutant.dough.DoughException;
import name.mutant.dough.data.OfxFile;
import name.mutant.dough.data.OfxFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * Created by lynchnf on 7/18/17.
 */
@Service
public class OfxService {
    private static final Logger logger = LoggerFactory.getLogger(OfxService.class);
    @Autowired
    OfxFileRepository ofxFileRepository;
    public OfxFile upload(MultipartFile uploadedFile) throws DoughException {
        if (uploadedFile.isEmpty()) {
            String msg = "Uploaded file " + uploadedFile.getOriginalFilename() + " is empty.";
            throw new DoughException(msg);
        }
        OfxFile ofxFile = new OfxFile();
        ofxFile.setOriginalFilename(uploadedFile.getOriginalFilename());
        ofxFile.setContentType(uploadedFile.getContentType());
        ofxFile.setSize(uploadedFile.getSize());
        ofxFile.setUploadTimestamp(new Date());
        ofxFile.setProcessed(Boolean.FALSE);
        return ofxFileRepository.save(ofxFile);

    }
}