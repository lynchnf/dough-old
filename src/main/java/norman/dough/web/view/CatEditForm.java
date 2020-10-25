package norman.dough.web.view;

import norman.dough.domain.Cat;
import norman.dough.exception.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

public class CatEditForm {
    private Long id;
    private Integer version = 0;
    @NotBlank(message = "Name may not be blank.")
    @Size(max = 100, message = "Name may not be over {max} characters long.")
    private String name;

    public CatEditForm() {
    }

    public CatEditForm(Cat entity) {
        id = entity.getId();
        version = entity.getVersion();
        name = entity.getName();
    }

    public Cat toEntity() throws NotFoundException {
        Cat entity = new Cat();
        entity.setId(id);
        entity.setVersion(version);
        entity.setName(StringUtils.trimToNull(name));
        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
