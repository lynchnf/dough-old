package name.mutant.dough.service;

import name.mutant.dough.DoughException;
import name.mutant.dough.domain.Acct;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.OptimisticLockException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AcctServiceTest {
    private static final Long READ_ACCT_ID = Long.valueOf(3317);
    private static final String READ_ACCT_NAME = "read ira";
    private static final Long SAVE_ACCT_ID = Long.valueOf(2401);
    private static final String SAVE_ACCT_NAME = "save uju";
    private static final String READ_FID = "1111";
    private static final String READ_OFX_ACCT_ID = "4094";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testReadAcct() throws Exception {
        // Verify that reading a non-existing acct returns null.
        Acct acct1 = AcctService.readAcct(Long.valueOf(9999));
        assertNull(acct1);

        // Verify reading a real acct.
        Acct acct2 = AcctService.readAcct(READ_ACCT_ID);
        assertNotNull(acct2);
        assertEquals(READ_ACCT_NAME, acct2.getName());
    }

    @Test
    public void testSaveAcct() throws Exception {
        // Start by verifying that our test acct exists.
        Acct acct1 = AcctService.readAcct(SAVE_ACCT_ID);
        assertEquals(SAVE_ACCT_NAME, acct1.getName());
        assertNotEquals(Integer.valueOf(0), acct1.getVersion());

        // Try updating the name with the wrong version.
        Acct acct2 = new Acct();
        acct2.setId(acct1.getId());
        acct2.setVersion(Integer.valueOf(0));
        String newName = StringUtils.replace(SAVE_ACCT_NAME, "save", "changed");
        acct2.setName(newName);
        acct2.setFid(acct1.getFid());
        acct2.setOfxAcctId(acct1.getOfxAcctId());
        boolean exceptionThrown = false;
        try {
            AcctService.saveAcct(acct2);
        } catch (Exception e) {
            exceptionThrown = true;
            assertTrue(e instanceof DoughException);
            assertTrue(e.getCause() instanceof OptimisticLockException);
        }
        assertTrue(exceptionThrown);

        // Try again with the right version.
        acct2.setVersion(acct1.getVersion());
        AcctService.saveAcct(acct2);

        // Verify that our test acct has really been changed.
        Acct acct3 = AcctService.readAcct(SAVE_ACCT_ID);
        assertEquals(newName, acct3.getName());
    }

    @Test
    public void testReadAcctByFidAndOfxAcctId() throws Exception {
        Acct acct = AcctService.readAcctByFidAndOfxAcctId(READ_FID, READ_OFX_ACCT_ID);
        assertNotNull(acct);
        assertEquals(READ_ACCT_NAME, acct.getName());
    }
}
