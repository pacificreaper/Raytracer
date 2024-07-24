package main;

import XMLElements.Scene;
import XMLElements.XMLParser;
import parser.ObjFileParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length  < 1) {
            System.out.println("No XML file available.");
            return;
        }

        List<String> paths = new ArrayList<>(Arrays.asList(args));

        List<Scene> scenes = new ArrayList<>();
        XMLParser parser = new XMLParser();

        for (String path: paths){
            Scene scene = parser.parseXMLFile(path);
            if (scene.getSurface().hasMesh())
                ObjFileParser.parseObjFile(scene);
            scenes.add(scene);
        }

        Tasks tasks = new Tasks();
        if (!scenes.isEmpty())
            for (Scene scene: scenes) {
                tasks.T345(scene);
            }
    }
}