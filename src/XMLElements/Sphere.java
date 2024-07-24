package XMLElements;

import Algebra.Matrix;
import Algebra.Vector;
import shapes.Shape;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sphere")
public class Sphere extends Shape {
   private Vector centerPoint;
   private double radius;
   private Material material;

   public Sphere(){}
    public Sphere(Vector centerPoint, double radius, Color color) {
        this.centerPoint = centerPoint;
        this.radius = radius;
        this.material = new Material();
    }

    public Vector getCenterPoint() {
        return centerPoint;
    }

    @XmlElement(name = "position")
    public void setCenterPoint(Vector centerPoint) {
        this.centerPoint = centerPoint;
    }

    public double getRadius() {
        return radius;
    }

    @XmlAttribute
    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Material getMaterial(){
        return this.material;
    }

    @XmlElements({
            @XmlElement(name = "material_solid", type = MaterialSolid.class),
            @XmlElement(name = "material_textured", type = MaterialTextured.class)
    })    public void setMaterial(Material material) {
        this.material =  material;
    }
}
