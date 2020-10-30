package norman.dough.web.view;

import norman.dough.domain.DataFile;
import norman.dough.domain.DataLine;

public class DataLineView {
    private Long id;
    private DataFile dataFile;
    private Integer seq;
    private String text;

    public DataLineView(DataLine entity) {
        id = entity.getId();
        dataFile = entity.getDataFile();
        seq = entity.getSeq();
        text = entity.getText();
    }

    public Long getId() {
        return id;
    }

    public DataFile getDataFile() {
        return dataFile;
    }

    public Integer getSeq() {
        return seq;
    }

    public String getText() {
        return text;
    }
}
