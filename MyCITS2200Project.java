/**
 * Project 1 2023 CITS2200: Graph Representation
 * This project represents the graph using adjacency list
 * each vertice represents the URL of Wikipedia page
 * each vertex is indexed by its unique id
 * for a purpose of better indexing
 * 
 * author: Edward Le 23020568
 * author: Ryan Alexander Hartono 23360449
 * 
 */
 package GraphRepresentation;

 import java.util.*;

 public class MyCITS2200Project implements CITS2200Project {
   /* 
    * assign each vertex a unique integer ID between in the range [0, V )
    * which is ID of each URL
    * create adjList which is adjacency list, adjList is a big arraylist of linkedlists
    * each linkedlist has an integer which is the unique integer ID of each URL
    */
   private ArrayList<LinkedList<Integer>> adjList = new ArrayList<>();
 
   /*
    * transposed adjacency list
    */
   private ArrayList<LinkedList<Integer>> transposeAdjList = new ArrayList<>();
 
   /*
    * urlList is an arraylist to keep each url
    */
   private ArrayList<String> urlList = new ArrayList<>();
 
   /*
    * idFromURL is a hashmap with key is an url and value is the index
    */
   private HashMap<String, Integer> idFromURL = new HashMap<>();
 
   /**
    * check if URL(vertex) already exists
    * for addVertex method later
    * 
    * @param url a String URL a vertex to check
    * @return whether vertex exists
    * 
    */
   private boolean hasVertex(String url){
     return idFromURL.containsKey(url);
   }
 
   /**
    * check if a vertex exists
    * and add a vertex which is URL to the Wikipedia page graph
    * to later create an edge
    * 
    * @param url a String URL to add
    * 
    */
   public void addVertex(String url){
     if (!hasVertex(url)) {
       int id = adjList.size();
       idFromURL.put(url, id); //each new vertex is added to the end of idFromURL which has same size of adjList
       urlList.add(url); //keep each URL added
       adjList.add(new LinkedList<>()); //create new empty linked list to the end of the graph
       transposeAdjList.add(new LinkedList<>()); //create new empty linked list to the end of the graph
     }
   }
 
   /**
    * check if an edge already exists
    * for addEdge method later
    * 
    * @param idFrom the id where edge starts
    * @param idTo the id where edge ends
    * @return whether edge exists
    * 
    */
   private boolean hasEdge(int idFrom, int idTo){
     LinkedList<Integer> neighbors = adjList.get(idFrom); //retrieves the linked list of the idFrom vertex
     return neighbors.contains(idTo);
   }
 
   /**
    * Adds an edge to the Wikipedia page graph. If the pages do not
    * already exist in the graph, they will be added to the graph.
    * 
    * @param urlFrom the URL which has a link to urlTo.
    * @param urlTo the URL which urlFrom has a link to.
    * 
    */
   public void addEdge(String urlFrom, String urlTo){
     //check if 2 vertexes exist otherwise add them to later form an edge
     //addVertex(urlTo) is called right after addVertex(urlFrom) because each edge has weight of 1
     addVertex(urlFrom);
     addVertex(urlTo);
 
     int idFrom = idFromURL.get(urlFrom); //represents the vertex with the urlFrom URL from the graph
     int idTo = idFromURL.get(urlTo);
 
     if (!hasEdge(idFrom, idTo)){
       adjList.get(idFrom).add(idTo); //creates a direct edge from the idFrom vertex to the idTo vertex.
       transposeAdjList.get(idTo).add(idFrom); // Adds the corresponding edge in the transpose graph.
     }
   }
 
   /*
   ******************************
   * Question 1 - Shortest Path *
   ******************************
   */
 
   /**
   * Finds the shortest path in number of links between two pages.
   * If there is no path, returns -1.
   * 
   * @param urlFrom the URL where the path should start.
   * @param urlTo the URL where the path should end.
   * @return the length of the shorest path in number of links followed.
   * 
   */
   public int getShortestPath(String urlFrom, String urlTo){
     int startVertex = idFromURL.get(urlFrom);
     int endVertex = idFromURL.get(urlTo);
 
     return breadthFirstSearch(startVertex, endVertex);
   }
 
   /**
    * helper method to traverse the graph
    * using breadth first search
    * 
    * @param startVertex the id where the path should start
    * @param endVertex the id where the path should end
    * @return the distance of the startVertex to endVertex
    * 
    */
   private int breadthFirstSearch(int startVertex, int endVertex){
     //if two URLs are the same, the distance is 0
     if (startVertex == endVertex){
       return 0;
     }
 
     int[] distance = new int[adjList.size()];
     Arrays.fill(distance, -1); //initialize all distances to -1, which means the vertex hasn't been visited yet
     distance[startVertex] = 0; //startVertex has distance to itself of 0
 
     Queue<Integer> queue = new LinkedList<>();
     queue.add(startVertex); //startVertex is first added into queue
 
     while (!queue.isEmpty()){
       //pop each value from queue
       int tail = queue.poll();
 
       for (int neighbor : adjList.get(tail)){
         if (distance[neighbor] == -1){ //if the neighbor hasn't been visited yet
           distance[neighbor] = distance[tail] + 1; //distance to every new vertex is increased by 1
           if (neighbor == endVertex){ //stop when reaching endVertex and get distance at that point
             return distance[neighbor];
           }
           queue.add(neighbor);
         }
       }
     }
 
     return -1; //return -1 if the path is not found
   }
 
   /*
   *********************************
   * Question 2 - Hamiltonian Path *
   *********************************
   */
   
   /**
    * Finds a Hamiltonian path in the page graph. There may be many
    * possible Hamiltonian paths. Any of these paths is a correct output.
    * This method should never be called on a graph with more than 20
    * vertices. If there is no Hamiltonian path, this method will
    * return an empty array. The output array should contain the URLs of pages
    * in a Hamiltonian path. The order matters, as the elements of the
    * array represent this path in sequence. So the element [0] is the start
    * of the path, and [1] is the next page, and so on.
    * 
    * @return a Hamiltonian path of the page graph.
    * 
    */
   public String[] getHamiltonianPath(){
     int numVertices = adjList.size();
 
     //return empty array if there are more than 20 vertices
     if (numVertices > 20){
       System.out.println("this algorithm is inappropriate for more than 20 vertices");
       return new String[0];
     }
 
     //visited is to track whether the vertex is visited yet
     //path is to store the vertexes to form Hamiltonian path
     boolean[] visited = new boolean[numVertices];
     ArrayList<String> path = new ArrayList<>();
 
     //try every vertex as a potential starting vertex
     for (int i = 0; i < numVertices; i++){
       Arrays.fill(visited, false); //reset visited array for each starting vertex
       path.clear(); //clear path for each starting vertex
 
       if (hasHamiltonianPath(i, visited, path)){
         return path.toArray(new String[0]); //return if there is the path
       }
     }
 
     //no Hamiltonian path found
     return new String[0];
   }
 
   /**
    * Recursive helper method to find a Hamiltonian path.
    *
    * @param vertex   the current vertex
    * @param visited  a boolean array to track visited vertices
    * @param path     an ArrayList to store the Hamiltonian path
    * @return true if a Hamiltonian path is found, false otherwise
    * 
    */
   private boolean hasHamiltonianPath(int vertex, boolean[] visited, ArrayList<String> path){
     visited[vertex] = true; //mark current vertex as visited
     path.add(urlList.get(vertex)); //add current vertex to path
 
     //base case: return true when path contains all vertice
     //which means the path is found
     if (path.size() == adjList.size()){
       return true;
     }
 
     //explore all neighbor using recursion
     //neighbor is check whether has been visited yet
     //to make sure each vertex in path is added only once
     for (int neighbor : adjList.get(vertex)){
       if (!visited[neighbor]){
         if (hasHamiltonianPath(neighbor, visited, path)){
           return true;
         }
       }
     }
 
     //backtrack: remove current vertex from path and mark it as unvisited
     //then return false in case the path is not found
     visited[vertex] = false;
     path.remove(path.size() - 1);
 
     return false;
   }
 
   /*
   **********************************************
   * Question 3 - Strongly Connected Components *
   **********************************************
   */
 
   /**
    * Finds all the strongly connected components of the page graph.
    * Every strongly connected component can be represented as an array 
    * containing the page URLs in the component. The return value is thus an array
    * of strongly connected components. The order of elements in these arrays
    * does not matter. Any output that contains all the strongly connected
    * components is considered correct.
    * 
    * @return an array containing every strongly connected component.
    * 
    */
   public String[][] getStronglyConnectedComponents() {
     // holds graph's strongly connected components
     ArrayList<Stack<Integer>> result = new ArrayList<>();
 
     // holds vertices by finish time in original graph.
     Stack<Integer> stack = new Stack<>();
     
     // Mark all the vertices as not visited (For first DFS)
     boolean visited[] = new boolean[urlList.size()]; // urlList.size() = num of vertices
     Arrays.fill(visited, false);
 
     // Fill vertices in stack according to their finishing time
     for (int i = 0; i < urlList.size(); i++) {
       if (!visited[i]) {
         depthFirstSearch(i, visited, stack, adjList);
       }
     }
 
     // Mark all the vertices as not visited (For second DFS)
     Arrays.fill(visited, false);
 
     // Process all vertices in order defined by Stack
     while (!stack.isEmpty()) {
       // Pop a vertex from stack
       int v = (int)stack.pop();
 
       if (!visited[v]) {
         // A new stack to store the strongly connected component
         Stack<Integer> component = new Stack<>();
         // Perform DFS starting from the popped vertex to find the connected component
         depthFirstSearch(v, visited, component, transposeAdjList);
         // Add the component stack to the result list
         // System.out.println(component);
         result.add(component);
       }
     }
     
     // Convert the strongly connected components to string arrays
     String[][] components = new String[result.size()][];
     for (int i = 0; i < result.size(); i++) {
       Stack<Integer> component = result.get(i);
       components[i] = new String[component.size()];
 
       int j = 0;
       while (!component.isEmpty()) {
         int vertex = component.pop();
         components[i][j] = urlList.get(vertex);
         j++;
       }
     }
 
     return components;
   }
 
   /**
    * function to perform depth first search
    * this function can be used for both
    * original graph and transpose one
    * 
    * @param v the current vertex
    * @param visited a boolean array to track visited vertices
    * @param stack a stack to hold graph's strongly connected components
    * @param myList the graph that is traversed
    * 
    */
   private void depthFirstSearch(int v, boolean visited[], Stack<Integer> stack, ArrayList<LinkedList<Integer>> myList){
     // Mark the current node as visited 
     visited[v] = true;
 
     // Recursion for all the vertices adjacent to this vertex
     Iterator<Integer> i = myList.get(v).iterator();
     while (i.hasNext()) {
       int n = i.next();
 
       if (!visited[n]) {
         depthFirstSearch(n, visited, stack, myList);
       }
     }
 
     // All vertices reachable from v are processed by now,
     // push v to Stack
     stack.push(v);
   }
 
   /*
   ******************************
   * Question 4 - Graph Centers *
   ******************************
   */
 
   /**
    * Finds all the centers of the page graph. The order of pages
    * in the output does not matter. Any order is correct as long as
    * all the centers are in the array, and no pages that aren't centers
    * are in the array.
    * 
    * @return an array containing all the URLs that correspond to pages that are centers.
    */
   public String[] getCenters(){
     return findCenters().toArray(new String[0]);
   }
 
   /**
    * helper method to find the centers
    * @return an arraylist that contain all the center
    * 
    */
   private ArrayList<String> findCenters() {
     int numVertices = adjList.size();
     int[] eccentricities = new int[numVertices]; //array to store eccentricity of each vertex
 
     //find the eccentricity of each vertex
     for (int i = 0; i < numVertices; i++) {
       //set maxDistance as not found yet
       //maxDistance of each new vertex is reset to -1
       int maxDistance = -1;
 
       for (int j = 0; j < numVertices; j++) {
         //find distance of current vertex to every vertex including itself
         int distance = breadthFirstSearch(i, j);
         
         if (distance > maxDistance){
           maxDistance = distance;
         }
       }
       eccentricities[i] = maxDistance; //eccentricity is the maximum length shortest path
     }
 
     //find the center(s) of the graph
     //                                       
     int minEccentricity = findMinimum(eccentricities); 
     ArrayList<String> centerList = new ArrayList<>();
 
     for (int i = 0; i < numVertices; i++) {
       if (eccentricities[i] == minEccentricity) {
         centerList.add(urlList.get(i));
       }
     }
 
     return centerList;
   }
 
   /**
    * helper method to find minimum value of a list
    *
    * @param list an integer array to find the mininum value
    * @return that mininum value
    * 
    */
   private int findMinimum(int[] list){
     int min = list[0];
     for (int num : list){
       if (num < min){
         min = num;
       }
     }
 
     return min;
   }
 } 
