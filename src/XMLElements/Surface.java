package XMLElements;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.ArrayList;
import java.util.List;

public class Surface {
    private List<Sphere> spheres;
    private List<Mesh> meshes;

    public Surface() {
        this.spheres = new ArrayList<>();
        this.meshes = new ArrayList<>();
    }

    public List<Sphere> getSpheres() {
        return spheres;
    }

    @XmlElements({
            @XmlElement(name = "sphere", type = Sphere.class),
    })
    public void setSpheres(List<Sphere> sphere) {
        this.spheres = sphere;
    }

    public List<Mesh> getMeshes() {
        return meshes;
    }

    @XmlElements({
            @XmlElement(name = "mesh", type = Mesh.class),
    })
    public void setMeshes(List<Mesh> meshes) {
        this.meshes = meshes;
    }

    public boolean hasMesh(){
        return !this.meshes.isEmpty();
    }
}
