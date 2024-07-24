package XMLElements;

import javax.xml.bind.annotation.XmlAttribute;

public class Resolution {
    private int horizontal;
    private int vertical;

    public int getHorizontal() {
        return horizontal;
    }

    @XmlAttribute
    public void setHorizontal(int horizontal) {
        this.horizontal = horizontal;
    }

    public int getVertical() {
        return vertical;
    }

    @XmlAttribute
    public void setVertical(int vertical) {
        this.vertical = vertical;
    }
}
