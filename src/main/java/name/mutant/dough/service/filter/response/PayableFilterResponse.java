package name.mutant.dough.service.filter.response;

import name.mutant.dough.domain.Payable;

import java.util.ArrayList;
import java.util.List;

public class PayableFilterResponse extends BaseFilterResponse {
    private List<Payable> resultList = new ArrayList<>();

    public List<Payable> getResultList() {
        return resultList;
    }

    public void setResultList(List<Payable> resultList) {
        this.resultList = resultList;
    }
}
