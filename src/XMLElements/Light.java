package XMLElements;

import javax.xml.bind.annotation.XmlElement;

public class Light {
    private Color color;

    public Color getColor() {
        return color;
    }

    @XmlElement(name = "color")
    public void setColor(Color color) {
        this.color = color;
    }
}
