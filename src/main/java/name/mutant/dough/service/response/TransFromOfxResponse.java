package name.mutant.dough.service.response;

import name.mutant.dough.domain.Tran;

import java.util.ArrayList;
import java.util.List;

public class TransFromOfxResponse {
    private List<Tran> trans = new ArrayList<>();
    private List<String> errors = new ArrayList<>();

    public List<Tran> getTrans() {
        return trans;
    }

    public List<String> getErrors() {
        return errors;
    }
}
