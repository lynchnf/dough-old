package name.mutant.dough.service;

import name.mutant.dough.DoughException;
import name.mutant.dough.data.AcctType;
import name.mutant.dough.data.OfxFile;
import name.mutant.dough.data.OfxFileRepository;
import name.mutant.dough.data.OfxLine;
import name.mutant.dough.service.response.OfxAcct;
import name.mutant.dough.service.response.OfxInst;
import name.mutant.dough.service.response.OfxParseResponse;
import name.mutant.dough.service.response.OfxStmtTran;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lynchnf on 7/18/17.
 */
@Service
public class OfxService {
    private static final Logger logger = LoggerFactory.getLogger(OfxService.class);
    private static final String FI = "<FI>";
    private static final String FI_END = "</FI>";
    private static final String ORG = "<ORG>";
    private static final String FID = "<FID>";
    private static final String BANKACCTFROM = "<BANKACCTFROM>";
    private static final String BANKACCTFROM_END = "</BANKACCTFROM>";
    private static final String CCACCTFROM = "<CCACCTFROM>";
    private static final String CCACCTFROM_END = "</CCACCTFROM>";
    private static final String BANKID = "<BANKID>";
    private static final String ACCTID = "<ACCTID>";
    private static final String ACCTTYPE = "<ACCTTYPE>";
    private static final String BANKTRANLIST = "<BANKTRANLIST>";
    private static final String BANKTRANLIST_END = "</BANKTRANLIST>";
    private static final String STMTTRN = "<STMTTRN>";
    private static final String STMTTRN_END = "</STMTTRN>";
    private static final String TRNTYPE = "<TRNTYPE>";
    private static final String DTPOSTED = "<DTPOSTED>";
    private static final String DTUSER = "<DTUSER>";
    private static final String TRNAMT = "<TRNAMT>";
    private static final String FITID = "<FITID>";
    private static final String SIC = "<SIC>";
    private static final String CHECKNUM = "<CHECKNUM>";
    private static final String CORRECTFITID = "<CORRECTFITID>";
    private static final String CORRECTACTION = "<CORRECTACTION>";
    private static final String NAME = "<NAME>";
    private static final String CATEGORY = "<CATEGORY>";
    private static final String MEMO = "<MEMO>";
    private static final DateFormat DF = new SimpleDateFormat("yyyyMMddHHmmss");
    @Autowired
    OfxFileRepository ofxFileRepository;

    private static void BadState(State state) throws DoughException {
        String msg = "Invalid state=\"" + state + "\".";
        logger.error(msg);
        throw new DoughException(msg);
    }

    private static void BadToken(State state, String line) throws DoughException {
        String msg = "Invalid token found: state=\"" + state + "\", line=\"" + line + "\".";
        logger.error(msg);
        throw new DoughException(msg);
    }

