package XMLElements;

import shapes.Triangle;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement(name = "mesh")
public class Mesh {
    private String name;
    private Material material;
    private List<Triangle> triangles = new ArrayList<>();

    public String getName() {
        return name;
    }

    @XmlAttribute(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    public Material getMaterial() {
        return material;
    }

    @XmlElements({
            @XmlElement(name = "material_solid", type = MaterialSolid.class),
            @XmlElement(name = "material_textured", type = MaterialTextured.class)
    })
    public void setMaterial(Material material) {
        this.material = material;
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    public void setTriangles(List<Triangle> triangles) {
        this.triangles = triangles;
    }
}
