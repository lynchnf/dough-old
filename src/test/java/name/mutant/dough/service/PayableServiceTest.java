package name.mutant.dough.service;

import name.mutant.dough.domain.Payable;
import name.mutant.dough.domain.Payee;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class PayableServiceTest {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final Long READ_PAYABLE_ID = Long.valueOf(5376);
    private static final String READ_PAYABLE_MEMO = "read ice";
    private static final Long SAVE_PAYABLE_ID = Long.valueOf(1897);
    private static final String SAVE_PAYABLE_MEMO = "save ehm";
    private static final Long SCHEDULE_PAYEE_ID = new Long(6962);
    private static final String SCHEDULE_CRON_EXPRESSION = "0 0 0 20 * ?";
    private static final String SCHEDULE_EST_AMOUNT = "38.59";
    private static final String SCHEDULE_TODAY = "2016-02-15";
    private static final String[] SCHEDULE_EST_DUE_DATE = {"2016-02-20", "2016-03-20"};

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testReadPayable() throws Exception {
        Payable payable = PayableService.readPayable(READ_PAYABLE_ID);
        assertNotNull(payable);
        assertEquals(READ_PAYABLE_MEMO, payable.getMemo());
    }

    @Test
    public void testSavePayable() throws Exception {
        // Start by verifying that our test payable exists.
        Payable payable1 = PayableService.readPayable(SAVE_PAYABLE_ID);
        assertEquals(SAVE_PAYABLE_MEMO, payable1.getMemo());
        assertNotEquals(Integer.valueOf(0), payable1.getVersion());

        // Try updating the memo.
        Payable payable2 = new Payable();
        payable2.setId(payable1.getId());
        payable2.setVersion(payable1.getVersion());

        Long payeeId = payable1.getPayee().getId();
        Payee payee = PayeeService.readPayee(payeeId);
        payable2.setPayee(payee);

        String newMemo = StringUtils.replace(SAVE_PAYABLE_MEMO, "save", "changed");
        payable2.setMemo(newMemo);
        PayableService.savePayable(payable2);

        // Verify that our test payable has really been changed.
        Payable payable3 = PayableService.readPayable(SAVE_PAYABLE_ID);
        assertEquals(newMemo, payable3.getMemo());
    }

    @Test
    public void testCreateEstimatedPayables() throws Exception {
        // Verify our account exists and has the schedule values we expect.
        Payee payee1 = PayeeService.readPayee(SCHEDULE_PAYEE_ID);
        assertNotNull(payee1);
        assertEquals(SCHEDULE_CRON_EXPRESSION, payee1.getCronExpression());
        assertEquals(SCHEDULE_EST_DUE_DATE.length, payee1.getNbrEstToCreate().intValue());
        assertEquals(SCHEDULE_EST_AMOUNT, payee1.getEstAmount().toPlainString());

        // Verify a payable already exists in the "future" and has the due date we expect.
        List<Payable> payables1 = PayableService.readAllPayablesForPayee(SCHEDULE_PAYEE_ID);
        assertNotNull(payables1);
        assertEquals(1, payables1.size());
        Payable existingPayable = payables1.iterator().next();
        assertNotNull(existingPayable);
        assertEquals(SCHEDULE_EST_DUE_DATE[0], DATE_FORMAT.format(existingPayable.getEstDueDate()));

        // Save the id and version so we verify this payable does not change.
        Long existingPayableId = existingPayable.getId();
        Integer existingPayableVersion = existingPayable.getVersion();

        // Fake "today" to make this easier to test.
        Date today = DATE_FORMAT.parse(SCHEDULE_TODAY);
        PayableService.createEstimatedPayables(SCHEDULE_PAYEE_ID, today);

        // Verify we now have 2 payables.
        List<Payable> payables2 = PayableService.readAllPayablesForPayee(SCHEDULE_PAYEE_ID);
        assertNotNull(payables2);
        assertEquals(SCHEDULE_EST_DUE_DATE.length, payables2.size());

        for (Payable payable : payables2) {
            // Verify the old one did not change.
            if (payable.getId().equals(existingPayableId)) {
                assertEquals(existingPayableVersion, payable.getVersion());
            } else {
                // Verify the new one has the expected values.
                assertEquals(SCHEDULE_EST_DUE_DATE[1], DATE_FORMAT.format(payable.getEstDueDate()));
                assertEquals(SCHEDULE_EST_AMOUNT, payable.getEstAmount().toPlainString());
            }
        }
    }
}
