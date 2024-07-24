package Raytracer;

import Algebra.Vector;
import XMLElements.Sphere;
import shapes.IntersectionTest;
import shapes.Triangle;

import java.util.List;

public class Ray {
    private Vector origin;
    private Vector direction;
    final double epsilon = 1e-8;

    public Ray(){
        this.origin = new Vector(0,0,0);
        this.direction = new Vector(0,0,0);
    }

    public Ray(Vector direction){
        this.origin = new Vector(0,0,0);
        this.direction = direction.normalize();
    }

    public Ray(Vector origin, Vector direction) {
        this.origin = origin;
        this.direction = direction.normalize();
    }

    public Vector getOrigin() {
        return origin;
    }

    public Vector getDirection() {
        return direction;
    }

   public double intersects(Sphere sphere) {
        Vector centerPoint = sphere.getCenterPoint();
        double radius = sphere.getRadius();
        Vector oc = origin.subtract(centerPoint);
        double a = direction.dot(direction);
        double b = 2 * oc.dot(direction);
        double c = oc.dot(oc) - radius * radius;
        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            return -1.0;
        } else if (discriminant == 0){
            double t = -b / (2.0 * a);
            if (t <= 0)
                return -1;
            return t;
        } else {
            // determine closest intersection
            double t1 = (-b + Math.sqrt(discriminant)) / (2.0 * a);
            double t2 = (-b - Math.sqrt(discriminant)) / (2.0 * a);
            if (t1 <= 0 && t2 <= 0)
                return -1;
            else if (t1 <= 0) {
                return t2;
            }
            else if (t2 <= 0){
                return t1;
            }
            else
                return Math.min(t1, t2);
        }
    }

    public Vector calculateIntersectionPoint(double intersection) {
        return origin.add(direction.multiply(intersection));
    }

    public IntersectionTest intersects(Triangle triangle) {
        List<Vector> vertices = triangle.getVertices();
        Vector v0 = vertices.get(0);
        Vector v1 = vertices.get(1);
        Vector v2 = vertices.get(2);

        Vector s = origin.subtract(v0);
        Vector edge1 = v1.subtract(v0);
        Vector edge2 = v2.subtract(v0);
        double detA = direction.cross(edge2).dot(edge1);



        if (Math.abs(detA) < epsilon) {
            return new IntersectionTest(-1, -1, -1, new Vector());
        }

        double inverseDet = 1.0/detA;

        double a = inverseDet * direction.cross(edge2).dot(s);
        if (a < 0 || a > 1) {
            return new IntersectionTest(-1, -1, -1, new Vector());
        }

        double b = inverseDet * s.cross(edge1).dot(direction);
        if (b < 0 || a+b > 1.0) {
            return new IntersectionTest(-1, -1, -1, new Vector());
        }

        double t = inverseDet * s.cross(edge1).dot(edge2);
        /*if (t <= 0)
            return new IntersectionTest(-1, -1, -1, new Vector());
*/
        return new IntersectionTest(t, a, b, calculateIntersectionPoint(t));
    }

}
