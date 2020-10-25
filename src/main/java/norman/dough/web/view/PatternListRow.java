package norman.dough.web.view;

import norman.dough.domain.Cat;
import norman.dough.domain.Pattern;

public class PatternListRow {
    private Long id;
    private Integer seq;
    private String regex;
    private Cat cat;

    public PatternListRow(Pattern entity) {
        id = entity.getId();
        seq = entity.getSeq();
        regex = entity.getRegex();
        cat = entity.getCat();
    }

    public Long getId() {
        return id;
    }

    public Integer getSeq() {
        return seq;
    }

    public String getRegex() {
        return regex;
    }

    public Cat getCat() {
        return cat;
    }
}
