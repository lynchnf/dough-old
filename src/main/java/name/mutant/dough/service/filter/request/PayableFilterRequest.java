package name.mutant.dough.service.filter.request;

import java.util.Date;

public class PayableFilterRequest extends BaseFilterRequest {
    private static final long serialVersionUID = 1L;
    private static final PayableOrderByField DEFAULT_ORDER_BY_FIELD = PayableOrderByField.DUE_DATE;
    private Long wherePayeeIdEq;
    private Date whereDueDateAfter;
    private Date whereDueDateBefore;
    private String whereMemoLike;
    private Boolean whereActual;
    private Boolean wherePaid;
    private Boolean whereNoBill;
    private PayableOrderByField orderByField = DEFAULT_ORDER_BY_FIELD;

    public Long getWherePayeeIdEq() {
        return wherePayeeIdEq;
    }

    public void setWherePayeeIdEq(Long wherePayeeIdEq) {
        this.wherePayeeIdEq = wherePayeeIdEq;
    }

    public Date getWhereDueDateAfter() {
        return whereDueDateAfter;
    }

    public void setWhereDueDateAfter(Date whereDueDateAfter) {
        this.whereDueDateAfter = whereDueDateAfter;
    }

    public Date getWhereDueDateBefore() {
        return whereDueDateBefore;
    }

    public void setWhereDueDateBefore(Date whereDueDateBefore) {
        this.whereDueDateBefore = whereDueDateBefore;
    }

    public String getWhereMemoLike() {
        return whereMemoLike;
    }

    public void setWhereMemoLike(String whereMemoLike) {
        this.whereMemoLike = whereMemoLike;
    }

    public Boolean getWhereActual() {
        return whereActual;
    }

    public void setWhereActual(Boolean whereActual) {
        this.whereActual = whereActual;
    }

    public Boolean getWherePaid() {
        return wherePaid;
    }

    public void setWherePaid(Boolean wherePaid) {
        this.wherePaid = wherePaid;
    }

    public Boolean getWhereNoBill() {
        return whereNoBill;
    }

    public void setWhereNoBill(Boolean whereNoBill) {
        this.whereNoBill = whereNoBill;
    }

    public PayableOrderByField getOrderByField() {
        return orderByField;
    }

    public void setOrderByField(PayableOrderByField orderByField) {
        this.orderByField = orderByField;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PayableFilterRequest{");
        if (wherePayeeIdEq != null) sb.append("wherePayeeIdEq=").append(wherePayeeIdEq).append(", ");
        if (whereDueDateAfter != null) sb.append("whereDueDateAfter=").append(whereDueDateAfter).append(", ");
        if (whereDueDateBefore != null) sb.append("whereDueDateBefore=").append(whereDueDateBefore).append(", ");
        if (whereMemoLike != null) sb.append("whereMemoLike='").append(whereMemoLike).append('\'').append(", ");
        if (whereActual != null) sb.append("whereActual=").append(whereActual).append(", ");
        if (wherePaid != null) sb.append("wherePaid=").append(wherePaid).append(", ");
        if (whereNoBill != null) sb.append("whereNoBill=").append(whereNoBill).append(", ");
        sb.append("orderByField=").append(orderByField).append(", ");
        sb.append("orderByDirection=").append(orderByDirection).append(", ");
        sb.append("first=").append(first).append(", ");
        sb.append("max=").append(max).append('}');
        return sb.toString();
    }
}
