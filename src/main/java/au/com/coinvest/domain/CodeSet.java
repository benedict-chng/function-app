package au.com.coinvest.domain;

import java.io.Serializable;
import java.util.Arrays;

public class CodeSet implements Serializable {
    private String codeSetName;
    private Code[] codes;

    public String getCodeSetName() {
        return codeSetName;
    }
    public void setCodeSetName(String codeSetName) {
        this.codeSetName = codeSetName;
    }
    public Code[] getCodes() {
        return codes;
    }
    public void setCodes(Code[] codes) {
        this.codes = codes;
    }

    @Override
    public String toString() {
        return "CodeSet [codeSetName=" + codeSetName + ", codes=" + Arrays.toString(codes) + "]";
    }
}
