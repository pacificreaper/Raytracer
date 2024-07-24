package XMLElements;

import Algebra.Matrix;
import Algebra.Vector;
import Raytracer.Ray;

import javax.xml.bind.annotation.XmlElement;

public class Camera {
    private Vector position;
    private Vector lookAt;
    private Vector up;
    private HorizontalFOV horizontalFOV;
    private Resolution resolution;
    private MaxBounces maxBounces;

    public Vector getPosition() {
        return position;
    }

    @XmlElement(name = "position")
    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getLookAt() {
        return lookAt;
    }

    @XmlElement(name = "lookat")
    public void setLookAt(Vector lookAt) {
        this.lookAt = lookAt;
    }

    public Vector getUp() {
        return up;
    }

    @XmlElement(name = "up")
    public void setUp(Vector up) {
        this.up = up;
    }

    public HorizontalFOV getHorizontalFOV() {
        return horizontalFOV;
    }

    @XmlElement(name = "horizontal_fov")
    public void setHorizontalFOV(HorizontalFOV horizontalFOV) {
        this.horizontalFOV = horizontalFOV;
    }

    public Resolution getResolution() {
        return resolution;
    }

    @XmlElement(name = "resolution")
    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public MaxBounces getMaxBounces() {
        return maxBounces;
    }

    @XmlElement(name = "max_bounces")
    public void setMaxBounces(MaxBounces maxBounces) {
        this.maxBounces = maxBounces;
    }

    public Matrix getTransformationMatrix() {
        Vector Z = position.subtract(lookAt).normalize();
        Vector X = up.cross(Z).normalize();
        Vector Y = Z.cross(X).normalize();

        Matrix rotationMatrix = new Matrix(new double[][] {
                {X.getX(), Y.getX(), Z.getX(), 0},
                {X.getY(), Y.getY(), Z.getY(), 0},
                {X.getZ(), Y.getZ(), Z.getZ(), 0},
                {0, 0, 0, 1}
        });

        Matrix translationMatrix = new Matrix(new double[][] {
                {1, 0, 0, position.getX()},
                {0, 1, 0, position.getY()},
                {0, 0, 1, position.getZ()},
                {0, 0, 0, 1}
        });

        return translationMatrix.multiply(rotationMatrix);
    }

    public Ray transformRay(Ray cameraRay, Matrix transformationMatrix) {
        Vector transformedOrigin = transformationMatrix.transformPoint(cameraRay.getOrigin());
        Vector transformedDirection = transformationMatrix.transformVector(cameraRay.getDirection()).normalize();
        return new Ray(transformedOrigin, transformedDirection);
    }
}
