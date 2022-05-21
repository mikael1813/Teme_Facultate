package socialnetwork.domain;// Adjacency Matrix representation in Java

/**
 * Creaza un graf si niste functionalitati asupra lui
 */
public class Graph2 {
    private boolean graph[][];
    private int numVertices;

    /**
     * Metoda creaza graful cu numVertices noduri
     *
     * @param numVertices de tip int
     */
    public Graph2(int numVertices) {
        this.numVertices = numVertices;
        graph = new boolean[numVertices][numVertices];
    }

    /**
     * Metoda adauga muchia formata de nodurile src si dest in graf
     *
     * @param i de tip int
     * @param j  de tip int
     */
    public void addEdge(int i, int j) {
        graph[i][j] = true;
        graph[j][i] = true;
    }

    /**
     * Metoda sterge muchia formata de nodurile src si dest in graf
     *
     * @param i de tip int
     * @param j  de tip int
     */
    public void removeEdge(int i, int j) {
        graph[i][j] = false;
        graph[j][i] = false;
    }

    /**
     * Gaseste cel mai lung lant si returneaza lungimea lui.
     *
     * @param from The start vertex.
     * @param to The end vertex.
     * @return The length of the longest path between from and to.
     */
    public int getLongestPath(int from, int to) {
        int n = graph.length;
        boolean[][] hasPath = new boolean[1 << n][n];
        hasPath[1 << from][from] = true;
        for (int mask = 0; mask < (1 << n); mask++)
            for (int last = 0; last < n; last++)
                for (int curr = 0; curr < n; curr++)
                    if (graph[last][curr] && (mask & (1 << curr)) == 0)
                        hasPath[mask | (1 << curr)][curr] |= hasPath[mask][last];
        int result = 0;
        for (int mask = 0; mask < (1 << n); mask++)
            if (hasPath[mask][to])
                result = Math.max(result, Integer.bitCount(mask));
        return result;
    }

}