package norman.dough.web.view;

import norman.dough.domain.Pattern;
import norman.dough.domain.Cat;

import java.math.BigDecimal;
import java.util.Date;

public class PatternView {
    private Long id;
    private Integer seq;
    private String regex;
    private Cat cat;

    public PatternView(Pattern entity) {
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
