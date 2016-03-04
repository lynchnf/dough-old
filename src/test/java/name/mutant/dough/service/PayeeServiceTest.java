package name.mutant.dough.service;

import name.mutant.dough.domain.Payee;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class PayeeServiceTest {
    private static final Long READ_PAYEE_ID = Long.valueOf(748);
    private static final String READ_PAYEE_NAME = "read dxk";
    private static final Long SAVE_PAYEE_ID = Long.valueOf(255);
    private static final String SAVE_PAYEE_NAME = "save jog";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testReadPayee() throws Exception {
        Payee payee2 = PayeeService.readPayee(READ_PAYEE_ID);
        assertNotNull(payee2);
        assertEquals(READ_PAYEE_NAME, payee2.getName());
    }

    @Test
    public void testSavePayee() throws Exception {
        // Start by verifying that our test payee exists.
        Payee payee1 = PayeeService.readPayee(SAVE_PAYEE_ID);
        assertEquals(SAVE_PAYEE_NAME, payee1.getName());
        assertNotEquals(Integer.valueOf(0), payee1.getVersion());

        // Try updating the name.
        Payee payee2 = new Payee();
        payee2.setId(payee1.getId());
        payee2.setVersion(Integer.valueOf(0));
        String newName = StringUtils.replace(SAVE_PAYEE_NAME, "save", "changed");
        payee2.setName(newName);
        payee2.setVersion(payee1.getVersion());
        PayeeService.savePayee(payee2);

        // Verify that our test payee has really been changed.
        Payee payee3 = PayeeService.readPayee(SAVE_PAYEE_ID);
        assertEquals(newName, payee3.getName());
    }
}