    private static void MissingToken(State state, String line) throws DoughException {
        String msg = "No valid token found: state=\"" + state + "\", line=\"" + line + "\".";
        logger.error(msg);
        throw new DoughException(msg);
    }

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

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(uploadedFile.getInputStream()));
            String text = reader.readLine();
            while (text != null) {
                OfxLine ofxLine = new OfxLine();
                ofxLine.setOfxFile(ofxFile);
                ofxLine.setText(text);
                ofxFile.getOfxLines().add(ofxLine);
                text = reader.readLine();
            }
        } catch (IOException e) {
            String msg = "Error while reading uploaded file " + uploadedFile.getOriginalFilename() + " is empty.";
            throw new DoughException(msg, e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                    String msg = "Error ignored while closing uploaded file " + uploadedFile.getOriginalFilename() +
                            ".";
                    logger.warn(msg, ignored);
                }
            }
        }

        //        ofxFile.setProcessed(Boolean.FALSE);
        return ofxFileRepository.save(ofxFile);
    }

    public OfxParseResponse parse(Long ofxFileId) throws DoughException {
        OfxParseResponse response = new OfxParseResponse();
        OfxFile ofxFile = ofxFileRepository.findOne(ofxFileId);
        State state = State.OFX;
        for (OfxLine ofxLine : ofxFile.getOfxLines()) {
            if (state == State.OFX) {
                if (ofxLine.getText().contains(FI)) {
                    state = State.FI;
                    response.setOfxInst(new OfxInst());
                } else if (ofxLine.getText().contains(FI_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(ORG)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(FID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKACCTFROM)) {
                    state = State.BANKACCTFROM;
                    response.setOfxAcct(new OfxAcct());
                } else if (ofxLine.getText().contains(BANKACCTFROM_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CCACCTFROM)) {
                    state = State.CCACCTFROM;
                    response.setOfxAcct(new OfxAcct());
                } else if (ofxLine.getText().contains(CCACCTFROM_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(ACCTID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(ACCTTYPE)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKTRANLIST)) {
                    state = State.BANKTRANLIST;
                } else if (ofxLine.getText().contains(BANKTRANLIST_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(STMTTRN)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(STMTTRN_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(TRNTYPE)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(DTPOSTED)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(DTUSER)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(TRNAMT)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(FITID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(SIC)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CHECKNUM)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CORRECTFITID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CORRECTACTION)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(NAME)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CATEGORY)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(MEMO)) {
                    BadToken(state, ofxLine.getText());
                }
            } else if (state == State.FI) {
                if (ofxLine.getText().contains(FI)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(FI_END)) {
                    state = State.OFX;
                } else if (ofxLine.getText().contains(ORG)) {
                    String s = StringUtils.substringAfter(ofxLine.getText(), ORG);
                    response.getOfxInst().setOrganization(s);
                } else if (ofxLine.getText().contains(FID)) {
                    String s = StringUtils.substringAfter(ofxLine.getText(), FID);
                    response.getOfxInst().setFid(s);
                } else if (ofxLine.getText().contains(BANKACCTFROM)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKACCTFROM_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CCACCTFROM)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CCACCTFROM_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(ACCTID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(ACCTTYPE)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKTRANLIST)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKTRANLIST_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(STMTTRN)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(STMTTRN_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(TRNTYPE)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(DTPOSTED)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(DTUSER)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(TRNAMT)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(FITID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(SIC)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CHECKNUM)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CORRECTFITID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CORRECTACTION)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(NAME)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CATEGORY)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(MEMO)) {
                    BadToken(state, ofxLine.getText());
                } else {
                    MissingToken(state, ofxLine.getText());
                }
            } else if (state == State.BANKACCTFROM) {
                if (ofxLine.getText().contains(FI)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(FI_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(ORG)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(FID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKACCTFROM)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKACCTFROM_END)) {
                    state = State.OFX;
                } else if (ofxLine.getText().contains(CCACCTFROM)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CCACCTFROM_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKID)) {
                    String s = StringUtils.substringAfter(ofxLine.getText(), BANKID);
                    response.getOfxAcct().setBankId(s);
                } else if (ofxLine.getText().contains(ACCTID)) {
                    String s = StringUtils.substringAfter(ofxLine.getText(), ACCTID);
                    response.getOfxAcct().setAcctId(s);
                } else if (ofxLine.getText().contains(ACCTTYPE)) {
                    String s = StringUtils.substringAfter(ofxLine.getText(), ACCTTYPE);
                    AcctType acctType = AcctType.valueOf(s);
                    response.getOfxAcct().setType(acctType);
                } else if (ofxLine.getText().contains(BANKTRANLIST)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKTRANLIST_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(STMTTRN)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(STMTTRN_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(TRNTYPE)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(DTPOSTED)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(DTUSER)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(TRNAMT)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(FITID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(SIC)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CHECKNUM)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CORRECTFITID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CORRECTACTION)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(NAME)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CATEGORY)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(MEMO)) {
                    BadToken(state, ofxLine.getText());
                } else {
                    MissingToken(state, ofxLine.getText());
                }
            } else if (state == State.CCACCTFROM) {
                if (ofxLine.getText().contains(FI)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(FI_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(ORG)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(FID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKACCTFROM)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKACCTFROM_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CCACCTFROM)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CCACCTFROM_END)) {
                    state = State.OFX;
                } else if (ofxLine.getText().contains(BANKID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(ACCTID)) {
                    String s = StringUtils.substringAfter(ofxLine.getText(), ACCTID);
                    response.getOfxAcct().setAcctId(s);
                } else if (ofxLine.getText().contains(ACCTTYPE)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKTRANLIST)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKTRANLIST_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(STMTTRN)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(STMTTRN_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(TRNTYPE)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(DTPOSTED)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(DTUSER)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(TRNAMT)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(FITID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(SIC)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CHECKNUM)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CORRECTFITID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CORRECTACTION)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(NAME)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CATEGORY)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(MEMO)) {
                    BadToken(state, ofxLine.getText());
                } else {
                    MissingToken(state, ofxLine.getText());
                }
            } else if (state == State.BANKTRANLIST) {
                if (ofxLine.getText().contains(FI)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(FI_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(ORG)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(FID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKACCTFROM)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKACCTFROM_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CCACCTFROM)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CCACCTFROM_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(ACCTID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(ACCTTYPE)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKTRANLIST)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKTRANLIST_END)) {
                    state = State.OFX;
                } else if (ofxLine.getText().contains(STMTTRN)) {
                    state = State.STMTTRN;
                    response.addOfxStmtTran(new OfxStmtTran());
                } else if (ofxLine.getText().contains(STMTTRN_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(TRNTYPE)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(DTPOSTED)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(DTUSER)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(TRNAMT)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(FITID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(SIC)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CHECKNUM)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CORRECTFITID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CORRECTACTION)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(NAME)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CATEGORY)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(MEMO)) {
                    BadToken(state, ofxLine.getText());
                }
            } else if (state == State.STMTTRN) {
                if (ofxLine.getText().contains(FI)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(FI_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(ORG)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(FID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKACCTFROM)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKACCTFROM_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CCACCTFROM)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(CCACCTFROM_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(ACCTID)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(ACCTTYPE)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKTRANLIST)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(BANKTRANLIST_END)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(STMTTRN)) {
                    BadToken(state, ofxLine.getText());
                } else if (ofxLine.getText().contains(STMTTRN_END)) {
                    state = State.BANKTRANLIST;
                } else if (ofxLine.getText().contains(TRNTYPE)) {
                    String s = StringUtils.substringAfter(ofxLine.getText(), TRNTYPE);
                    int idx = response.getOfxStmtTrans().size() - 1;
                    response.getOfxStmtTrans().get(idx).setType(s);
                } else if (ofxLine.getText().contains(DTPOSTED)) {
                    String s = StringUtils.substringAfter(ofxLine.getText(), DTPOSTED);
                    try {
                        Date d = DF.parse(s.substring(0, 14));
                        int idx = response.getOfxStmtTrans().size() - 1;
                        response.getOfxStmtTrans().get(idx).setPostDate(d);
                    } catch (ParseException e) {
                        String msg = "Error parsing post date in ofxLine.getText()=\"" + ofxLine.getText() + "\".";
                        logger.error(msg, e);
                        throw new DoughException(msg, e);
                    }
                } else if (ofxLine.getText().contains(DTUSER)) {
                    String s = StringUtils.substringAfter(ofxLine.getText(), DTUSER);
                    try {
                        Date d = DF.parse(s.substring(0, 14));
                        int idx = response.getOfxStmtTrans().size() - 1;
                        response.getOfxStmtTrans().get(idx).setUserDate(d);
                    } catch (ParseException e) {
                        String msg = "Error parsing user date in ofxLine.getText()=\"" + ofxLine.getText() + "\".";
                        logger.error(msg, e);
                        throw new DoughException(msg, e);
                    }
                } else if (ofxLine.getText().contains(TRNAMT)) {
                    String s = StringUtils.substringAfter(ofxLine.getText(), TRNAMT);
                    BigDecimal bd = new BigDecimal(s);
                    int idx = response.getOfxStmtTrans().size() - 1;
                    response.getOfxStmtTrans().get(idx).setAmount(bd);
                } else if (ofxLine.getText().contains(FITID)) {
                    String s = StringUtils.substringAfter(ofxLine.getText(), FITID);
                    int idx = response.getOfxStmtTrans().size() - 1;
                    response.getOfxStmtTrans().get(idx).setFitId(s);
                } else if (ofxLine.getText().contains(SIC)) {
                    String s = StringUtils.substringAfter(ofxLine.getText(), SIC);
                    int idx = response.getOfxStmtTrans().size() - 1;
                    response.getOfxStmtTrans().get(idx).setSic(s);
                } else if (ofxLine.getText().contains(CHECKNUM)) {
                    String s = StringUtils.substringAfter(ofxLine.getText(), CHECKNUM);
                    int idx = response.getOfxStmtTrans().size() - 1;
                    response.getOfxStmtTrans().get(idx).setCheckNumber(s);
                } else if (ofxLine.getText().contains(CORRECTFITID)) {
                    String s = StringUtils.substringAfter(ofxLine.getText(), CORRECTFITID);
                    int idx = response.getOfxStmtTrans().size() - 1;
                    response.getOfxStmtTrans().get(idx).setCorrectFitId(s);
                } else if (ofxLine.getText().contains(CORRECTACTION)) {
                    String s = StringUtils.substringAfter(ofxLine.getText(), CORRECTACTION);
                    int idx = response.getOfxStmtTrans().size() - 1;
                    response.getOfxStmtTrans().get(idx).setCorrectAction(s);
                } else if (ofxLine.getText().contains(NAME)) {
                    String s = StringUtils.substringAfter(ofxLine.getText(), NAME);
                    int idx = response.getOfxStmtTrans().size() - 1;
                    response.getOfxStmtTrans().get(idx).setName(s);
                } else if (ofxLine.getText().contains(CATEGORY)) {
                    // ignore
                } else if (ofxLine.getText().contains(MEMO)) {
                    String s = StringUtils.substringAfter(ofxLine.getText(), MEMO);
                    int idx = response.getOfxStmtTrans().size() - 1;
                    response.getOfxStmtTrans().get(idx).setMemo(s);
                } else {
                    MissingToken(state, ofxLine.getText());
                }
            } else {
                BadState(state);
            }
        }
        return response;
    }

    private enum State {
        OFX, FI, BANKACCTFROM, CCACCTFROM, BANKTRANLIST, STMTTRN
    }
}