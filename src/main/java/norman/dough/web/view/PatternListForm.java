package norman.dough.web.view;

import norman.dough.domain.Pattern;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PatternListForm extends ListForm<Pattern> {
    private List<PatternListRow> rows = new ArrayList<>();

    public PatternListForm(Page<Pattern> innerPage) {
        super(innerPage);
        for (Pattern entity : innerPage.getContent()) {
            PatternListRow row = new PatternListRow(entity);
            rows.add(row);
        }
    }

    public List<PatternListRow> getRows() {
        return rows;
    }
}
