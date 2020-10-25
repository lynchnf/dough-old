package norman.dough.web.view;

import norman.dough.domain.DataLine;

import java.math.BigDecimal;
import java.util.Date;

public class DataLineListRow {
    private Long id;
    private Integer seq;
    private String text;

    public DataLineListRow(DataLine entity) {
        id = entity.getId();
        seq = entity.getSeq();
        text = entity.getText();
    }

    public Long getId() {
        return id;
    }

    public Integer getSeq() {
        return seq;
    }

    public String getText() {
        return text;
    }
}
