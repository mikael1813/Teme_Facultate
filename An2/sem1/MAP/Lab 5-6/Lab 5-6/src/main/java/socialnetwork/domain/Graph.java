package socialnetwork.domain;
// Java program to print connected components in
// an undirected graph

import java.util.ArrayList;
import java.util.List;

/**
 * Creaza un graf si niste functionalitati asupra lui
 */
public class Graph {
    // A user define class to represent a graph.
    // A graph is an array of adjacency lists.
    // Size of array will be V (number of vertices
    // in graph)
    int V;
    ArrayList<ArrayList<Integer>> adjListArray;

    /**
     * Metoda creaza graful cu V noduri
     *
     * @param V de tip int
     */
    public Graph(int V) {
        this.V = V;
        // define the size of array as
        // number of vertices
        adjListArray = new ArrayList<>();

        // Create a new list for each vertex
        // such that adjacent nodes can be stored

        for (int i = 0; i < V; i++) {
            adjListArray.add(i, new ArrayList<>());
        }
    }

    /**
     * Metoda adauga muchia formata de nodurile src si dest in graf
     *
     * @param dest de tip int
     * @param src  de tip int
     */
    public void addEdge(int src, int dest) {
        // Add an edge from src to dest.
        adjListArray.get(src).add(dest);

        // Since graph is undirected, add an edge from dest
        // to src also
        adjListArray.get(dest).add(src);
    }

    /**
     * Metoda completeaza vectorul vizitat cu true la pozitia i daca si numai daca
     * nodul v poate ajunge la i
     *
     * @param v       de tip int
     * @param visited de tip boolean[]
     */
    public void DFSUtil(int v, boolean[] visited) {
        // Mark the current node as visited and print it
        visited[v] = true;
        //System.out.print(v + " ");
        // Recur for all the vertices
        // adjacent to this vertex
        for (int x : adjListArray.get(v)) {
            if (!visited[x])
                DFSUtil(x, visited);
        }
    }

    /**
     * Metoda compleeteaza vectorul list cu toste nodurile ce formeaza
     * componenta conexa de care apartine v
     *
     * @param v       de tip int
     * @param visited de tip boolean[]
     * @param k       de tip int
     * @param list    de tip int[]
     */
    public void DFSUtil2(int v, boolean[] visited, int[] list, int k) {
        // Mark the current node as visited and print it
        visited[v] = true;
        list[k] = v;
        k++;
        //System.out.print(v + " ");
        // Recur for all the vertices
        // adjacent to this vertex
        for (int x : adjListArray.get(v)) {
            if (!visited[x])
                DFSUtil2(x, visited,list,k);
        }
    }

    /**
     * @return numarul de componente conexe existente in graf
     */
    public int connectedComponents() {
        int s = 0;
        // Mark all the vertices as not visited
        boolean[] visited = new boolean[V];
        for (int v = 0; v < V; ++v) {
            if (!visited[v]) {
                // print all reachable vertices
                // from v
                DFSUtil(v, visited);
                s++;
            }
        }

        return s;
    }

}