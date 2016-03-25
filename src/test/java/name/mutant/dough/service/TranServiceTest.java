package name.mutant.dough.service;

import name.mutant.dough.domain.Acct;
import name.mutant.dough.domain.Tran;
import name.mutant.dough.service.filter.request.OrderByDirection;
import name.mutant.dough.service.filter.request.TranFilterRequest;
import name.mutant.dough.service.filter.request.TranOrderByField;
import name.mutant.dough.service.filter.response.TranFilterResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class TranServiceTest {
    private static final Long READ_TRAN_ID = Long.valueOf(5144);
    private static final String READ_TRAN_NAME = "read tki";
    private static final Long SAVE_TRAN_ID = Long.valueOf(8909);
    private static final String SAVE_TRAN_NAME = "save jma";
    private static final Long READ_ACCT_ID = Long.valueOf(3317);
    private static final Long[] FILTER_TRAN_ID = {Long.valueOf(4181), Long.valueOf(7915), Long.valueOf(2198)};
    private static final String[] FILTER_TRAN_NAME = {"filter glv", "filter hxb", "filter iqi"};
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
    public void testFilterTrans() throws Exception {
        TranFilterRequest request = new TranFilterRequest();
        request.setMax(3); // three records per page
        request.setFirst(3); // show second page
        request.setOrderByField(TranOrderByField.POST_DATE); // sort by due date
        request.setOrderByDirection(OrderByDirection.DESC); // sort backwards
        request.setWhereNameLike("filter"); // select only names containing "filter"
        TranFilterResponse response = TranService.filterTrans(request);
/*
        System.out.println("Test Filter Trans"); // DEBUG
        System.out.println("idx    id  name        post"); // DEBUG
        List<Tran> resultList = response.getResultList(); // DEBUG
        for (int i = 0; i < resultList.size(); i++) { // DEBUG
            System.out.printf(" %2d  %4s  %-10s  %tF%n",
                    i,
                    resultList.get(i).getId(),
                    resultList.get(i).getName(),
                    resultList.get(i).getPostDate()); // DEBUG
        } // DEBUG
*/

        // There should be ten unpaid records with names containing "filter".
        assertEquals(Long.valueOf(10), response.getCount());

        // We should fetch the fourth to sixth records in due date order.
        assertEquals(FILTER_TRAN_ID.length, response.getResultList().size());
        for (int i = 0; i < response.getResultList().size(); i++) {
            assertEquals(FILTER_TRAN_ID[i], response.getResultList().get(i).getId());
            assertEquals(FILTER_TRAN_NAME[i], response.getResultList().get(i).getName());
        }
    }

    @Test
    public void testReadTranByAcctAndFitId() throws Exception {
        Tran tran1 = TranService.readTranByAcctAndFitId(READ_ACCT_ID, READ_FITID);
        assertNotNull(tran1);
        assertEquals(READ_TRAN_NAME, tran1.getName());
    }
}
