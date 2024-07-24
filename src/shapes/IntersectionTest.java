package shapes;

import Algebra.Vector;

public class IntersectionTest {
    private double t;
    private double a;
    private double b;
    private Vector intersectionPoint;

    public IntersectionTest(double t, double a, double b, Vector intersectionPoint) {
        this.t = t;
        this.a = a;
        this.b = b;
        this.intersectionPoint = intersectionPoint;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public Vector getIntersectionPoint() {
        return intersectionPoint;
    }

    public void setIntersectionPoint(Vector intersectionPoint) {
        this.intersectionPoint = intersectionPoint;
    }
}
