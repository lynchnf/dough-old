package name.mutant.dough.controller;

import name.mutant.dough.FakeDataUtil;
import name.mutant.dough.domain.Acct;
import name.mutant.dough.domain.Category;
import name.mutant.dough.domain.Pattern;
import name.mutant.dough.domain.Tran;
import name.mutant.dough.service.PatternService;
import name.mutant.dough.service.TranService;
import org.hamcrest.core.StringContains;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(TranController.class)
public class TranControllerTest {
    private Long tran1Id;
    private Acct acct1;
    private Tran tran1;
    private String tran1Name;
    private Long category1Id;
    private Category category1;
    private Pattern pattern1;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TranService tranService;
    @MockBean
    private PatternService patternService;

    @Before
    public void setUp() throws Exception {
        tran1Id = Long.valueOf(1);
        acct1 = FakeDataUtil.buildAcct(2);
        tran1 = FakeDataUtil.buildTran(acct1, tran1Id, null);
        tran1Name = tran1.getName();
        category1Id = Long.valueOf(3);
        category1 = FakeDataUtil.buildCategory(category1Id);
        pattern1 = FakeDataUtil.buildPattern(category1, 4);
    }

    @Test
    public void loadView() throws Exception {
        Mockito.when(tranService.findTranById(tran1Id)).thenReturn(tran1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tran").param("tranId", "1");
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.view().name("tranView"));
        resultActions.andExpect(MockMvcResultMatchers.content().string(StringContains.containsString(tran1Name)));
    }

    @Test
    public void assignCategories() throws Exception {
        pattern1.setTranName(tran1Name.substring(0, 1) + ".*");
        List<Pattern> patterns1 = patternService.findAllPatterns();
        patterns1.add(pattern1);
        List<Tran> trans1 = tranService.findAllNonAssigned();
        trans1.add(tran1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tranAssign");
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        resultActions.andExpect(MockMvcResultMatchers.status().isFound());
        resultActions.andExpect(MockMvcResultMatchers.view().name("redirect:/"));
        // TODO Verify that the tran that was saved now has a category id assigned to it.
    }
}