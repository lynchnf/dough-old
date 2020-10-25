package norman.dough.web.view;

import norman.dough.domain.DataTran;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class DataTranListForm extends ListForm<DataTran> {
    private List<DataTranListRow> rows = new ArrayList<>();

    public DataTranListForm(Page<DataTran> innerPage) {
        super(innerPage);
        for (DataTran entity : innerPage.getContent()) {
            DataTranListRow row = new DataTranListRow(entity);
            rows.add(row);
        }
    }

    public List<DataTranListRow> getRows() {
        return rows;
    }
}
