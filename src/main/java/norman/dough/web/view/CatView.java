package norman.dough.web.view;

import norman.dough.domain.Cat;

import java.math.BigDecimal;
import java.util.Date;

public class CatView {
    private Long id;
    private String name;

    public CatView(Cat entity) {
        id = entity.getId();
        name = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
