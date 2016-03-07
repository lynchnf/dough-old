package name.mutant.dough.service.filter.response;

public abstract class BaseFilterResponse {
    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
