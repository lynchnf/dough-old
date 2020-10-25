package norman.dough.web.view;

import norman.dough.domain.Stmt;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class StmtListForm extends ListForm<Stmt> {
    private List<StmtListRow> rows = new ArrayList<>();

    public StmtListForm(Page<Stmt> innerPage) {
        super(innerPage);
        for (Stmt entity : innerPage.getContent()) {
            StmtListRow row = new StmtListRow(entity);
            rows.add(row);
        }
    }

    public List<StmtListRow> getRows() {
        return rows;
    }
}
