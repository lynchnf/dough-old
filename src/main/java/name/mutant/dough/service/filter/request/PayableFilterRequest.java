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
        StringBuilder builder = new StringBuilder();
        builder.append("PayableFilterRequest [");
        if (wherePayeeIdEq != null) {
            builder.append("wherePayeeIdEq=");
            builder.append(wherePayeeIdEq);
            builder.append(", ");
        }
        if (whereDueDateAfter != null) {
            builder.append("whereDueDateAfter=");
            builder.append(whereDueDateAfter);
            builder.append(", ");
        }
        if (whereDueDateBefore != null) {
            builder.append("whereDueDateBefore=");
            builder.append(whereDueDateBefore);
            builder.append(", ");
        }
        if (whereMemoLike != null) {
            builder.append("whereMemoLike=");
            builder.append(whereMemoLike);
            builder.append(", ");
        }
        if (whereActual != null) {
            builder.append("whereActual=");
            builder.append(whereActual);
            builder.append(", ");
        }
        if (wherePaid != null) {
            builder.append("wherePaid=");
            builder.append(wherePaid);
            builder.append(", ");
        }
        if (whereNoBill != null) {
            builder.append("whereNoBill=");
            builder.append(whereNoBill);
            builder.append(", ");
        }
        if (orderByField != null) {
            builder.append("orderByField=");
            builder.append(orderByField);
            builder.append(", ");
        }
        if (orderByDirection != null) {
            builder.append("orderByDirection=");
            builder.append(orderByDirection);
            builder.append(", ");
        }
        builder.append("first=");
        builder.append(first);
        builder.append(", max=");
        builder.append(max);
        builder.append("]");
        return builder.toString();
    }
}
