package XMLElements;

import javax.xml.bind.annotation.XmlAttribute;

public class Reflectance {
    private double r;

    public  Reflectance(){
        this.r = 0.0;
    }

    public double getR() {
        return r;
    }

    @XmlAttribute
    public void setR(double r) {
        this.r = r;
    }
}
