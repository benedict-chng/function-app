package au.com.coinvest.domain;

import java.io.Serializable;

public class Code implements Serializable {
    private String value;
    private String description;

    private boolean selectable;

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isSelectable() {
        return selectable;
    }
    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    @Override
    public String toString() {
        return "Code [value=" + value + ", description=" + description + ", isSelectable=" + selectable + "]";
    }
}
