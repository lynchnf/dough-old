package name.mutant.dough.service.filter.response;

import name.mutant.dough.domain.Acct;

import java.util.ArrayList;
import java.util.List;

public class AcctFilterResponse extends BaseFilterResponse {
    private List<Acct> resultList = new ArrayList<>();

    public List<Acct> getResultList() {
        return resultList;
    }

    public void setResultList(List<Acct> resultList) {
        this.resultList = resultList;
    }
}
