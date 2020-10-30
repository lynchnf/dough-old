package norman.dough.web.view;

import norman.dough.domain.Cat;

public class CatListRow {
    private Long id;
    private String name;

    public CatListRow(Cat entity) {
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
