package name.mutant.dough.service.filter.request;

import name.mutant.dough.domain.PayeeType;

public class PayeeFilterRequest extends BaseFilterRequest {
    private static final long serialVersionUID = 1L;
    private static final PayeeOrderByField DEFAULT_ORDER_BY_FIELD = PayeeOrderByField.NAME;
    private String whereNameLike;
    private PayeeType whereTypeEq;
    private PayeeOrderByField orderByField = DEFAULT_ORDER_BY_FIELD;

    public String getWhereNameLike() {
        return whereNameLike;
    }

    public void setWhereNameLike(String whereNameLike) {
        this.whereNameLike = whereNameLike;
    }

    public PayeeType getWhereTypeEq() {
        return whereTypeEq;
    }

    public void setWhereTypeEq(PayeeType whereTypeEq) {
        this.whereTypeEq = whereTypeEq;
    }

    public PayeeOrderByField getOrderByField() {
        return orderByField;
    }

    public void setOrderByField(PayeeOrderByField orderByField) {
        this.orderByField = orderByField;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PayeeFilterRequest [");
        if (whereNameLike != null) {
            builder.append("whereNameLike=");
            builder.append(whereNameLike);
            builder.append(", ");
        }
        if (whereTypeEq != null) {
            builder.append("whereTypeEq=");
            builder.append(whereTypeEq);
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
