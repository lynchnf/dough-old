package name.mutant.dough.service.filter.request;

import java.io.Serializable;

public abstract class BaseFilterRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final OrderByDirection DEFAULT_ORDER_BY_DIRECTION = OrderByDirection.ASC;
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int[] PAGE_SIZE_OPTIONS = {10, 20, 50, 100};
    protected OrderByDirection orderByDirection = DEFAULT_ORDER_BY_DIRECTION;
    protected int first = 0;
    protected int max = DEFAULT_PAGE_SIZE;

    public OrderByDirection getOrderByDirection() {
        return orderByDirection;
    }

    public void setOrderByDirection(OrderByDirection orderByDirection) {
        this.orderByDirection = orderByDirection;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
