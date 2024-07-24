package Raytracer;

import Algebra.Matrix;
import Algebra.Vector;
import XMLElements.*;
import shapes.IntersectionTest;
import shapes.Shape;
import shapes.Triangle;

import java.awt.image.BufferedImage;
import java.util.List;

public class Raytracer {
    private Ray ray;
    private Scene scene;
    private Ray refractedRay;
    private Ray reflectedRay;
    private double depth;


    public Raytracer() {
        // Ray with origin (0, 0, 0)
        this.ray = new Ray();
        this.reflectedRay = ray;
        this.refractedRay = ray;
    }

    // For the XML tasks
    public void raytraceXMLFile(BufferedImage image, Scene scene) {
        int imageHeight = image.getHeight();
        int imageWidth = image.getWidth();
        this.scene = scene;
        double fovX = Math.toRadians(scene.getCamera().getHorizontalFOV().getAngle());
        double fovY = fovX * ((double) imageWidth / imageHeight);

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                // Normalize coordinates
                double mappedX = (x + 0.5) / imageWidth;
                double mappedY = (y + 0.5) / imageHeight;
                // Map to image plane
                mappedX = (2 * mappedX - 1) * Math.tan(fovX);
                mappedY = (2 * mappedY - 1) * Math.tan(fovY);
                Vector rayDirection = new Vector(mappedX, mappedY, -1).normalize();

                Vector rayOrigin = new Vector(0, 0, 0);
                Ray cameraRay = new Ray(rayOrigin, rayDirection);
                Matrix transformationMatrix = scene.getCamera().getTransformationMatrix();
                this.ray = scene.getCamera().transformRay(cameraRay, transformationMatrix);

                int depth = 0;
                Color color = traceRay(ray, depth, true);
                image.setRGB(x, imageHeight - y - 1, color.toRGB());
            }
        }
    }

    private Color traceRay(Ray ray, int depth, boolean isReflection) {
        this.depth = depth;
        Shape closestShape = null;
        double closestIntersection = Double.MAX_VALUE;
        IntersectionTest intersectionData = null;
        Mesh closestMesh = null;

        // Get closest intersection
        for (Sphere sphere : scene.getSurface().getSpheres()) {
            double sphereIntersection = ray.intersects(sphere);
            if (sphereIntersection > 0 && sphereIntersection < closestIntersection) {
                closestIntersection = sphereIntersection;
                closestShape = sphere;
                intersectionData = new IntersectionTest(-1, -1, -1, ray.calculateIntersectionPoint(closestIntersection));
            }
        }

        for (Mesh mesh : scene.getSurface().getMeshes()) {
            for (Triangle triangle : mesh.getTriangles()) {
                IntersectionTest triangleIntersectionData = ray.intersects(triangle);
                double triangleIntersection = triangleIntersectionData.getT();
                if (triangleIntersection > 0 && triangleIntersection < closestIntersection) {
                    closestIntersection = triangleIntersection;
                    closestShape = triangle;
                    intersectionData = triangleIntersectionData;
                    closestMesh = mesh;
                }
            }
        }

        if (closestShape != null) {
            Color color = new Color(0, 0, 0);
            Vector normal = new Vector();
            double reflectance = 0.0;
            double transmittance = 0.0;

            if (closestShape instanceof Sphere) {

                Sphere closestSphere = (Sphere) closestShape;
                Material material = closestSphere.getMaterial();
                normal = intersectionData.getIntersectionPoint().subtract(closestSphere.getCenterPoint()).normalize();
                reflectance = material.getReflectance().getR();
                transmittance = material.getTransmittance().getT();

                color = illuminate(normal, material, intersectionData, closestSphere, isReflection);
            } else if (closestShape instanceof Triangle) {
                Material meshMaterial = closestMesh.getMaterial();
                Triangle closestTriangle = (Triangle) closestShape;
                List<Vector> vertices = closestTriangle.getVertices();
                Vector v0 = vertices.get(0);
                Vector v1 = vertices.get(1);
                Vector v2 = vertices.get(2);

                Vector firstEdge = v1.subtract(v0);
                Vector secondEdge = v2.subtract(v0);
                normal = firstEdge.cross(secondEdge).normalize();
                if (normal.getX() > 0.0){
                    normal = normal.negate();
                }
                color = illuminate(normal, meshMaterial, intersectionData, closestTriangle, isReflection);
            }

            // Reflection
            if (depth >= scene.getCamera().getMaxBounces().getN()) {
                return color;
            }
            Color reflectedColor = new Color(0,0,0);
            if (reflectance > 0) {
                Ray reflectedRay = getReflectedRay(intersectionData.getIntersectionPoint(), normal, depth);
                this.reflectedRay = reflectedRay;
                reflectedColor = traceRay(reflectedRay, depth + 1, true).multiply(reflectance);
            }

            // Refraction
            Color refractedColor = new Color(0,0,0);
            if (transmittance > 0) {
                double iof = ((Sphere) closestShape).getMaterial().getRefraction().getIndexOfRefraction();


                Ray refractedRay = getRefractedRay(intersectionData.getIntersectionPoint(), normal, iof, depth);
                this.refractedRay = refractedRay;

                refractedColor = traceRay(refractedRay, depth + 1, false).multiply(transmittance);
            }
            return color.multiply(1 - reflectance - transmittance).add(reflectedColor).add(refractedColor).clamp();
        } else {
            return scene.getBackgroundColor();
        }
    }

    private Color illuminate(Vector normal, Material material, IntersectionTest intersectionData, Shape closestShape, boolean isReflection) {
        Color color = new Color(0, 0, 0);
        double lightDistance = 0;

        for (Light light : scene.getLightsWrapper().getLights()) {
            if (light instanceof AmbientLight) {
                // Ambient lighting
                Color ambient = calculateAmbient(light.getColor(), material, intersectionData, closestShape);
                color = color.add(ambient);
            } else {
                Vector lightVector;
                Color lightColor = light.getColor();

                if (light instanceof ParallelLight) {
                    ParallelLight parallelLight = (ParallelLight) light;
                    lightVector = parallelLight.getDirection().normalize().negate();
                } else if (light instanceof PointLight) {
                    PointLight pointLight = (PointLight) light;
                    lightVector = pointLight.getPosition().subtract(intersectionData.getIntersectionPoint()).normalize();
                    lightDistance = lightVector.magnitude();
                } else {
                    continue;
                }

                // Check for shadows
                Ray shadowRay = new Ray(intersectionData.getIntersectionPoint().add(normal.multiply(0.0000001)), lightVector);
                boolean inShadow = isInShadow(shadowRay, lightDistance);

                if (!inShadow) {
                    // Diffuse
                    double nDotL = normal.dot(lightVector);
                    Color diffuse = calculateDiffuse(lightColor, nDotL, material, intersectionData, closestShape);
                    color = color.add(diffuse);

                    // Specular
                    Color specular = calculateSpecular(lightColor, nDotL, material, normal, lightVector, isReflection);
                    color = color.add(specular);
                }
            }
        }

        return color;
    }


    private boolean isInShadow(Ray shadowRay, double lightDistance) {
        for (Sphere sphere : scene.getSurface().getSpheres()) {
            if (shadowRay.intersects(sphere) >= 0 && shadowRay.intersects(sphere) <= lightDistance + 6.4) {
                return true;
            }
        }

        for (Mesh mesh : scene.getSurface().getMeshes()) {
            for (Triangle triangle : mesh.getTriangles()) {
                if (shadowRay.intersects(triangle).getT() >= 0 && shadowRay.intersects(triangle).getT() < lightDistance) {
                    return true;
                }
            }
        }

        return false;
    }

    private Color calculateSpecular(Color lightColor, double nDotL, Material material, Vector normal, Vector lightVector, boolean isReflection) {
        Vector viewVector = ray.getDirection().negate().normalize();
        if (depth > 0){
            if (isReflection)
                viewVector = reflectedRay.getDirection().negate().normalize();
            else viewVector = refractedRay.getDirection().negate().normalize();
        }
        Vector reflectionVector = normal.multiply(2 * nDotL).subtract(lightVector).normalize();
        double exponent = material.getPhong().getExponent();
        double specularIntensity = Math.pow(Math.max(reflectionVector.dot(viewVector), 0), exponent);
        return lightColor.multiply(material.getPhong().getKs()).multiply(specularIntensity);
    }

    private Color calculateAmbient(Color lightColor, Material material, IntersectionTest intersectionData, Shape closestShape) {
        double ka = material.getPhong().getKa();
        if (material instanceof MaterialSolid){
            Color sphereColor = ((MaterialSolid) material).getColor();
            return sphereColor.multiply(lightColor).multiply(ka);
        }
        else {
            // Texture logic
            MaterialTextured texturedMaterial = (MaterialTextured) material;
            if (closestShape instanceof Triangle) {
                Triangle triangle = (Triangle) closestShape;

                List<Vector> textureCoordinates = triangle.getTextureCoordinates();
                double a = (1 - intersectionData.getA() - intersectionData.getB()) * textureCoordinates.get(0).getX() + intersectionData.getA() * textureCoordinates.get(1).getX() + intersectionData.getB() * textureCoordinates.get(2).getX();
                double b = (1 - intersectionData.getA() - intersectionData.getB()) * textureCoordinates.get(0).getY() + intersectionData.getA() * textureCoordinates.get(1).getY() + intersectionData.getB() * textureCoordinates.get(2).getY();

                Color textureColor = texturedMaterial.getTexture().getColor(a, b);
                return textureColor.multiply(lightColor).multiply(ka);
            } else {
                    Sphere sphere = (Sphere) closestShape;
                    Vector intersectionPoint = intersectionData.getIntersectionPoint();
                    Vector sphereCenter = sphere.getCenterPoint();

                    Vector d = intersectionPoint.subtract(sphereCenter).normalize();

                    double u = 0.5 + Math.atan2(d.getX(), d.getZ()) / (2 * Math.PI);
                    double v = 0.5 - Math.asin(d.getY()) / Math.PI;

                    Color textureColor = texturedMaterial.getTexture().getColor(u, v);
                    return textureColor.multiply(lightColor).multiply(ka);
            }
        }
    }

    private Color calculateDiffuse(Color lightColor, double nDotL, Material material, IntersectionTest intersectionData, Shape closestShape) {
        double kd = material.getPhong().getKd();
        if (material instanceof MaterialSolid){
            Color sphereColor = ((MaterialSolid) material).getColor();
            return sphereColor.multiply(lightColor).multiply(kd).multiply(nDotL);
        }
        else {
            // Texture logic
            MaterialTextured texturedMaterial = (MaterialTextured) material;
            if (closestShape instanceof Triangle) {

                Triangle triangle = (Triangle) closestShape;

                List<Vector> textureCoordinates = triangle.getTextureCoordinates();
                double a = (1 - intersectionData.getA() - intersectionData.getB()) * textureCoordinates.get(0).getX() + intersectionData.getA() * textureCoordinates.get(1).getX() + intersectionData.getB() * textureCoordinates.get(2).getX();
                double b = (1 - intersectionData.getA() - intersectionData.getB()) * textureCoordinates.get(0).getY() + intersectionData.getA() * textureCoordinates.get(1).getY() + intersectionData.getB() * textureCoordinates.get(2).getY();

                Color textureColor = texturedMaterial.getTexture().getColor(a, b);
                return textureColor.multiply(lightColor).multiply(kd).multiply(nDotL);
            } else {
                Sphere sphere = (Sphere) closestShape;
                Vector intersectionPoint = intersectionData.getIntersectionPoint();
                Vector sphereCenter = sphere.getCenterPoint();

                Vector d = intersectionPoint.subtract(sphereCenter).normalize();

                double u = 0.5 + Math.atan2(d.getX(), d.getZ()) / (2 * Math.PI);
                double v = 0.5 - Math.asin(d.getY()) / Math.PI;

                Color textureColor = texturedMaterial.getTexture().getColor(u, v);
                return textureColor.multiply(lightColor).multiply(kd).multiply(nDotL);
            }
        }
    }

    private Ray getReflectedRay(Vector intersectionPoint, Vector normal, double depth) {
        Vector incidentRay = ray.getDirection().normalize();
        if (depth > 0)
            incidentRay = reflectedRay.getDirection();
        Vector reflection = incidentRay.add(normal.multiply(incidentRay.negate().dot(normal) * 2));
        return new Ray(intersectionPoint.add(normal.multiply(0.0001)), reflection);
    }

    private Ray getRefractedRay(Vector intersectionPoint, Vector normal, double n2, double depth) {
        double n1 = 1.0;  // iof of air
        Vector incidentRay = ray.getDirection().normalize();
        if (depth > 0)
            incidentRay = refractedRay.getDirection();
        double cosTheta = normal.dot(incidentRay);
        double eta;
        boolean fromInside = false;

        if (cosTheta < 0) {
            // Entering from outside
            cosTheta = -cosTheta;
            eta = n1 / n2;
        } else {
            // From inside
            fromInside = true;
            eta = n2;
            normal = normal.negate();
        }

        double valueUnderRoot = 1 - (eta * eta) * (1 - cosTheta * cosTheta);
        if (valueUnderRoot < 0) {
            // Total internal reflection
            if (fromInside)
                return getReflectedRay(intersectionPoint, normal.negate(), depth);
            else return getReflectedRay(intersectionPoint, normal, depth);
        }
        Vector tParallel = (incidentRay.add(normal.multiply(cosTheta))).multiply(eta);
        Vector tPerpendicular = normal.multiply(-Math.sqrt(valueUnderRoot));
        Vector refractedDirection = tParallel.add(tPerpendicular).normalize();

        return new Ray(intersectionPoint.add(incidentRay.multiply(1e-8)), refractedDirection);
    }

}
