package XMLElements;

import javax.xml.bind.annotation.XmlElement;

public class MaterialSolid extends Material{
    private Color color;
    private PhongIllumination phong;
    private Reflectance reflectance;
    private Transmittance transmittance;
    private Refraction refraction;

    public MaterialSolid() {}

    public MaterialSolid(Color color){
        this.color = color;
        this.phong = new PhongIllumination();
        this.reflectance = new Reflectance();
        this.transmittance = new Transmittance();
        this.refraction = new Refraction();
    }


    public Color getColor() {
        return color;
    }

    @XmlElement(name = "color")
    public void setColor(Color color) {
        this.color = color;
    }

    public PhongIllumination getPhong() {
        return phong;
    }

    @XmlElement(name = "phong")
    public void setPhong(PhongIllumination phong) {
        this.phong = phong;
    }

    public Reflectance getReflectance() {
        return reflectance;
    }

    @XmlElement(name = "reflectance")
    public void setReflectance(Reflectance reflectance) {
        this.reflectance = reflectance;
    }

    public Transmittance getTransmittance() {
        return transmittance;
    }

    @XmlElement(name = "transmittance")
    public void setTransmittance(Transmittance transmittance) {
        this.transmittance = transmittance;
    }

    public Refraction getRefraction() {
        return refraction;
    }

    @XmlElement(name = "refraction")
    public void setRefraction(Refraction refraction) {
        this.refraction = refraction;
    }
}
