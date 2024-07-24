package XMLElements;

import javax.xml.bind.annotation.XmlAttribute;

public class Refraction {
    private double indexOfRefraction;

    public Refraction(){
        this.indexOfRefraction = 0.0;
    }

    public double getIndexOfRefraction() {
        return indexOfRefraction;
    }

    @XmlAttribute(name = "iof")
    public void setIndexOfRefraction(double indexOfRefraction) {
        this.indexOfRefraction = indexOfRefraction;
    }
}
