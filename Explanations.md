# CITS2200 Project Editorial

## Graph Representation
Consider a graph containing V vertices and E edges. There are two common representations of graphs: The adjacency list, and the adjacency matrix. An adjacency list is a list, for each vertex of all the vertices that are adjacent to this vertex (that is, all the edges leaving this vertex). An adjacency matrix is a V × V matrix where the element at row i , column j represents the edge between vertex i and vertex j . An adjacency list uses O(E) memory, and has the benefit of allowing us to enumerate the edges leaving a vertex in linear time, whereas an adjacency matrix would require us to check every possible edge, taking O(V^2) time. An adjacency matrix uses O(V) memory and has the benefit of allowing us to check the existence of an edge in constant time, whereas to perform the same check in an adjacency list is linear in the number of edges leaving the vertex the edge originates from, which is worst case O(V). As you will see, the algorithms used in this project do not require us to ever test for the existence of an edge, but often require us to enumerate all edges incident to a vertex. For this reason this project uses an adjacency list to represent the graph.

In this project, our vertices are represented by strings, the URL of the Wikipedia page.
Indexing by these strings can be messy and inefficient, so we instead assign each vertex a
unique integer ID between in the range [0, V). This ID will serve as an index into the
adjacency list, allowing us to find a vertex's list of neighbors in constant time. To allow us to convert back and forth between the string and integer representations of our vertices, we
introduce a list of strings that can be indexed efficiently by vertex ID, and a map from the
vertex URL to its ID.

Adding an edge to the graph requires us to first make sure both vertices exist in the graph.
To this end, we introduce the addVertex() function that checks if a vertex exists using our
map from URL to ID, and adds it to the graph if it does not. We then need simply add an
entry to the adjacency list to represent the new edge.

## Question 1: Shortest Path
In this project, our vertices represent Wikipedia pages, and our edges represent links from
one page to another. This means that our graph is directed, but unweighted. In an unweighted graph the length of the shortest path from one vertex to another is the minimum number of edges that must be traversed in order to get from our source to our destination. We can find the lengths of these shortest paths by performing a Breadth First Search (BFS), which enumerates vertices according to the number of edges they are away from our starting vertex. That is, a BFS will visit our starting vertex, then all vertices that are one edge away, then two, and so on. By maintaining an array of distances from our starting vertex, we can fill this array in as we perform our BFS. When the BFS has finished, our array will hold the length of the shortest path from our source to each vertex it can reach, or the original value of the array if no such path exists. A BFS visits each vertex at most once, and for each vertex iterates over every edge leaving that vertex. This means every vertex and every edge will be considered at most once, giving our BFS an expected and worst-case time complexity of O(V + E) , which is optimal.

## Question 2: Hamiltonian Path
A Hamiltonian path is any path through a graph that visits every vertex exactly once. Finding such a path is a well-known computationally intensive problem, which is why this question specified it would be run for at most 20 vertices. There is no known polynomial time solution
to this problem. A common algorithm for this problem is a backtracking Depth First Search (DFS), which works much like a regular DFS, but if it runs into a dead end without having found a Hamiltonian path, it backs out the way it came, marking vertices it is leaving as unvisited. This causes it to explore every path originating from its starting vertex. Repeating this process for each starting vertex allows us to explore every simple path in the graph. If this algorithm fails to find a Hamiltonian path, then there is none. In the worst case, there are as many simple paths as there are permutations of vertices, and at every step in the backtracking DFS we must consider every potential next vertex, giving a worst-case time complexity for this algorithm of O(V V!).






