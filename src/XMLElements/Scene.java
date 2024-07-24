package XMLElements;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "scene")
public class Scene {
    private String outputFile;
    private Color backgroundColor;
    private Camera camera;
    private List<Light> lights = new ArrayList<>();
    private Surface surfaces;

    public String getOutputFile() {
        return outputFile;
    }

    @XmlAttribute(name = "output_file")
    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    @XmlElement(name = "background_color")
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Camera getCamera() {
        return camera;
    }

    @XmlElement(name = "camera")
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    @XmlElement(name = "lights")
    public LightsWrapper getLightsWrapper() {
        LightsWrapper wrapper = new LightsWrapper();
        wrapper.setLights(lights);
        return wrapper;
    }

    public void setLightsWrapper(LightsWrapper wrapper) {
        this.lights = wrapper.getLights();
    }

    // Wrapper class to handle the lights element
    public static class LightsWrapper {
        private List<Light> lights;

        @XmlElements({
                @XmlElement(name = "ambient_light", type = AmbientLight.class),
                @XmlElement(name = "parallel_light", type = ParallelLight.class),
                @XmlElement(name = "point_light", type = PointLight.class),
        })
        public List<Light> getLights() {
            return lights;
        }

        public void setLights(List<Light> lights) {
            this.lights = lights;
        }
    }

    public Surface getSurface() {
        return surfaces;
    }

    @XmlElement(name = "surfaces")
    public void setSurface(Surface surfaces) {
        this.surfaces = surfaces;
    }
}
