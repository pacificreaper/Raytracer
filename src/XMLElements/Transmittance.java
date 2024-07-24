package XMLElements;

import javax.xml.bind.annotation.XmlAttribute;

public class Transmittance {
    private double t;

    public Transmittance(){
        this.t = 0.0;
    }

    public double getT() {
        return t;
    }

    @XmlAttribute
    public void setT(double t) {
        this.t = t;
    }
}
