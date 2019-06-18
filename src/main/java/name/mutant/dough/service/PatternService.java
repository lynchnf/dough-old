package name.mutant.dough.service;

import name.mutant.dough.DoughOptimisticLockingException;
import name.mutant.dough.domain.Pattern;
import name.mutant.dough.repository.PatternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatternService {
    @Autowired
    private PatternRepository patternRepository;

    public List<Pattern> findAllPatterns() {
        return patternRepository.findAllByOrderBySeq();
    }

    public Iterable<Pattern> saveAllPatterns(List<Pattern> patterns) throws DoughOptimisticLockingException {
        try {
            return patternRepository.saveAll(patterns);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new DoughOptimisticLockingException("Optimistic locking failure while saving patterns", e);
        }
    }
}