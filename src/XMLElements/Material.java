package XMLElements;

public class Material {
    private PhongIllumination phong;
    private Reflectance reflectance;
    private Transmittance transmittance;
    private Refraction refraction;

    public Material() {
    }

    public Material(PhongIllumination phong, Reflectance reflectance, Transmittance transmittance, Refraction refraction) {
        this.phong = new PhongIllumination();
        this.reflectance = new Reflectance();
        this.transmittance = new Transmittance();
        this.refraction = new Refraction();
    }

    public PhongIllumination getPhong() {
        return phong;
    }

    public void setPhong(PhongIllumination phong) {
        this.phong = phong;
    }

    public Reflectance getReflectance() {
        return reflectance;
    }

    public void setReflectance(Reflectance reflectance) {
        this.reflectance = reflectance;
    }

    public Transmittance getTransmittance() {
        return transmittance;
    }

    public void setTransmittance(Transmittance transmittance) {
        this.transmittance = transmittance;
    }

    public Refraction getRefraction() {
        return refraction;
    }

    public void setRefraction(Refraction refraction) {
        this.refraction = refraction;
    }
}
