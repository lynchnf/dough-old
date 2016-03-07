package name.mutant.dough.service;

import name.mutant.dough.domain.Payee;
import name.mutant.dough.service.filter.request.OrderByDirection;
import name.mutant.dough.service.filter.request.PayeeFilterRequest;
import name.mutant.dough.service.filter.request.PayeeOrderByField;
import name.mutant.dough.service.filter.response.PayeeFilterResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class PayeeServiceTest {
    private static final Long READ_PAYEE_ID = Long.valueOf(748);
    private static final String READ_PAYEE_NAME = "read dxk";
    private static final Long SAVE_PAYEE_ID = Long.valueOf(255);
    private static final String SAVE_PAYEE_NAME = "save jog";
    public static final Long[] FILTER_PAYEE_ID = {Long.valueOf(6861), Long.valueOf(8818), Long.valueOf(6414)};
    public static final String[] FILTER_PAYEE_NAME = {"filter lvy", "filter leo", "filter gsi"};

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

    @Test
    public void testFilterPayees() throws Exception {
        // Set up our filter request.
        PayeeFilterRequest request = new PayeeFilterRequest();
        request.setMax(3); // three records per page
        request.setFirst(3); // show second page
        request.setOrderByField(PayeeOrderByField.NAME); // sort by name
        request.setOrderByDirection(OrderByDirection.DESC); // sort backwards
        request.setWhereNameLike("filter"); // select only names containing "filter"

        PayeeFilterResponse response = PayeeService.filterPayees(request);
        // List<Payee> resultList = response.getResultList(); // DEBUG
        // for (int i = 0; i < resultList.size(); i++) { // DEBUG
        // System.out.println("resultList[" + i + "]=\"" + resultList.get(i).getId() + "\", \"" + resultList.get(i).getName() + "\""); // DEBUG
        // } // DEBUG

        // There should be ten records with names containing "filter".
        assertEquals(Long.valueOf(10), response.getCount());

        // We should fetch the fourth to sixth records in reverse name order.
        assertEquals(FILTER_PAYEE_ID.length, response.getResultList().size());
        for (int i = 0; i < response.getResultList().size(); i++) {
            assertEquals(FILTER_PAYEE_ID[i], response.getResultList().get(i).getId());
            assertEquals(FILTER_PAYEE_NAME[i], response.getResultList().get(i).getName());
        }
    }
}
