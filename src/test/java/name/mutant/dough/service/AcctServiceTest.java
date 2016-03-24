package name.mutant.dough.service;

import name.mutant.dough.DoughException;
import name.mutant.dough.domain.Acct;
import name.mutant.dough.domain.AcctType;
import name.mutant.dough.service.dto.AcctBalance;
import name.mutant.dough.service.filter.request.AcctFilterRequest;
import name.mutant.dough.service.filter.request.AcctOrderByField;
import name.mutant.dough.service.filter.request.OrderByDirection;
import name.mutant.dough.service.filter.response.AcctFilterResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.OptimisticLockException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AcctServiceTest {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final Long READ_ACCT_ID = Long.valueOf(3317);
    private static final String READ_ACCT_NAME = "read ira";
    private static final Long SAVE_ACCT_ID = Long.valueOf(2401);
    private static final String SAVE_ACCT_NAME = "save uju";
    private static final Long[] FILTER_ACCT_ID = {Long.valueOf(7949), Long.valueOf(6251), Long.valueOf(5621)};
    private static final String[] FILTER_ACCT_NAME = {"filter htv", "filter sds", "filter khv"};
    private static final String READ_FID = "1111";
    private static final String READ_OFX_ACCT_ID = "4094";
    private static final Long ACCT_BALANCE_ID = Long.valueOf(7399);
    private static final String ACCT_BALANCE_NAME = "zzz acct balance";
    private static final AcctType ACCT_BALANCE_TYPE = AcctType.CHECKING;
    private static final String ACCT_BALANCE_AMT = "7118.61";
    private static final String ACCT_BALANCE_DATE = "2015-06-09";

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
    public void testFilterAccts() throws Exception {
        // Set up our filter request.
        AcctFilterRequest request = new AcctFilterRequest();
        request.setMax(3); // three records per page
        request.setFirst(3); // show second page
        request.setOrderByField(AcctOrderByField.FID); // sort by fid
        request.setOrderByDirection(OrderByDirection.ASC); // sort fowards
        request.setWhereNameLike("filter"); // select only names containing "filter"
        AcctFilterResponse response = AcctService.filterAccts(request);
/*
        System.out.println("Test Filter Accts"); // DEBUG
        System.out.println("idx    id  name        fid   ofx acct"); // DEBUG
        List<Acct> resultList = response.getResultList(); // DEBUG
        for (int i = 0; i < resultList.size(); i++) { // DEBUG
            System.out.printf(" %2d  %4s  %-10s  %s  %s%n",
                    i,
                    resultList.get(i).getId(),
                    resultList.get(i).getName(),
                    resultList.get(i).getFid(),
                    resultList.get(i).getOfxAcctId()); // DEBUG
        } // DEBUG
*/

        // There should be ten records with names containing "filter".
        assertEquals(Long.valueOf(10), response.getCount());

        // We should fetch the fourth to sixth records in fid order.
        assertEquals(FILTER_ACCT_ID.length, response.getResultList().size());
        for (int i = 0; i < response.getResultList().size(); i++) {
            assertEquals(FILTER_ACCT_ID[i], response.getResultList().get(i).getId());
            assertEquals(FILTER_ACCT_NAME[i], response.getResultList().get(i).getName());
        }
    }

    @Test
    public void testReadAcctByFidAndOfxAcctId() throws Exception {
        Acct acct = AcctService.readAcctByFidAndOfxAcctId(READ_FID, READ_OFX_ACCT_ID);
        assertNotNull(acct);
        assertEquals(READ_ACCT_NAME, acct.getName());
    }

    @Test
    public void testGetAcctBalances() throws Exception {
        List<AcctBalance> resultList = AcctService.getAcctBalances();
/*
        System.out.println("Test Filter Accts"); // DEBUG
        System.out.println("idx    id  name              type        balance  last"); // DEBUG
        NumberFormat curFormat = NumberFormat.getCurrencyInstance(); // DEBUG
        for (int i = 0; i < resultList.size(); i++) { // DEBUG
            System.out.printf(" %2d  %4s  %-16s  %-8s  %9s  %-10s%n",
                    i,
                    resultList.get(i).getAcctId(),
                    resultList.get(i).getAcctName(),
                    resultList.get(i).getAcctType(),
                    curFormat.format(resultList.get(i).getBalance()),
                    resultList.get(i).getLastTranDate()); // DEBUG
        } // DEBUG
*/

        assertEquals(13, resultList.size());
        AcctBalance last = resultList.get(resultList.size() - 1);
        assertEquals(ACCT_BALANCE_ID, last.getAcctId());
        assertEquals(ACCT_BALANCE_NAME, last.getAcctName());
        assertEquals(ACCT_BALANCE_TYPE, last.getAcctType());
        assertEquals(ACCT_BALANCE_AMT, last.getBalance().toPlainString());
        assertEquals(ACCT_BALANCE_DATE, DATE_FORMAT.format(last.getLastTranDate()));
    }
}
