package norman.dough.web.view;

import norman.dough.domain.Acct;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class AcctListForm extends ListForm<Acct> {
    private List<AcctListRow> rows = new ArrayList<>();

    public AcctListForm(Page<Acct> innerPage) {
        super(innerPage);
        for (Acct entity : innerPage.getContent()) {
            AcctListRow row = new AcctListRow(entity);
            rows.add(row);
        }
    }

    public List<AcctListRow> getRows() {
        return rows;
    }
}
