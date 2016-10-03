package name.mutant.dough.service;

import name.mutant.dough.DoughException;
import name.mutant.dough.domain.Acct;
import name.mutant.dough.domain.AcctType;
import name.mutant.dough.domain.Tran;
import name.mutant.dough.domain.TranType;
import name.mutant.dough.service.dto.CorrectAction;
import name.mutant.dough.service.dto.OfxAcct;
import name.mutant.dough.service.dto.OfxInst;
import name.mutant.dough.service.dto.OfxStmtTran;
import name.mutant.dough.service.response.OfxParseFileResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OfxService extends BaseService {
    private static final Logger LOG = LogManager.getLogger();

    private enum State {
        OFX, FI, BANKACCTFROM, CCACCTFROM, BANKTRANLIST, STMTTRN
    }

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

    private OfxService() {
    }

    public static OfxParseFileResponse parseFile(File importFile) throws DoughException {
        OfxParseFileResponse response = new OfxParseFileResponse();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(importFile));
            String line = reader.readLine();
            State state = State.OFX;
            while (line != null) {
                if (state == State.OFX) {
                    if (line.contains(FI)) {
                        state = State.FI;
                        response.setOfxInst(new OfxInst());
                    } else if (line.contains(FI_END)) {
                        BadToken(state, line);
                    } else if (line.contains(ORG)) {
                        BadToken(state, line);
                    } else if (line.contains(FID)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKACCTFROM)) {
                        state = State.BANKACCTFROM;
                        response.setOfxAcct(new OfxAcct());
                    } else if (line.contains(BANKACCTFROM_END)) {
                        BadToken(state, line);
                    } else if (line.contains(CCACCTFROM)) {
                        state = State.CCACCTFROM;
                        response.setOfxAcct(new OfxAcct());
                    } else if (line.contains(CCACCTFROM_END)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKID)) {
                        BadToken(state, line);
                    } else if (line.contains(ACCTID)) {
                        BadToken(state, line);
                    } else if (line.contains(ACCTTYPE)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKTRANLIST)) {
                        state = State.BANKTRANLIST;
                    } else if (line.contains(BANKTRANLIST_END)) {
                        BadToken(state, line);
                    } else if (line.contains(STMTTRN)) {
                        BadToken(state, line);
                    } else if (line.contains(STMTTRN_END)) {
                        BadToken(state, line);
                    } else if (line.contains(TRNTYPE)) {
                        BadToken(state, line);
                    } else if (line.contains(DTPOSTED)) {
                        BadToken(state, line);
                    } else if (line.contains(DTUSER)) {
                        BadToken(state, line);
                    } else if (line.contains(TRNAMT)) {
                        BadToken(state, line);
                    } else if (line.contains(FITID)) {
                        BadToken(state, line);
                    } else if (line.contains(SIC)) {
                        BadToken(state, line);
                    } else if (line.contains(CHECKNUM)) {
                        BadToken(state, line);
                    } else if (line.contains(CORRECTFITID)) {
                        BadToken(state, line);
                    } else if (line.contains(CORRECTACTION)) {
                        BadToken(state, line);
                    } else if (line.contains(NAME)) {
                        BadToken(state, line);
                    } else if (line.contains(CATEGORY)) {
                        BadToken(state, line);
                    } else if (line.contains(MEMO)) {
                        BadToken(state, line);
                    }
                } else if (state == State.FI) {
                    if (line.contains(FI)) {
                        BadToken(state, line);
                    } else if (line.contains(FI_END)) {
                        state = State.OFX;
                    } else if (line.contains(ORG)) {
                        String s = StringUtils.substringAfter(line, ORG);
                        response.getOfxInst().setOrganization(s);
                    } else if (line.contains(FID)) {
                        String s = StringUtils.substringAfter(line, FID);
                        response.getOfxInst().setFid(s);
                    } else if (line.contains(BANKACCTFROM)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKACCTFROM_END)) {
                        BadToken(state, line);
                    } else if (line.contains(CCACCTFROM)) {
                        BadToken(state, line);
                    } else if (line.contains(CCACCTFROM_END)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKID)) {
                        BadToken(state, line);
                    } else if (line.contains(ACCTID)) {
                        BadToken(state, line);
                    } else if (line.contains(ACCTTYPE)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKTRANLIST)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKTRANLIST_END)) {
                        BadToken(state, line);
                    } else if (line.contains(STMTTRN)) {
                        BadToken(state, line);
                    } else if (line.contains(STMTTRN_END)) {
                        BadToken(state, line);
                    } else if (line.contains(TRNTYPE)) {
                        BadToken(state, line);
                    } else if (line.contains(DTPOSTED)) {
                        BadToken(state, line);
                    } else if (line.contains(DTUSER)) {
                        BadToken(state, line);
                    } else if (line.contains(TRNAMT)) {
                        BadToken(state, line);
                    } else if (line.contains(FITID)) {
                        BadToken(state, line);
                    } else if (line.contains(SIC)) {
                        BadToken(state, line);
                    } else if (line.contains(CHECKNUM)) {
                        BadToken(state, line);
                    } else if (line.contains(CORRECTFITID)) {
                        BadToken(state, line);
                    } else if (line.contains(CORRECTACTION)) {
                        BadToken(state, line);
                    } else if (line.contains(NAME)) {
                        BadToken(state, line);
                    } else if (line.contains(CATEGORY)) {
                        BadToken(state, line);
                    } else if (line.contains(MEMO)) {
                        BadToken(state, line);
                    } else {
                        MissingToken(state, line);
                    }
                } else if (state == State.BANKACCTFROM) {
                    if (line.contains(FI)) {
                        BadToken(state, line);
                    } else if (line.contains(FI_END)) {
                        BadToken(state, line);
                    } else if (line.contains(ORG)) {
                        BadToken(state, line);
                    } else if (line.contains(FID)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKACCTFROM)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKACCTFROM_END)) {
                        state = State.OFX;
                    } else if (line.contains(CCACCTFROM)) {
                        BadToken(state, line);
                    } else if (line.contains(CCACCTFROM_END)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKID)) {
                        String s = StringUtils.substringAfter(line, BANKID);
                        response.getOfxAcct().setBankId(s);
                    } else if (line.contains(ACCTID)) {
                        String s = StringUtils.substringAfter(line, ACCTID);
                        response.getOfxAcct().setAcctId(s);
                    } else if (line.contains(ACCTTYPE)) {
                        String s = StringUtils.substringAfter(line, ACCTTYPE);
                        AcctType acctType = AcctType.valueOf(s);
                        response.getOfxAcct().setType(acctType);
                    } else if (line.contains(BANKTRANLIST)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKTRANLIST_END)) {
                        BadToken(state, line);
                    } else if (line.contains(STMTTRN)) {
                        BadToken(state, line);
                    } else if (line.contains(STMTTRN_END)) {
                        BadToken(state, line);
                    } else if (line.contains(TRNTYPE)) {
                        BadToken(state, line);
                    } else if (line.contains(DTPOSTED)) {
                        BadToken(state, line);
                    } else if (line.contains(DTUSER)) {
                        BadToken(state, line);
                    } else if (line.contains(TRNAMT)) {
                        BadToken(state, line);
                    } else if (line.contains(FITID)) {
                        BadToken(state, line);
                    } else if (line.contains(SIC)) {
                        BadToken(state, line);
                    } else if (line.contains(CHECKNUM)) {
                        BadToken(state, line);
                    } else if (line.contains(CORRECTFITID)) {
                        BadToken(state, line);
                    } else if (line.contains(CORRECTACTION)) {
                        BadToken(state, line);
                    } else if (line.contains(NAME)) {
                        BadToken(state, line);
                    } else if (line.contains(CATEGORY)) {
                        BadToken(state, line);
                    } else if (line.contains(MEMO)) {
                        BadToken(state, line);
                    } else {
                        MissingToken(state, line);
                    }
                } else if (state == State.CCACCTFROM) {
                    if (line.contains(FI)) {
                        BadToken(state, line);
                    } else if (line.contains(FI_END)) {
                        BadToken(state, line);
                    } else if (line.contains(ORG)) {
                        BadToken(state, line);
                    } else if (line.contains(FID)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKACCTFROM)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKACCTFROM_END)) {
                        BadToken(state, line);
                    } else if (line.contains(CCACCTFROM)) {
                        BadToken(state, line);
                    } else if (line.contains(CCACCTFROM_END)) {
                        state = State.OFX;
                    } else if (line.contains(BANKID)) {
                        BadToken(state, line);
                    } else if (line.contains(ACCTID)) {
                        String s = StringUtils.substringAfter(line, ACCTID);
                        response.getOfxAcct().setAcctId(s);
                    } else if (line.contains(ACCTTYPE)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKTRANLIST)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKTRANLIST_END)) {
                        BadToken(state, line);
                    } else if (line.contains(STMTTRN)) {
                        BadToken(state, line);
                    } else if (line.contains(STMTTRN_END)) {
                        BadToken(state, line);
                    } else if (line.contains(TRNTYPE)) {
                        BadToken(state, line);
                    } else if (line.contains(DTPOSTED)) {
                        BadToken(state, line);
                    } else if (line.contains(DTUSER)) {
                        BadToken(state, line);
                    } else if (line.contains(TRNAMT)) {
                        BadToken(state, line);
                    } else if (line.contains(FITID)) {
                        BadToken(state, line);
                    } else if (line.contains(SIC)) {
                        BadToken(state, line);
                    } else if (line.contains(CHECKNUM)) {
                        BadToken(state, line);
                    } else if (line.contains(CORRECTFITID)) {
                        BadToken(state, line);
                    } else if (line.contains(CORRECTACTION)) {
                        BadToken(state, line);
                    } else if (line.contains(NAME)) {
                        BadToken(state, line);
                    } else if (line.contains(CATEGORY)) {
                        BadToken(state, line);
                    } else if (line.contains(MEMO)) {
                        BadToken(state, line);
                    } else {
                        MissingToken(state, line);
                    }
                } else if (state == State.BANKTRANLIST) {
                    if (line.contains(FI)) {
                        BadToken(state, line);
                    } else if (line.contains(FI_END)) {
                        BadToken(state, line);
                    } else if (line.contains(ORG)) {
                        BadToken(state, line);
                    } else if (line.contains(FID)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKACCTFROM)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKACCTFROM_END)) {
                        BadToken(state, line);
                    } else if (line.contains(CCACCTFROM)) {
                        BadToken(state, line);
                    } else if (line.contains(CCACCTFROM_END)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKID)) {
                        BadToken(state, line);
                    } else if (line.contains(ACCTID)) {
                        BadToken(state, line);
                    } else if (line.contains(ACCTTYPE)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKTRANLIST)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKTRANLIST_END)) {
                        state = State.OFX;
                    } else if (line.contains(STMTTRN)) {
                        state = State.STMTTRN;
                        response.addOfxStmtTran(new OfxStmtTran());
                    } else if (line.contains(STMTTRN_END)) {
                        BadToken(state, line);
                    } else if (line.contains(TRNTYPE)) {
                        BadToken(state, line);
                    } else if (line.contains(DTPOSTED)) {
                        BadToken(state, line);
                    } else if (line.contains(DTUSER)) {
                        BadToken(state, line);
                    } else if (line.contains(TRNAMT)) {
                        BadToken(state, line);
                    } else if (line.contains(FITID)) {
                        BadToken(state, line);
                    } else if (line.contains(SIC)) {
                        BadToken(state, line);
                    } else if (line.contains(CHECKNUM)) {
                        BadToken(state, line);
                    } else if (line.contains(CORRECTFITID)) {
                        BadToken(state, line);
                    } else if (line.contains(CORRECTACTION)) {
                        BadToken(state, line);
                    } else if (line.contains(NAME)) {
                        BadToken(state, line);
                    } else if (line.contains(CATEGORY)) {
                        BadToken(state, line);
                    } else if (line.contains(MEMO)) {
                        BadToken(state, line);
                    }
                } else if (state == State.STMTTRN) {
                    if (line.contains(FI)) {
                        BadToken(state, line);
                    } else if (line.contains(FI_END)) {
                        BadToken(state, line);
                    } else if (line.contains(ORG)) {
                        BadToken(state, line);
                    } else if (line.contains(FID)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKACCTFROM)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKACCTFROM_END)) {
                        BadToken(state, line);
                    } else if (line.contains(CCACCTFROM)) {
                        BadToken(state, line);
                    } else if (line.contains(CCACCTFROM_END)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKID)) {
                        BadToken(state, line);
                    } else if (line.contains(ACCTID)) {
                        BadToken(state, line);
                    } else if (line.contains(ACCTTYPE)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKTRANLIST)) {
                        BadToken(state, line);
                    } else if (line.contains(BANKTRANLIST_END)) {
                        BadToken(state, line);
                    } else if (line.contains(STMTTRN)) {
                        BadToken(state, line);
                    } else if (line.contains(STMTTRN_END)) {
                        state = State.BANKTRANLIST;
                    } else if (line.contains(TRNTYPE)) {
                        String s = StringUtils.substringAfter(line, TRNTYPE);
                        TranType tranType = TranType.valueOf(s);
                        int idx = response.getOfxStmtTrans().size() - 1;
                        response.getOfxStmtTrans().get(idx).setType(tranType);
                    } else if (line.contains(DTPOSTED)) {
                        String s = StringUtils.substringAfter(line, DTPOSTED);
                        try {
                            Date d = DF.parse(s.substring(0, 14));
                            int idx = response.getOfxStmtTrans().size() - 1;
                            response.getOfxStmtTrans().get(idx).setPostDate(d);
                        } catch (ParseException e) {
                            String msg = "Error parsing post date in line=\"" + line + "\".";
                            LOG.error(msg, e);
                            throw new DoughException(msg, e);
                        }
                    } else if (line.contains(DTUSER)) {
                        String s = StringUtils.substringAfter(line, DTUSER);
                        try {
                            Date d = DF.parse(s.substring(0, 14));
                            int idx = response.getOfxStmtTrans().size() - 1;
                            response.getOfxStmtTrans().get(idx).setUserDate(d);
                        } catch (ParseException e) {
                            String msg = "Error parsing user date in line=\"" + line + "\".";
                            LOG.error(msg, e);
                            throw new DoughException(msg, e);
                        }
                    } else if (line.contains(TRNAMT)) {
                        String s = StringUtils.substringAfter(line, TRNAMT);
                        BigDecimal bd = new BigDecimal(s);
                        int idx = response.getOfxStmtTrans().size() - 1;
                        response.getOfxStmtTrans().get(idx).setAmount(bd);
                    } else if (line.contains(FITID)) {
                        String s = StringUtils.substringAfter(line, FITID);
                        int idx = response.getOfxStmtTrans().size() - 1;
                        response.getOfxStmtTrans().get(idx).setFitId(s);
                    } else if (line.contains(SIC)) {
                        String s = StringUtils.substringAfter(line, SIC);
                        int idx = response.getOfxStmtTrans().size() - 1;
                        response.getOfxStmtTrans().get(idx).setSic(s);
                    } else if (line.contains(CHECKNUM)) {
                        String s = StringUtils.substringAfter(line, CHECKNUM);
                        int idx = response.getOfxStmtTrans().size() - 1;
                        response.getOfxStmtTrans().get(idx).setCheckNumber(s);
                    } else if (line.contains(CORRECTFITID)) {
                        String s = StringUtils.substringAfter(line, CORRECTFITID);
                        int idx = response.getOfxStmtTrans().size() - 1;
                        response.getOfxStmtTrans().get(idx).setCorrectFitId(s);
                    } else if (line.contains(CORRECTACTION)) {
                        String s = StringUtils.substringAfter(line, CORRECTACTION);
                        CorrectAction correctAction = CorrectAction.valueOf(s);
                        int idx = response.getOfxStmtTrans().size() - 1;
                        response.getOfxStmtTrans().get(idx).setCorrectAction(correctAction);
                    } else if (line.contains(NAME)) {
                        String s = StringUtils.substringAfter(line, NAME);
                        int idx = response.getOfxStmtTrans().size() - 1;
                        response.getOfxStmtTrans().get(idx).setName(s);
                    } else if (line.contains(CATEGORY)) {
                        // ignore
                    } else if (line.contains(MEMO)) {
                        String s = StringUtils.substringAfter(line, MEMO);
                        int idx = response.getOfxStmtTrans().size() - 1;
                        response.getOfxStmtTrans().get(idx).setMemo(s);
                    } else {
                        MissingToken(state, line);
                    }
                } else {
                    BadState(state);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            String msg = "Error reading from file=\"" + importFile + "\".";
            LOG.error(msg, e);
            throw new DoughException(msg, e);
        }

        if (reader != null) {
            try {
                reader.close();
            } catch (IOException ignored) {
                String msg = "Ignoring IOException while closing file=\"" + importFile + "\".";
                LOG.warn(msg);
            }
        }
        return response;
    }

    public static Acct createAcctFromOfx(OfxInst ofxInst, OfxAcct ofxAcct) {
        Acct acct = new Acct();
        String organization = ofxInst.getOrganization();
        String ofxAcctId = ofxAcct.getAcctId();
        String suffix = ofxAcctId.substring(ofxAcctId.length() - 4);
        String name = organization + "-*" + suffix;
        acct.setAcctNbr(ofxAcctId);
        acct.setName(name);
        acct.setOrganization(organization);
        acct.setFid(ofxInst.getFid());
        acct.setOfxBankId(ofxAcct.getBankId());
        acct.setOfxAcctId(ofxAcctId);
        AcctType type = ofxAcct.getType() == null ? AcctType.CC : ofxAcct.getType();
        acct.setType(type);
        acct.setBeginBalance(BigDecimal.ZERO);
        return acct;
    }

    public static List<Tran> createTransFromOfx(Acct acct, List<OfxStmtTran> ofxStmtTrans) {
        List<Tran> trans = new ArrayList<>();
        for (OfxStmtTran ofxStmtTran : ofxStmtTrans) {
            if (!ofxStmtTran.getPostDate().before(acct.getBeginDate())) {
                Tran tran = new Tran();
                tran.setAcct(acct);
                tran.setType(ofxStmtTran.getType());
                tran.setPostDate(ofxStmtTran.getPostDate());
                tran.setUserDate(ofxStmtTran.getUserDate());
                tran.setAmount(ofxStmtTran.getAmount());
                tran.setFitId(ofxStmtTran.getFitId());
                tran.setSic(ofxStmtTran.getSic());
                tran.setCheckNumber(ofxStmtTran.getCheckNumber());
                tran.setName(ofxStmtTran.getName());
                tran.setMemo(ofxStmtTran.getMemo());
                trans.add(tran);
            }
        }
        return trans;
    }

    private static void BadState(State state) throws DoughException {
        String msg = "Invalid state=\"" + state + "\".";
        LOG.error(msg);
        throw new DoughException(msg);
    }

    private static void BadToken(State state, String line) throws DoughException {
        String msg = "Invalid token found: state=\"" + state + "\", line=\"" + line + "\".";
        LOG.error(msg);
        throw new DoughException(msg);
    }

    private static void MissingToken(State state, String line) throws DoughException {
        String msg = "No valid token found: state=\"" + state + "\", line=\"" + line + "\".";
        LOG.error(msg);
        throw new DoughException(msg);
    }
}
