package GraphRepresentation;

import java.io.*;
import java.util.*;

public class CITS2200ProjectTester {
    public static void loadGraph(MyCITS2200Project project, String path) {
        // The graph is in the following format:
        // Every pair of consecutive lines represent a directed edge.
        // The edge goes from the URL in the first line to the URL in the second line.
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while (reader.ready()) {
                String from = reader.readLine();
                String to = reader.readLine();
                System.out.println("Adding edge from " + from + " to " + to);
                project.addEdge(from, to);
            }
        } catch (Exception e) {
            System.out.println("There was a problem:");
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        // Change this to be the path to the graph file.
        String pathToGraphFile = "example_graph1";
        // Create an instance of your implementation.
        MyCITS2200Project proj = new MyCITS2200Project();
        // Load the graph into the project.
        loadGraph(proj, pathToGraphFile);
    
        //Test the getShortestPath method.
        String startVertex = "/wiki/Flow_network";
        String endVertex = "/wiki/Approximate_max-flow_min-cut_theorem";
        int shortestPath = proj.getShortestPath(startVertex, endVertex);
        System.out.println("Shortest path from " + startVertex + " to " + endVertex + " is: " + shortestPath);

        String[][] stronglyConnectedComponents = proj.getStronglyConnectedComponents();
        for (String[] component : stronglyConnectedComponents) {
            System.out.println(Arrays.toString(component));
        }

        String[] centers = proj.getCenters();
        for (String center : centers){
            System.out.println(center);
        }

        String[] paths = proj.getHamiltonianPath();
        for (String path : paths){
            System.out.println(path);
        }
    }
}
