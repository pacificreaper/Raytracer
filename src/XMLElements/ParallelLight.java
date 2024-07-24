package XMLElements;

import Algebra.Vector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "parallel_light")
public class ParallelLight extends Light {
    private Vector direction;

    public Vector getDirection() {
        return direction;
    }

    @XmlElement(name = "direction")
    public void setDirection(Vector direction) {
        this.direction = direction;
    }
}
