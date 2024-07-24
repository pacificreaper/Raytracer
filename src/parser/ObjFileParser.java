package parser;

import Algebra.Vector;
import XMLElements.Mesh;
import XMLElements.Scene;
import shapes.Triangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ObjFileParser {
    public static void parseObjFile(Scene scene){
        // get obj file from scene
        // FIXME: change mesh list or loop through meshes
        String objFile = scene.getSurface().getMeshes().get(0).getName();

        // create buffers to store data from obj file
        List<Vector> vertices = new ArrayList<>();
        List<Vector> normals = new ArrayList<>();
        List<Vector> textureCoordinates = new ArrayList<>();
        List<Triangle> triangles = new ArrayList<>();

        // parse obj file
        try (BufferedReader reader = new BufferedReader(new FileReader(objFile))){
            String line;
            while ((line = reader.readLine()) != null){
                String[] components = line.split(" ");
                String attribute = components[0];
                System.out.println(Arrays.toString(components));
                switch (attribute) {
                    case "v":
                        double v0 = Double.parseDouble(components[1]);
                        double v1 = Double.parseDouble(components[2]);
                        double v2 = Double.parseDouble(components[3]);
                        vertices.add(new Vector(v0, v1, v2));
                        break;
                    case "vn":
                        double vn0 = Double.parseDouble(components[1]);
                        double vn1 = Double.parseDouble(components[2]);
                        double vn2 = Double.parseDouble(components[3]);
                        normals.add(new Vector(vn0, vn1, vn2));
                        break;
                    case "vt":
                        double u = Double.parseDouble(components[1]);
                        double v = Double.parseDouble(components[2]);
                        textureCoordinates.add(new Vector(u, v, 0));
                        break;
                    case "f":
                        // TODO: add faces parsing
                        // TODO: create triangles and meshes and save them approptiately to then later access
                        List<Vector> vList = new ArrayList<>();
                        List<Vector> nList = new ArrayList<>();
                        List<Vector> tList = new ArrayList<>();

                        for (int i = 0; i < 3; i++) {
                            String[] face = components[i + 1].split("/");
                            int vIndex = Integer.parseInt(face[0]) - 1;
                            vList.add(vertices.get(vIndex));

                            if (face.length > 1 && !face[1].isEmpty()) {
                                int tIndex = Integer.parseInt(face[1]) - 1;
                                tList.add(textureCoordinates.get(tIndex));
                            }

                            if (face.length > 2) {
                                int nIndex = Integer.parseInt(face[2]) - 1;
                                nList.add(normals.get(nIndex));
                            }
                        }
                        triangles.add(new Triangle(vList, nList, tList));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Mesh mesh : scene.getSurface().getMeshes()) {
            mesh.setTriangles(triangles);
        }
    }
}
