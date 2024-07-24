package XMLElements.Lights;

import Algebra.Vector;
import XMLElements.Light;

import javax.xml.bind.annotation.XmlElement;

public class SpotLight extends Light {
    private Vector position;
    private Vector direction;
    private FallOff fallOff;

    public Vector getPosition() {
        return position;
    }

    @XmlElement(name = "position")
    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getDirection() {
        return direction;
    }

    @XmlElement(name = "direction")
    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public FallOff getFallOff() {
        return fallOff;
    }

    @XmlElement(name = "fall_off")
    public void setFallOff(FallOff fallOff) {
        this.fallOff = fallOff;
    }
}
