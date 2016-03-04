package name.mutant.dough.service.response;

import name.mutant.dough.service.dto.OfxAcct;
import name.mutant.dough.service.dto.OfxInst;
import name.mutant.dough.service.dto.OfxStmtTran;

import java.util.ArrayList;
import java.util.List;

public class OfxParseFileResponse {
    private OfxInst ofxInst;
    private OfxAcct ofxAcct;
    private List<OfxStmtTran> ofxStmtTrans = new ArrayList<>();

    public OfxInst getOfxInst() {
        return ofxInst;
    }

    public void setOfxInst(OfxInst ofxInst) {
        this.ofxInst = ofxInst;
    }

    public OfxAcct getOfxAcct() {
        return ofxAcct;
    }

    public void setOfxAcct(OfxAcct ofxAcct) {
        this.ofxAcct = ofxAcct;
    }

    public List<OfxStmtTran> getOfxStmtTrans() {
        return ofxStmtTrans;
    }

    public void addOfxStmtTran(OfxStmtTran ofxStmtTran) {
        ofxStmtTrans.add(ofxStmtTran);
    }
}
