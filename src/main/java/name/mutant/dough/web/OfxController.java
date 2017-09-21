package name.mutant.dough.web;

import name.mutant.dough.DoughException;
import name.mutant.dough.data.Acct;
import name.mutant.dough.data.AcctRepository;
import name.mutant.dough.data.Inst;
import name.mutant.dough.data.InstRepository;
import name.mutant.dough.data.OfxFile;
import name.mutant.dough.data.OfxFileRepository;
import name.mutant.dough.service.OfxService;
import name.mutant.dough.service.response.OfxParseResponse;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by lynchnf on 7/14/17.
 */
@Controller
public class OfxController {
    private static final Logger logger = LoggerFactory.getLogger(OfxController.class);
    private static final String defaultSortColumn = "id";
    // Does not include default sort column.
    private static final String[] sortableColumns = {"originalFilename", "contentType", "size", "uploadTimestamp"};
    @Autowired
    private AcctRepository acctRepository;
    @Autowired
    private InstRepository instRepository;
    @Autowired
    private OfxFileRepository ofxFileRepository;
    @Autowired
    private OfxService ofxService;

    @RequestMapping("ofxFileUpload")
    public String upload(@RequestParam("ofxFile") MultipartFile ofxFile) {
        OfxFile savedOfxFile = null;
        try {
            savedOfxFile = ofxService.upload(ofxFile);
            OfxParseResponse ofxParseResponse = ofxService.parse(savedOfxFile.getId());
            String fid = ofxParseResponse.getOfxInst().getFid();
            List<Inst> insts = instRepository.findByFid(fid);
            Inst inst = null;
            if (insts.isEmpty()) {
                Inst newInst = new Inst();
                newInst.setOrganization(ofxParseResponse.getOfxInst().getOrganization());
                newInst.setFid(fid);
                inst = instRepository.save(newInst);
            } else if (insts.size() == 1) {
                inst = insts.iterator().next();
            } else {
                String msg = "Unexpected Error: More than one Inst with fid=\"" + fid + "\".";
                throw new DoughException(msg);
            }
            String ofxAcctId = ofxParseResponse.getOfxAcct().getAcctId();
            List<Acct> accts = acctRepository.findByOfxAcctIdAndInstId(ofxAcctId, inst.getId());
            Acct acct = null;
            if (accts.isEmpty()) {
                Acct newAcct = new Acct();
                newAcct.setInst(inst);
                inst.getAccts().add(newAcct);
                newAcct.setAcctNbr(ofxAcctId);
                newAcct.setName(inst.getOrganization() + "-*" + ofxAcctId.substring(ofxAcctId.length() - 4));
                newAcct.setOfxBankId(ofxParseResponse.getOfxAcct().getBankId());
                newAcct.setOfxAcctId(ofxAcctId);
                newAcct.setType(ofxParseResponse.getOfxAcct().getType());
                newAcct.setBeginDate(new Date());
                newAcct.setBeginBalance(BigDecimal.ZERO);
                acct = acctRepository.save(newAcct);
            } else if (accts.size() == 1) {
                acct = accts.iterator().next();
            } else {
                String msg = "Unexpected Error: More than one Acct with fid=\"" + fid + "\" and acctId= \"" +
                        ofxAcctId + "\".";
                throw new DoughException(msg);
            }

        } catch (DoughException e) {
            String errorMessage = e.getMessage();
        }
        return "redirect:/ofxFileList";
    }

    @RequestMapping("/ofxFileList")
    public String list(@RequestParam(value = "whereOriginalFilenameContains", required = false) String
                                   whereOriginalFilenameContains, @RequestParam(value = "pageNumber", required =
            false, defaultValue = "0") int pageNumber, @RequestParam(value = "pageSize", required = false,
            defaultValue = "10") int pageSize, @RequestParam(value = "sortColumn", required = false, defaultValue =
            "id") String sortColumn, @RequestParam(value = "sortDirection", required = false, defaultValue = "ASC")
            Sort.Direction sortDirection, Model model) {

        // Convert sort column from string to an array of strings.
        String[] sortColumns = {defaultSortColumn};
        if (Arrays.asList(sortableColumns).contains(sortColumn)) {
            sortColumns = new String[]{sortColumn, defaultSortColumn};
        }

        // Get a page of records.
        Pageable pageable = new PageRequest(pageNumber, pageSize, sortDirection, sortColumns);
        String trimmedOriginalFilename = StringUtils.trimToNull(whereOriginalFilenameContains);
        Page<OfxFile> page;
        if (trimmedOriginalFilename == null) {
            page = ofxFileRepository.findAll(pageable);
        } else {
            page = ofxFileRepository.findByOriginalFilenameContaining(trimmedOriginalFilename, pageable);
        }
        OfxFileListForm ofxFileListForm = new OfxFileListForm(trimmedOriginalFilename, page);

        // Add objects to model and display the webpage.
        model.addAttribute("ofxFileListForm", ofxFileListForm);
        return "ofxFileList";
    }
}