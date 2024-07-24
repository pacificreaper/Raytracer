package main;

import Algebra.Matrix;
import Algebra.Vector;
import Raytracer.Raytracer;
import Raytracer.Ray;
import XMLElements.Scene;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tasks {

    public void T345(Scene scene){
        //printXML(scene);
        int imageWidth = scene.getCamera().getResolution().getHorizontal();
        int imageHeight = scene.getCamera().getResolution().getVertical();
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);

        // Raytrace
        Raytracer raytracer = new Raytracer();
        raytracer.raytraceXMLFile(image, scene);

        File output = new File(scene.getOutputFile());
        try {
            ImageIO.write(image, "png", output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void printXML(Scene scene){
        System.out.println("Output File: " + scene.getOutputFile());
        System.out.println("Background color: " + scene.getBackgroundColor().toString());
        System.out.println();
        System.out.println("Camera");
        System.out.println("Position: " + scene.getCamera().getPosition().getX() + ","  + scene.getCamera().getPosition().getY() + ", " + scene.getCamera().getPosition().getZ());
        System.out.println("LookAt: " + scene.getCamera().getLookAt().getX() + ","  + scene.getCamera().getLookAt().getY() + ", " + scene.getCamera().getLookAt().getZ());
        System.out.println("Up: " + scene.getCamera().getUp().getX() + ","  + scene.getCamera().getUp().getY() + ", " + scene.getCamera().getUp().getZ());
        System.out.println("Horizontal fov: " + scene.getCamera().getHorizontalFOV().getAngle());
        System.out.println("Resolution: " + scene.getCamera().getResolution().getHorizontal() + ", " + scene.getCamera().getResolution().getVertical());
        System.out.println("Max bounces: " + scene.getCamera().getMaxBounces().getN());
        System.out.println();
        System.out.println("Lights");
        System.out.println("Ambient Light");
        scene.getLightsWrapper().getLights().forEach(light -> System.out.println("Color: "+ light.getColor().toString()));
        System.out.println();
        System.out.println("Surfaces");
        System.out.println("Sphere: ");
        scene.getSurface().getSpheres().forEach(surface -> System.out.println("Radius: " + surface.getRadius() +
                ", Position: " + surface.getCenterPoint().toString() + ", Material:  Color: " +
                       // surface.getMaterial().getColor().toString() + " , Phong" +
                        surface.getMaterial().getPhong().toString() + " , reflectance: " +
                        surface.getMaterial().getReflectance().getR() + " , Transmittance: " +
                        surface.getMaterial().getTransmittance().getT() + ", refraction: " +
                        surface.getMaterial().getRefraction().getIndexOfRefraction()
                ));
    }
}
