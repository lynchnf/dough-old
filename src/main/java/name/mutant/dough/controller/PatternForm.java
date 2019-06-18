package name.mutant.dough.controller;

import name.mutant.dough.domain.Pattern;
import name.mutant.dough.service.CategoryService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class PatternForm {
    @Valid
    private List<PatternRow> patternRows = new ArrayList<>();
    private PatternFormAction action;

    public PatternForm() {
    }

    public PatternForm(Iterable<Pattern> patterns) {
        for (Pattern pattern : patterns) {
            PatternRow patternRow = new PatternRow(pattern);
            patternRows.add(patternRow);
        }
    }

    public List<Pattern> toPatterns(CategoryService categoryService) {
        List<Pattern> patterns = new ArrayList<>();
        for (int i = 0; i < patternRows.size(); i++) {
            PatternRow patternRow = patternRows.get(i);
            Pattern pattern = patternRow.toPattern(categoryService);
            pattern.setSeq(i + 1);
            patterns.add(pattern);
        }
        return patterns;
    }

    public List<PatternRow> getPatternRows() {
        return patternRows;
    }

    public void setPatternRows(List<PatternRow> patternRows) {
        this.patternRows = patternRows;
    }
}