package XMLElements;

import javax.xml.bind.annotation.XmlAttribute;

public class MaxBounces {
    private int n;

    public int getN() {
        return n;
    }

    @XmlAttribute
    public void setN(int n) {
        this.n = n;
    }
}
