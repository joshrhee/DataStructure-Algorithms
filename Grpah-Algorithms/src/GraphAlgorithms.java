import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Sang June Rhee
 * @version 1.0
 * @userid srhee34
 * @GTID 903569008
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Input is null or start doesn't exist in the graph");
        }
        List<Vertex<T>> vS = new LinkedList<>(); //Initialize Visited Set for LinkedList(return type) as VS
        Queue<Vertex<T>> q = new LinkedList<>();  //Initialize Queue as Q
        Set<Vertex<T>> visitedSet = new HashSet<>();
        visitedSet.add(start);  //Marked start vertex in VS
        q.add(start);  //Enqueue start

        Vertex<T> v;
        while (!q.isEmpty() && graph.getVertices().size() != vS.size()) {  //While Q is not empty
            v = q.remove();  // v <- Q.dequeue
            vS.add(v);

            List<VertexDistance<T>> w = graph.getAdjList().get(v);  //Initialize w as adjacency vertex list of v
            for (int i = 0; i < w.size(); i++) {  //for all w
                if (!visitedSet.contains(w.get(i).getVertex())) {  //if w is not in VS
                    visitedSet.add(w.get(i).getVertex());   //Add to VS
                    q.add(w.get(i).getVertex());  //Q.enqueue(w)
                }
            }
        }
        return vS;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Input is null or start doesn't exist in the graph");
        }
        List<Vertex<T>> vS = new LinkedList<>(); //Initialize Visited Set for LinkedList(return type) as VS
        Set<Vertex<T>> visitedSet = new HashSet<>();

        rDFS(start, graph, vS, visitedSet); //dfs(w, graph)
        return vS;
    }

    /**
     *
     * @param u Original vertex
     * @param graph the graph to search through
     * @param <T> the generic typing of the data
     * @return list of vertices in visited order
     */

    private static <T> void rDFS(Vertex<T> u, Graph<T> graph, List<Vertex<T>> vS, Set<Vertex<T>> visitedSet) {
        if (vS.size() < graph.getVertices().size()) {
            if (!visitedSet.contains(u)) {
                visitedSet.add(u);
                vS.add(u);

                List<VertexDistance<T>> w = graph.getAdjList().get(u); //Initialize w as adjacency vertex list of u
                for (int i = 0; i < w.size(); i++) {  //for all w adjacent to u
                    rDFS(w.get(i).getVertex(), graph, vS, visitedSet);  //
                }
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Input is null or start doesn't exist in the graph");
        }
        Set<Vertex<T>> vS = new HashSet<>(); //Initialize VisitedSet as VS
        Map<Vertex<T>, Integer> dM = new HashMap<>(); //Initialize DistanceMap as DM
        PriorityQueue<VertexDistance<T>> pQ = new PriorityQueue<>(); //Initialize PriorityQueue as PQ

        //for all v in graph, initialize distance of v to infinity
        for (Vertex<T> vertices : graph.getVertices()) {
            dM.put(vertices, Integer.MAX_VALUE);
        }

        pQ.add(new VertexDistance<>(start, 0)); //PQ.enqueue(start, 0)
        dM.put(start, 0); //Starting Vertex with 0

        //while PQ is not empty and VS is not full
        while (vS.size() != graph.getVertices().size() && !pQ.isEmpty()) {
            Vertex<T> u = pQ.remove().getVertex(); //(u, d) <- PQ.dequeue
            if (!vS.contains(u)) {
                vS.add(u);
                for (VertexDistance<T> distance : graph.getAdjList().get(u)) {
                    Vertex<T> w = distance.getVertex();
                    if (!vS.contains(w)) {
                        int cost = dM.get(u) + distance.getDistance();
                        if (dM.get(w) > cost) { //Keep the smallest edge weight for the vertex
                            dM.put(w, cost);
                            pQ.add(new VertexDistance<>(w, cost));
                        }
                    }
                }
            }
        }
        return dM;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Input is null");
        }
        DisjointSet<Vertex<T>> dS = new DisjointSet<>();  //Initialize Disjointset as DS

        Set<Edge<T>> mST = new HashSet<>(); //Initialize MST EdgeSet
        PriorityQueue<Edge<T>> pQ = new PriorityQueue<>(graph.getEdges());  //Add all edges into PQ

        //while PQ is not empty and MST has fewer than n-1 edges
        while (!pQ.isEmpty() && mST.size() < 2 * (graph.getVertices().size() - 1)) {
            //Vertex<T> u = PQ.remove().getU();
            //Vertex<T> v = PQ.remove().getV();

            Edge<T> uv = pQ.remove(); //edge(u, v) <- PQ.dequeue

            //If u and v are not in the same cluster
            if (!dS.find(uv.getU()).equals(dS.find(uv.getV()))) {
                mST.add(uv); //Add edge(u, v), which is PQ.dequeue, to MST
                mST.add(new Edge<T>(uv.getV(), uv.getU(), uv.getWeight()));
                dS.union(uv.getU(), uv.getV()); //union u's and v's cluster
            }
        }
        if (mST.size() == 2 * (graph.getVertices().size() - 1)) {
            return mST;
        } else {
            return null;
        }
    }
}
