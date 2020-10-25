package norman.dough.web.view;

import norman.dough.domain.AcctNbr;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class AcctNbrListForm extends ListForm<AcctNbr> {
    private List<AcctNbrListRow> rows = new ArrayList<>();

    public AcctNbrListForm(Page<AcctNbr> innerPage) {
        super(innerPage);
        for (AcctNbr entity : innerPage.getContent()) {
            AcctNbrListRow row = new AcctNbrListRow(entity);
            rows.add(row);
        }
    }

    public List<AcctNbrListRow> getRows() {
        return rows;
    }
}
