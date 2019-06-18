package name.mutant.dough.service;

import name.mutant.dough.DoughOptimisticLockingException;
import name.mutant.dough.FakeDataUtil;
import name.mutant.dough.domain.Category;
import name.mutant.dough.domain.Pattern;
import name.mutant.dough.repository.PatternRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class PatternServiceTest {
    private Category category1;
    private Pattern pattern1;
    private Integer pattern1Seq;

    @TestConfiguration
    static class PatternServiceTestConfiguration {
        @Bean
        public PatternService patternService() {
            return new PatternService();
        }
    }

    @Autowired
    private PatternService patternService;
    @MockBean
    private PatternRepository patternRepository;

    @Before
    public void setUp() throws Exception {
        category1 = FakeDataUtil.buildCategory(1);
        pattern1 = FakeDataUtil.buildPattern(category1, 2);
        pattern1Seq = pattern1.getSeq();
    }

    @Test
    public void findAllPatterns() {
        Mockito.when(patternRepository.findAllByOrderBySeq()).thenReturn(new ArrayList<>());
        Iterable<Pattern> patterns = patternService.findAllPatterns();
        assertFalse(patterns.iterator().hasNext());
    }

    @Test
    public void saveAllPatterns() throws Exception {
        List<Pattern> patterns1 = new ArrayList<>();
        patterns1.add(pattern1);
        Mockito.when(patternRepository.saveAll(Mockito.anyIterable())).thenReturn(patterns1);
        Iterable<Pattern> patterns2 = patternService.saveAllPatterns(patterns1);
        Iterator<Pattern> patterns2Iterator = patterns2.iterator();
        assertEquals(pattern1Seq, patterns2Iterator.next().getSeq());
        assertFalse(patterns2Iterator.hasNext());
    }

    @Test
    public void saveAllPatternsOptimisticLocking() {
        List<Pattern> patterns1 = new ArrayList<>();
        patterns1.add(pattern1);
        Mockito.when(patternRepository.saveAll(Mockito.anyIterable()))
                .thenThrow(ObjectOptimisticLockingFailureException.class);
        try {
            Iterable<Pattern> patterns2 = patternService.saveAllPatterns(patterns1);
            fail();
        } catch (DoughOptimisticLockingException e) {
        }
    }
}