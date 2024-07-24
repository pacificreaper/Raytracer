package XMLElements;

import javax.xml.bind.annotation.XmlAttribute;

public class HorizontalFOV {
    private int angle;

    public int getAngle() {
        return angle;
    }

    @XmlAttribute
    public void setAngle(int angle) {
        this.angle = angle;
    }
}
