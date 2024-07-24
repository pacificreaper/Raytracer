package XMLElements;

import Algebra.Vector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "point_light")
public class PointLight extends Light {
    private Vector position;
    private Color color;

    public Vector getPosition() {
        return position;
    }

    @XmlElement(name = "position")
    public void setPosition(Vector position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    @XmlElement(name = "color")
    public void setColor(Color color) {
        this.color = color;
    }
}
