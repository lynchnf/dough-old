package name.mutant.dough.service;

import name.mutant.dough.domain.Payable;
import name.mutant.dough.domain.Payee;
import name.mutant.dough.service.filter.request.OrderByDirection;
import name.mutant.dough.service.filter.request.PayableFilterRequest;
import name.mutant.dough.service.filter.request.PayableOrderByField;
import name.mutant.dough.service.filter.response.PayableFilterResponse;
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
import static org.junit.Assert.assertNull;

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
    private static final Long[] FILTER_PAYABLE_1_ID = {Long.valueOf(9912), Long.valueOf(5579), Long.valueOf(1171)};
    private static final String[] FILTER_PAYABLE_1_MEMO = {"filter ufv", "filter nwc", "filter ssh"};
    private static final Long[] FILTER_PAYABLE_2_ID = {
            Long.valueOf(1483),
            Long.valueOf(9912), +
            Long.valueOf(1171), +
            Long.valueOf(5579), +
            Long.valueOf(429), +
            Long.valueOf(5878), +
            Long.valueOf(334), +
            Long.valueOf(7655), +
            Long.valueOf(5735)};
    private static final String[] FILTER_PAYABLE_2_MEMO = {
            "filter cta",
            "filter ufv",
            "filter ssh",
            "filter nwc",
            "filter tnf",
            "filter wep",
            "filter drt",
            "filter fsw",
            "filter iwr"};

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

    @Test
    public void testFilterPayables1() throws Exception {
        // Set up our filter request.
        PayableFilterRequest request = new PayableFilterRequest();
        request.setMax(3); // three records per page
        request.setFirst(3); // show second page
        request.setOrderByField(PayableOrderByField.DUE_DATE); // sort by due date
        request.setOrderByDirection(OrderByDirection.ASC); // sort fowards
        request.setWhereMemoLike("filter"); // select only memos containing "filter"
        request.setWherePaid(Boolean.FALSE); // select only unpaid

        PayableFilterResponse response = PayableService.filterPayables(request);
        List<Payable> resultList = response.getResultList(); // DEBUG
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < resultList.size(); i++) { // DEBUG
            System.out.println("resultList[" + i + "]=\"" +
                    resultList.get(i).getId() + "\", \"" +
                    resultList.get(i).getPayee().getName() + "\", \"" +
                    df.format(resultList.get(i).getEstDueDate()) + "\", \"" +
                    resultList.get(i).getEstAmount() + "\", \"" +
                    (resultList.get(i).getActDueDate() == null ? null :
                            df.format(resultList.get(i).getActDueDate())) + "\", \"" +
                    resultList.get(i).getActAmount() + "\", \"" +
                    resultList.get(i).getMemo() + "\", \"" +
                    resultList.get(i).getPaidDate() + "\""); // DEBUG
        } // DEBUG

        // There should be ten unpaid records with names containing "filter".
        assertEquals(Long.valueOf(10), response.getCount());

        // We should fetch the fourth to sixth records in due date order.
        assertEquals(FILTER_PAYABLE_1_ID.length, response.getResultList().size());
        for (int i = 0; i < response.getResultList().size(); i++) {
            assertEquals(FILTER_PAYABLE_1_ID[i], response.getResultList().get(i).getId());
            assertEquals(FILTER_PAYABLE_1_MEMO[i], response.getResultList().get(i).getMemo());
            assertNull(response.getResultList().get(i).getPaidDate());
        }
    }

    @Test
    public void testFilterPayables2() throws Exception {
        // Set up our filter request.
        PayableFilterRequest request = new PayableFilterRequest();
        request.setMax(-1); // Get all records

        request.setOrderByField(PayableOrderByField.PAYEE_NAME); // sort by payee name
        request.setOrderByDirection(OrderByDirection.ASC); // sort fowards
        request.setWhereMemoLike("filter"); // select only memos containing "filter"
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        request.setWhereDueDateAfter(df.parse("2015-12-31")); // due date between Dec 31st.
        request.setWhereDueDateBefore(df.parse("2016-02-01")); // ... and Feb 1st.

        PayableFilterResponse response = PayableService.filterPayables(request);
        List<Payable> resultList = response.getResultList(); // DEBUG
        for (int i = 0; i < resultList.size(); i++) { // DEBUG
            System.out.println("resultList[" + i + "]=\"" +
                    resultList.get(i).getId() + "\", \"" +
                    resultList.get(i).getPayee().getName() + "\", \"" +
                    df.format(resultList.get(i).getEstDueDate()) + "\", \"" +
                    resultList.get(i).getEstAmount() + "\", \"" +
                    (resultList.get(i).getActDueDate() == null ? null :
                            df.format(resultList.get(i).getActDueDate())) + "\", \"" +
                    resultList.get(i).getActAmount() + "\", \"" +
                    resultList.get(i).getMemo() + "\", \"" +
                    resultList.get(i).getPaidDate() + "\""); // DEBUG
        } // DEBUG

        // There should be nine records in January with names containing "filter".
        assertEquals(Long.valueOf(9), response.getCount());

        // We should fetch the records in payee name order.
        assertEquals(FILTER_PAYABLE_2_ID.length, response.getResultList().size());
        for (int i = 0; i < response.getResultList().size(); i++) {
            assertEquals(FILTER_PAYABLE_2_ID[i], response.getResultList().get(i).getId());
            assertEquals(FILTER_PAYABLE_2_MEMO[i], response.getResultList().get(i).getMemo());
        }
    }
}
