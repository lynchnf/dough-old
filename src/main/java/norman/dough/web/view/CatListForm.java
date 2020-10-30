package norman.dough.web.view;

import norman.dough.domain.Cat;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class CatListForm extends ListForm<Cat> {
    private List<CatListRow> rows = new ArrayList<>();

    public CatListForm(Page<Cat> innerPage) {
        super(innerPage);
        for (Cat entity : innerPage.getContent()) {
            CatListRow row = new CatListRow(entity);
            rows.add(row);
        }
    }

    public List<CatListRow> getRows() {
        return rows;
    }
}
