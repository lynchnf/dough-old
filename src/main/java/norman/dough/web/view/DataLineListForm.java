package norman.dough.web.view;

import norman.dough.domain.DataLine;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class DataLineListForm extends ListForm<DataLine> {
    private List<DataLineListRow> rows = new ArrayList<>();

    public DataLineListForm(Page<DataLine> innerPage) {
        super(innerPage);
        for (DataLine entity : innerPage.getContent()) {
            DataLineListRow row = new DataLineListRow(entity);
            rows.add(row);
        }
    }

    public List<DataLineListRow> getRows() {
        return rows;
    }
}
