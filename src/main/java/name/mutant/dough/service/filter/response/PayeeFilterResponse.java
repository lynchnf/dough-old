package name.mutant.dough.service.filter.response;

import name.mutant.dough.domain.Payee;

import java.util.ArrayList;
import java.util.List;

public class PayeeFilterResponse extends BaseFilterResponse {
    private List<Payee> resultList = new ArrayList<>();

    public List<Payee> getResultList() {
        return resultList;
    }

    public void setResultList(List<Payee> resultList) {
        this.resultList = resultList;
    }
}
