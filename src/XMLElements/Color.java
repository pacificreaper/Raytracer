package XMLElements;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.Objects;

public class Color {
    private double r;
    private double g;
    private double b;

    public Color() {
    }

    public Color(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public double getR() {
        return r;
    }

    @XmlAttribute
    public void setR(double r) {
        this.r = r;
    }

    public double getG() {
        return g;
    }

    @XmlAttribute
    public void setG(double g) {
        this.g = g;
    }

    public double getB() {
        return b;
    }

    @XmlAttribute
    public void setB(double b) {
        this.b = b;
    }

    public Color multiply(double scalar) {
        return new Color(this.r * scalar, this.g * scalar, this.b * scalar);
    }

    public Color multiply(Color color) {
        return new Color(this.r * color.r, this.g * color.g, this.b * color.b);
    }

    public Color add(Color color) {
        return new Color(this.r + color.r, this.g + color.g, this.b + color.b);
    }

    @Override
    public String toString() {
        return r + ", " + g + ", " + b;
    }

    public Color clamp() {
        return new Color(Math.min(1, Math.max(0, r)), Math.min(1, Math.max(0, g)), Math.min(1, Math.max(0, b)));
    }

    public int toRGB() {
        int r = (int) (this.r * 255);
        int g = (int) (this.g * 255);
        int b = (int) (this.b * 255);
        return (r << 16) | (g << 8) | b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return Double.compare(r, color.r) == 0 && Double.compare(g, color.g) == 0 && Double.compare(b, color.b) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b);
    }
}
