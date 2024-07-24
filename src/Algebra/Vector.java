package Algebra;

import javax.xml.bind.annotation.XmlAttribute;

public class Vector {
    private double x;
    private double y;
    private double z;

    public Vector() {
    }

    public Vector(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    @XmlAttribute
    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    @XmlAttribute
    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    @XmlAttribute
    public void setZ(double z) {
        this.z = z;
    }

    public Vector add(Vector vector) {
        return new Vector(this.x + vector.x, this.y + vector.y, this.z + vector.z);
    }

    public Vector subtract(Vector vector) {
        return new Vector(this.x - vector.x, this.y - vector.y, this.z - vector.z);
    }

    public double dot(Vector vector) {
        return this.x * vector.x + this.y * vector.y + this.z * vector.z;
    }

    public Vector cross(Vector vector) {
        return new Vector(
                this.y * vector.z - this.z * vector.y,
                this.z * vector.x - this.x * vector.z,
                this.x * vector.y - this.y * vector.x
        );
    }

    public Vector normalize() {
        if (magnitude() > 0) {
            return new Vector(this.x / magnitude(), this.y / magnitude(), this.z / magnitude());
        } else {
            return new Vector(0, 0, 0);
        }
    }

    public Vector multiply(double scalar) {
        return new Vector(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public Vector negate() {
        return new Vector(-x, -y, -z);
    }

    @Override
    public String toString() {
        return x + ", " + y + ", " + z;
    }
}
