package norman.dough.web.view;

import norman.dough.domain.DataLine;

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
