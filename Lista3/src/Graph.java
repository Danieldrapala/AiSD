

import java.util.*;

class Graph {
        class subset {
            int parent, rank;
        }
        int V, E;
        Edge edge[];
        int [][] matrix;
    List<List<Edge>> adjacencyLists;
    boolean[] marked;
    Edge result[];

    PriorityQueue<Edge> pq;
        Graph(int v, int e) {
            V = v;
            E = e;
            edge = new Edge[E];
            for (int i = 0; i < e; ++i)
                edge[i] = new Edge();
            matrix=new int [V][V];
            this.adjacencyLists =  new ArrayList<>();
            for (int i = 0; i < v; i++) {
                adjacencyLists.add(new LinkedList<>());
            }
        }

     int find(subset subsets[], int i) {
            // find root and make root as parent of i (path compression)
            if (subsets[i].parent != i)
                subsets[i].parent = find(subsets, subsets[i].parent);

            return subsets[i].parent;
        }

        // A function that does union of two sets of x and y
        // (uses union by rank)
        void Union(subset subsets[], int x, int y) {
            int xroot = find(subsets, x);
            int yroot = find(subsets, y);

            // Attach smaller rank tree under root of high rank tree
            // (Union by Rank)
            if (subsets[xroot].rank < subsets[yroot].rank)
                subsets[xroot].parent = yroot;
            else if (subsets[xroot].rank > subsets[yroot].rank)
                subsets[yroot].parent = xroot;

                // If ranks are same, then make one as root and increment
                // its rank by one
            else {
                subsets[yroot].parent = xroot;
                subsets[xroot].rank++;
            }
        }

        // The main function to construct MST using Kruskal's algorithm
        void KruskalMST() {
            Edge result[] = new Edge[V]; // Tnis will store the resultant MST
            int e = 0; // An index variable, used for result[]
            int i; // An index variable, used for sorted edges
            Priority_Queue pq= new Priority_Queue(E,"Edge");
            subset subsets[] = new subset[V];

            for (i = 0; i < V; ++i)
                result[i] = new Edge();
            for(i =0;i<E;++i) pq.push(edge[i]);

            for (i = 0; i < V; ++i)
                subsets[i] = new subset();

            // Create V subsets with single elements
            for (int v = 0; v < V; ++v) {
                subsets[v].parent = v;
                subsets[v].rank = 0;
            }

            // Number of edges to be taken is equal to V-1
            while (e < V - 1) {
                // Step 2: Pick the smallest edge. And increment
                // the index for next iteration
                Edge next_edge = pq.popMaxEdge();

                int x = find(subsets, next_edge.src);
                int y = find(subsets, next_edge.dest);

                // If including this edge does't cause cycle,
                // include it in result and increment the index
                // of result for next edge
                if (x != y) {
                    result[e++] = next_edge;
                    Union(subsets, x, y);
                }
            }


            System.out.println("Following are the edges in " +
                    "the constructed MST");
            int sum=0;
            for (i = 0; i < e; ++i) {
                sum+=result[i].weight;
                System.out.println(result[i].src + " -- " +
                        result[i].dest + " == " + result[i].weight);
            }
            System.out.println("MST weight is " +sum);
        }




    public void addEdge(Edge edge) {
        int v = edge.getEitherVertex();
        int w = edge.getOtherVertex(v);
        adjacencyLists.get(v).add(edge);
        adjacencyLists.get(w).add(edge);
    }
    public void addEdgeoneway(Edge edge) {
        int v = edge.getEitherVertex();
        int w = edge.getOtherVertex(v);
        adjacencyLists.get(v).add(edge);
    }
    public List<List<Edge>>  getAdjacencyList() {
        return adjacencyLists;
    }


    public void PrimMst() {
                marked = new boolean[V];
                for (boolean b:marked) b=false;
                 result =   new Edge[V];
                int eidx=0;
                 pq = new PriorityQueue<Edge>(V);

                visit( 0);
                while (!pq.isEmpty()) {
                    Edge e = pq.poll();
                    int v = e.getEitherVertex();
                    int w = e.getOtherVertex(v);
                    if (marked[v] && marked[w]) {
                        continue;
                    }
                    result[eidx++]=e;
                    if (!marked[v]) {
                        visit( v);
                    }
                    if (!marked[w]) {
                        visit(w);
                    }
                }
        System.out.println("Following are the edges in " +
                "the constructed MST");
        int sum=0;
        for (int i = 0; i < eidx; ++i) {
            sum+=result[i].weight;
            System.out.println(result[i].src + " -- " +
                    result[i].dest + " == " + result[i].weight);
        }
        System.out.println("MST weight is " +sum);
            }




            private void visit( int v) {
                marked[v] = true;

                for (Edge edge : getAdjacencyList().get(v)) {
                    if (!marked[edge.getOtherVertex(v)]) {
                        pq.offer(edge);
                    }
                }
        }



    int dist[];
    ArrayList<Integer> settled=new ArrayList<Integer>();
    Priority_Queue pq1;
    public void dijkstra(int source)
    {
        pq1 = new Priority_Queue(V,"Item");
        dist=new int[V];
        for (int i = 0; i < V; i++)
            dist[i] = Integer.MAX_VALUE;

        // Add source node to the priority queue
        pq1.insert_value(source,0);

        // Distance to the source is 0
        dist[source] = 0;
        while (settled.size() != V) {

            int u = pq1.popMaxItem();
            settled.add(u);
            eNeighbours(u);
        }
        System.out.println("The shorted path from node :");
        for (int i = 0; i < dist.length; i++)
            if(dist[i]==Integer.MAX_VALUE) {
                System.out.println(source + " to " + i + " is unreachable");
            }
            else
            System.out.println(source + " to " + i + " is "
                    + dist[i]);
    }

    // Function to process all the neighbours
    // of the passed node
    private void eNeighbours(int u)
    {
        int edgeDistance = -1;
        int newDistance = -1;

        // All the neighbors of v
        for (int i = 0; i < adjacencyLists.get(u).size(); i++) {
            Edge v = adjacencyLists.get(u).get(i);

            // If current node hasn't already been processed
            if (!settled.contains(v.getOtherVertex(u))) {
                edgeDistance = v.weight;
                newDistance = dist[u] + edgeDistance;

                // If new distance is cheaper in cost
                if (newDistance < dist[v.getOtherVertex(u)])
                    dist[v.getOtherVertex(u)] = newDistance;

                // Add the current node to the queue
                pq1.insert_value(v.getOtherVertex(u), dist[v.getOtherVertex(u)]);
            }
        }
    }

}