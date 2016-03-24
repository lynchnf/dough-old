package name.mutant.dough.service.filter.request;

import name.mutant.dough.domain.AcctType;

public class AcctFilterRequest extends BaseFilterRequest {
    private static final long serialVersionUID = 1L;
    private static final AcctOrderByField DEFAULT_ORDER_BY_FIELD = AcctOrderByField.NAME;
    private String whereAcctNbrEq;
    private String whereNameLike;
    private String whereOrganizationLike;
    private String whereFidEq;
    private String whereOfxBankIdEq;
    private String whereOfxAcctIdEq;
    private AcctType whereTypeEq;
    private AcctOrderByField orderByField = DEFAULT_ORDER_BY_FIELD;

    public String getWhereAcctNbrEq() {
        return whereAcctNbrEq;
    }

    public void setWhereAcctNbrEq(String whereAcctNbrEq) {
        this.whereAcctNbrEq = whereAcctNbrEq;
    }

    public String getWhereNameLike() {
        return whereNameLike;
    }

    public void setWhereNameLike(String whereNameLike) {
        this.whereNameLike = whereNameLike;
    }

    public String getWhereOrganizationLike() {
        return whereOrganizationLike;
    }

    public void setWhereOrganizationLike(String whereOrganizationLike) {
        this.whereOrganizationLike = whereOrganizationLike;
    }

    public String getWhereFidEq() {
        return whereFidEq;
    }

    public void setWhereFidEq(String whereFidEq) {
        this.whereFidEq = whereFidEq;
    }

    public String getWhereOfxBankIdEq() {
        return whereOfxBankIdEq;
    }

    public void setWhereOfxBankIdEq(String whereOfxBankIdEq) {
        this.whereOfxBankIdEq = whereOfxBankIdEq;
    }

    public String getWhereOfxAcctIdEq() {
        return whereOfxAcctIdEq;
    }

    public void setWhereOfxAcctIdEq(String whereOfxAcctIdEq) {
        this.whereOfxAcctIdEq = whereOfxAcctIdEq;
    }

    public AcctType getWhereTypeEq() {
        return whereTypeEq;
    }

    public void setWhereTypeEq(AcctType whereTypeEq) {
        this.whereTypeEq = whereTypeEq;
    }

    public AcctOrderByField getOrderByField() {
        return orderByField;
    }

    public void setOrderByField(AcctOrderByField orderByField) {
        this.orderByField = orderByField;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AcctFilterRequest{");
        if (whereAcctNbrEq != null) sb.append("whereAcctNbrEq='").append(whereAcctNbrEq).append('\'').append(", ");
        if (whereNameLike != null) sb.append("whereNameLike='").append(whereNameLike).append('\'').append(", ");
        if (whereOrganizationLike != null)
            sb.append("whereOrganizationLike='").append(whereOrganizationLike).append('\'').append(", ");
        if (whereFidEq != null) sb.append("whereFidEq='").append(whereFidEq).append('\'').append(", ");
        if (whereOfxBankIdEq != null)
            sb.append("whereOfxBankIdEq='").append(whereOfxBankIdEq).append('\'').append(", ");
        if (whereOfxAcctIdEq != null)
            sb.append("whereOfxAcctIdEq='").append(whereOfxAcctIdEq).append('\'').append(", ");
        if (whereTypeEq != null) sb.append("whereTypeEq=").append(whereTypeEq).append(", ");
        sb.append("orderByField=").append(orderByField).append(", ");
        sb.append("orderByDirection=").append(orderByDirection).append(", ");
        sb.append("first=").append(first).append(", ");
        sb.append("max=").append(max).append('}');
        return sb.toString();
    }
}
