package name.mutant.dough.service;

import name.mutant.dough.domain.Acct;
import name.mutant.dough.domain.AcctType;
import name.mutant.dough.domain.Tran;
import name.mutant.dough.domain.TranType;
import name.mutant.dough.service.dto.OfxAcct;
import name.mutant.dough.service.dto.OfxInst;
import name.mutant.dough.service.dto.OfxStmtTran;
import name.mutant.dough.service.response.OfxParseFileResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class OfxServiceTest {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String IMPORT_PATH = "test1.qfx";
    private static final String IMPORT_FID = "1234";
    private static final String IMPORT_ACCT_ID = "456789012345";
    private static final String[] IMPORT_FIT_ID = {
            "1234567890123456789012345678901234",
            "1234567890123456789012345678901235",
            "1234567890123456789012345678901236"
    };
    private static final String CREATE_ORGANIZATION = "organization xaj";
    private static final String CREATE_FID = "8630";
    private static final String CREATE_BANK_ID = "8173";
    private static final String CREATE_ACCT_ID = "7864";
    private static final String CREATE_ACCT_BEGIN_DATE = "2015-01-01";
    private static final String CREATE_ACCT_BEGIN_AMOUNT = "1492.81";
    private static final TranType[] CREATE_TRAN_TYPE = {TranType.DEBIT, TranType.CREDIT, TranType.CHECK};
    private static final String[] CREATE_TRAN_POST_DATE = {"2014-12-01", "2015-01-01", "2015-02-01"};
    private static final String[] CREATE_TRAN_AMOUNT = {"-7.91", "33.22", "-54.79"};
    private static final String[] CREATE_TRAN_FIT_ID = {
            "297917520758524697055947975064",
            "401775334681683657509064047687",
            "987648631392245972797462435790"
    };
    private static final String[] CREATE_TRAN_CHECK_NUMBER = {null, null, "5696"};
    private static final String[] CREATE_TRAN_NAME = {"name cjf", "name ldd", "name wlh"};

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testParseFile() throws Exception {
        URL importUrl = Thread.currentThread().getContextClassLoader().getResource(IMPORT_PATH);
        File importFile = new File(importUrl.getPath());
        OfxParseFileResponse response = OfxService.parseFile(importFile);
        assertNotNull(response);
        assertEquals(IMPORT_FID, response.getOfxInst().getFid());
        assertEquals(IMPORT_ACCT_ID, response.getOfxAcct().getAcctId());

        // Verify that there is one (and only one) import transaction for each OfxOfxStmtTran in the results.
        List<String> importFitIds = new ArrayList<>(Arrays.asList(IMPORT_FIT_ID));
        for (OfxStmtTran ofxStmtTran : response.getOfxStmtTrans()) {
            assertTrue(importFitIds.remove(ofxStmtTran.getFitId()));
        }

        // ... and there are none left over.
        assertTrue(importFitIds.isEmpty());
    }

    @Test
    public void testCreateAcctFromOfx() throws Exception {
        Acct acct = getCreateTestAcct();
        assertNotNull(acct);
        assertEquals(CREATE_ACCT_ID, acct.getAcctNbr());
        assertEquals(CREATE_ORGANIZATION, acct.getOrganization());
        assertEquals(CREATE_FID, acct.getFid());
        assertEquals(CREATE_BANK_ID, acct.getOfxBankId());
        assertEquals(CREATE_ACCT_ID, acct.getOfxAcctId());
        assertEquals(AcctType.CHECKING, acct.getType());
        assertNull(acct.getBeginDate());
        assertEquals(BigDecimal.ZERO, acct.getBeginBalance());
    }

    @Test
    public void testCreateTransFromOfx() throws Exception {
        Acct acct = getCreateTestAcct();
        acct.setBeginDate(DATE_FORMAT.parse(CREATE_ACCT_BEGIN_DATE));
        acct.setBeginBalance(new BigDecimal(CREATE_ACCT_BEGIN_AMOUNT));
        List<OfxStmtTran> ofxStmtTrans = new ArrayList<>();
        for (int i = 0; i < CREATE_TRAN_TYPE.length; i++) {
            OfxStmtTran ofxStmtTran = new OfxStmtTran();
            ofxStmtTran.setType(CREATE_TRAN_TYPE[i]);
            ofxStmtTran.setPostDate(DATE_FORMAT.parse(CREATE_TRAN_POST_DATE[i]));
            ofxStmtTran.setAmount(new BigDecimal(CREATE_TRAN_AMOUNT[i]));
            ofxStmtTran.setFitId(CREATE_TRAN_FIT_ID[i]);
            ofxStmtTran.setCheckNumber(CREATE_TRAN_CHECK_NUMBER[i]);
            ofxStmtTran.setName(CREATE_TRAN_NAME[i]);
            ofxStmtTrans.add(ofxStmtTran);
        }
        List<Tran> trans = OfxService.createTransFromOfx(acct, ofxStmtTrans);
        assertNotNull(trans);

        // Verify that there is one (and only one) OfxOfxStmtTran for each Tran in the results.
        Map<String, Integer> createFitIdMap = new HashMap<>();
        for (int i = 0; i < CREATE_TRAN_FIT_ID.length; i++) {
            createFitIdMap.put(CREATE_TRAN_FIT_ID[i], i);
        }
        for (Tran tran : trans) {
            assertEquals(CREATE_FID, tran.getAcct().getFid());
            assertEquals(CREATE_ACCT_BEGIN_DATE, DATE_FORMAT.format(tran.getAcct().getBeginDate()));

            // Get the array index matching this tran.
            assertTrue(createFitIdMap.containsKey(tran.getFitId()));
            int i = createFitIdMap.get(tran.getFitId());
            assertEquals(CREATE_TRAN_TYPE[i], tran.getType());
            assertEquals(CREATE_TRAN_POST_DATE[i], DATE_FORMAT.format(tran.getPostDate()));
            assertEquals(CREATE_TRAN_AMOUNT[i], tran.getAmount().toPlainString());
            assertEquals(CREATE_TRAN_FIT_ID[i], tran.getFitId());
            assertEquals(CREATE_TRAN_CHECK_NUMBER[i], tran.getCheckNumber());
            assertEquals(CREATE_TRAN_NAME[i], tran.getName());

            // Transactions cannot be before the account begin date.
            assertFalse(tran.getPostDate().before(tran.getAcct().getBeginDate()));

            // Remove this transaction from map.
            createFitIdMap.remove(tran.getFitId());
        }

        // If there are any entries in the data arrays that are not in the result Tran list, verify they have a post date before the account begin date.
        for (String fitId : createFitIdMap.keySet()) {
            int idx = createFitIdMap.get(fitId);
            Date acctBeginDate = DATE_FORMAT.parse(CREATE_ACCT_BEGIN_DATE);
            Date postDate = DATE_FORMAT.parse(CREATE_TRAN_POST_DATE[idx]);
            assertTrue(postDate.before(acctBeginDate));
        }
    }

    private Acct getCreateTestAcct() {
        OfxInst ofxInst = new OfxInst();
        ofxInst.setOrganization(CREATE_ORGANIZATION);
        ofxInst.setFid(CREATE_FID);
        OfxAcct ofxAcct = new OfxAcct();
        ofxAcct.setBankId(CREATE_BANK_ID);
        ofxAcct.setAcctId(CREATE_ACCT_ID);
        ofxAcct.setType(AcctType.CHECKING);
        return OfxService.createAcctFromOfx(ofxInst, ofxAcct);
    }
}
