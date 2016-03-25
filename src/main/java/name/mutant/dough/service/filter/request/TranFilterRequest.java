package name.mutant.dough.service.filter.request;

import name.mutant.dough.domain.TranType;

import java.math.BigDecimal;
import java.util.Date;

public class TranFilterRequest extends BaseFilterRequest {
    private static final long serialVersionUID = 1L;
    private static final TranOrderByField DEFAULT_ORDER_BY_FIELD = TranOrderByField.POST_DATE;
    private Long whereAcctIdEq;
    private TranType whereTypeEq;
    private Date wherePostDateAfter;
    private Date wherePostDateBefore;
    private Date whereUserDateAfter;
    private Date whereUserDateBefore;
    private BigDecimal whereAmountGreaterOrEqual;
    private BigDecimal whereAmountLessOrEqual;
    private String whereFitIdEq;
    private String whereSicEq;
    private String whereCheckNumberEq;
    private String whereNameLike;
    private String whereMemoLike;
    private TranOrderByField orderByField = DEFAULT_ORDER_BY_FIELD;

    public Long getWhereAcctIdEq() {
        return whereAcctIdEq;
    }

    public void setWhereAcctIdEq(Long whereAcctIdEq) {
        this.whereAcctIdEq = whereAcctIdEq;
    }

    public TranType getWhereTypeEq() {
        return whereTypeEq;
    }

    public void setWhereTypeEq(TranType whereTypeEq) {
        this.whereTypeEq = whereTypeEq;
    }

    public Date getWherePostDateAfter() {
        return wherePostDateAfter;
    }

    public void setWherePostDateAfter(Date wherePostDateAfter) {
        this.wherePostDateAfter = wherePostDateAfter;
    }

    public Date getWherePostDateBefore() {
        return wherePostDateBefore;
    }

    public void setWherePostDateBefore(Date wherePostDateBefore) {
        this.wherePostDateBefore = wherePostDateBefore;
    }

    public Date getWhereUserDateAfter() {
        return whereUserDateAfter;
    }

    public void setWhereUserDateAfter(Date whereUserDateAfter) {
        this.whereUserDateAfter = whereUserDateAfter;
    }

    public Date getWhereUserDateBefore() {
        return whereUserDateBefore;
    }

    public void setWhereUserDateBefore(Date whereUserDateBefore) {
        this.whereUserDateBefore = whereUserDateBefore;
    }

    public BigDecimal getWhereAmountGreaterOrEqual() {
        return whereAmountGreaterOrEqual;
    }

    public void setWhereAmountGreaterOrEqual(BigDecimal whereAmountGreaterOrEqual) {
        this.whereAmountGreaterOrEqual = whereAmountGreaterOrEqual;
    }

    public BigDecimal getWhereAmountLessOrEqual() {
        return whereAmountLessOrEqual;
    }

    public void setWhereAmountLessOrEqual(BigDecimal whereAmountLessOrEqual) {
        this.whereAmountLessOrEqual = whereAmountLessOrEqual;
    }

    public String getWhereFitIdEq() {
        return whereFitIdEq;
    }

    public void setWhereFitIdEq(String whereFitIdEq) {
        this.whereFitIdEq = whereFitIdEq;
    }

    public String getWhereSicEq() {
        return whereSicEq;
    }

    public void setWhereSicEq(String whereSicEq) {
        this.whereSicEq = whereSicEq;
    }

    public String getWhereCheckNumberEq() {
        return whereCheckNumberEq;
    }

    public void setWhereCheckNumberEq(String whereCheckNumberEq) {
        this.whereCheckNumberEq = whereCheckNumberEq;
    }

    public String getWhereNameLike() {
        return whereNameLike;
    }

    public void setWhereNameLike(String whereNameLike) {
        this.whereNameLike = whereNameLike;
    }

    public String getWhereMemoLike() {
        return whereMemoLike;
    }

    public void setWhereMemoLike(String whereMemoLike) {
        this.whereMemoLike = whereMemoLike;
    }

    public TranOrderByField getOrderByField() {
        return orderByField;
    }

    public void setOrderByField(TranOrderByField orderByField) {
        this.orderByField = orderByField;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TranFilterRequest{");
        if (whereAcctIdEq != null) sb.append("whereAcctIdEq=").append(whereAcctIdEq).append(", ");
        if (whereTypeEq != null) sb.append("whereTypeEq=").append(whereTypeEq).append(", ");
        if (wherePostDateAfter != null) sb.append("wherePostDateAfter=").append(wherePostDateAfter).append(", ");
        if (wherePostDateBefore != null) sb.append("wherePostDateBefore=").append(wherePostDateBefore).append(", ");
        if (whereUserDateAfter != null) sb.append("whereUserDateAfter=").append(whereUserDateAfter).append(", ");
        if (whereUserDateBefore != null) sb.append("whereUserDateBefore=").append(whereUserDateBefore).append(", ");
        if (whereAmountGreaterOrEqual != null)
            sb.append("whereAmountGreaterOrEqual=").append(whereAmountGreaterOrEqual).append(", ");
        if (whereAmountLessOrEqual != null)
            sb.append("whereAmountLessOrEqual=").append(whereAmountLessOrEqual).append(", ");
        if (whereFitIdEq != null) sb.append("whereFitIdEq='").append(whereFitIdEq).append('\'').append(", ");
        if (whereSicEq != null) sb.append("whereSicEq='").append(whereSicEq).append('\'').append(", ");
        if (whereCheckNumberEq != null)
            sb.append("whereCheckNumberEq='").append(whereCheckNumberEq).append('\'').append(", ");
        if (whereNameLike != null) sb.append("whereNameLike='").append(whereNameLike).append('\'').append(", ");
        if (whereMemoLike != null) sb.append("whereMemoLike='").append(whereMemoLike).append('\'').append(", ");
        sb.append("orderByField=").append(orderByField).append(", ");
        sb.append("orderByDirection=").append(orderByDirection).append(", ");
        sb.append("first=").append(first).append(", ");
        sb.append("max=").append(max).append('}');
        return sb.toString();
    }
}
