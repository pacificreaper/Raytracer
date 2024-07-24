package XMLElements;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.XmlAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {
    private String name;
    private BufferedImage image;

    public String getName() {
        return name;
    }

    @XmlAttribute(name = "name")
    public void setName(String name) {
        this.name = name;
        readPNG();
    }

    private void readPNG(){
        try {
            this.image = ImageIO.read(new File(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Color getColor(double u, double v) {
        int width = image.getWidth();
        int height = image.getHeight();
        int x = (int) (u * width);
        int y = (int) (v * height);

        int rgb = image.getRGB(x, y);
        java.awt.Color color = new java.awt.Color(rgb);
        return new Color(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0);
    }
}
