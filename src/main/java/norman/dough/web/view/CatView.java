package norman.dough.web.view;

import norman.dough.domain.Cat;

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
