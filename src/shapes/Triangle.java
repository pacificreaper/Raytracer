package shapes;

import Algebra.Vector;

import java.util.*;

public class Triangle extends Shape {
    private List<Vector> vertices;
    private List<Vector> normals;
    private List<Vector> textureCoordinates;

    public Triangle() {
        this.vertices = new ArrayList<>();
        this.normals = new ArrayList<>();
        this.textureCoordinates = new ArrayList<>();
    }

    public Triangle(List<Vector> vertices, List<Vector> normals, List<Vector> textureCoordinates) {
        this.vertices = vertices;
        this.normals = normals;
        this.textureCoordinates = textureCoordinates;
    }

    public List<Vector> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vector> vertices) {
        this.vertices = vertices;
    }

    public List<Vector> getNormals() {
        return normals;
    }

    public void setNormals(List<Vector> normals) {
        this.normals = normals;
    }

    public List<Vector> getTextureCoordinates() {
        return textureCoordinates;
    }

    public void setTextureCoordinates(List<Vector> textureCoordinates) {
        this.textureCoordinates = textureCoordinates;
    }
}
