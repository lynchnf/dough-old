package norman.dough.web.view;

import norman.dough.domain.DataFile;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class DataFileListForm extends ListForm<DataFile> {
    private List<DataFileListRow> rows = new ArrayList<>();

    public DataFileListForm(Page<DataFile> innerPage) {
        super(innerPage);
        for (DataFile entity : innerPage.getContent()) {
            DataFileListRow row = new DataFileListRow(entity);
            rows.add(row);
        }
    }

    public List<DataFileListRow> getRows() {
        return rows;
    }
}
