package name.mutant.dough.service;

import name.mutant.dough.domain.Acct;
import name.mutant.dough.domain.Tran;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TranServiceTest {
    private static final Long READ_TRAN_ID = Long.valueOf(5144);
    private static final String READ_TRAN_NAME = "read tki";
    private static final Long SAVE_TRAN_ID = Long.valueOf(8909);
    private static final String SAVE_TRAN_NAME = "save jma";
    private static final Long READ_ACCT_ID = Long.valueOf(3317);
    private static final String READ_FITID = "2714";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testReadTran() throws Exception {
        Tran tran = TranService.readTran(READ_TRAN_ID);
        assertNotNull(tran);
        assertEquals(READ_TRAN_NAME, tran.getName());
    }

    @Test
    public void testSaveTran() throws Exception {
        // Start by verifying that our test tran exists.
        Tran tran1 = TranService.readTran(SAVE_TRAN_ID);
        assertEquals(SAVE_TRAN_NAME, tran1.getName());
        assertNotEquals(Integer.valueOf(0), tran1.getVersion());

        // Try updating the name.
        Tran tran2 = new Tran();
        tran2.setId(tran1.getId());
        tran2.setVersion(tran1.getVersion());

        Long acctId = tran1.getAcct().getId();
        Acct newAcct = AcctService.readAcct(acctId);
        tran2.setAcct(newAcct);

        tran2.setFitId(tran1.getFitId());
        String newName = StringUtils.replace(SAVE_TRAN_NAME, "save", "changed");
        tran2.setName(newName);
        TranService.saveTran(tran2);

        // Verify that our test tran has really been changed.
        Tran tran3 = TranService.readTran(SAVE_TRAN_ID);
        assertEquals(newName, tran3.getName());
    }

    @Test
    public void testReadTranByAcctAndFitId() throws Exception {
        Tran tran1 = TranService.readTranByAcctAndFitId(READ_ACCT_ID, READ_FITID);
        assertNotNull(tran1);
        assertEquals(READ_TRAN_NAME, tran1.getName());
    }
}
