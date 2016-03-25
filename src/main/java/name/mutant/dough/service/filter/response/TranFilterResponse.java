package name.mutant.dough.service.filter.response;

import name.mutant.dough.domain.Tran;

import java.util.ArrayList;
import java.util.List;

public class TranFilterResponse extends BaseFilterResponse {
    private List<Tran> resultList = new ArrayList<>();

    public List<Tran> getResultList() {
        return resultList;
    }

    public void setResultList(List<Tran> resultList) {
        this.resultList = resultList;
    }
}
