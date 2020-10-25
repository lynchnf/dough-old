package norman.dough.web.view;

import norman.dough.domain.Cat;
import norman.dough.domain.Pattern;
import norman.dough.exception.NotFoundException;
import norman.dough.service.CatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PatternEditForm {
    private CatService catService;
    private Long id;
    private Integer version = 0;
    @NotNull(message = "Sequence may not be blank.")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Integer seq;
    @NotBlank(message = "Regular Expression may not be blank.")
    @Size(max = 255, message = "Regular Expression may not be over {max} characters long.")
    private String regex;
    @NotNull(message = "Category may not be blank.")
    private Long catId;

    public PatternEditForm() {
    }

    public PatternEditForm(Pattern entity) {
        id = entity.getId();
        version = entity.getVersion();
        seq = entity.getSeq();
        regex = entity.getRegex();
        if (entity.getCat() != null) {
            catId = entity.getCat().getId();
        }
    }

    public Pattern toEntity() throws NotFoundException {
        Pattern entity = new Pattern();
        entity.setId(id);
        entity.setVersion(version);
        entity.setSeq(seq);
        entity.setRegex(StringUtils.trimToNull(regex));
        if (catId != null) {
            Cat cat = catService.findById(catId);
            entity.setCat(cat);
        }
        return entity;
    }

    public void setCatService(CatService catService) {
        this.catService = catService;
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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }
}
