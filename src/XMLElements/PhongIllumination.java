package XMLElements;

import javax.xml.bind.annotation.XmlAttribute;

public class PhongIllumination {
    private double ka;
    private double kd;
    private double ks;
    private int exponent;

    public double getKa() {
        return ka;
    }

    @XmlAttribute
    public void setKa(double ka) {
        this.ka = ka;
    }

    public double getKd() {
        return kd;
    }

    @XmlAttribute
    public void setKd(double kd) {
        this.kd = kd;
    }

    public double getKs() {
        return ks;
    }

    @XmlAttribute
    public void setKs(double ks) {
        this.ks = ks;
    }

    public int getExponent() {
        return exponent;
    }

    @XmlAttribute
    public void setExponent(int exponent) {
        this.exponent = exponent;
    }

    @Override
    public String toString() {
        return "PhongIllumination{" +
                "ka=" + ka +
                ", kd=" + kd +
                ", ks=" + ks +
                ", exponent=" + exponent +
                '}';
    }
}
