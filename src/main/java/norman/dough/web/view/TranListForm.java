package norman.dough.web.view;

import norman.dough.domain.Tran;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class TranListForm extends ListForm<Tran> {
    private List<TranListRow> rows = new ArrayList<>();

    public TranListForm(Page<Tran> innerPage) {
        super(innerPage);
        for (Tran entity : innerPage.getContent()) {
            TranListRow row = new TranListRow(entity);
            rows.add(row);
        }
    }

    public List<TranListRow> getRows() {
        return rows;
    }
}
