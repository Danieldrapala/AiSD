
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Kosaraju {

    private Graph graphModel;

    public Kosaraju(Graph graphModel){this.graphModel = graphModel;}

    void DFSUtil(int v, boolean visited[], Graph graphModel)
    {

        visited[v] = true;
        System.out.print(v + " ");


        for (Edge e: graphModel.adjacencyLists.get(v)) {
            if(!visited[e.dest])
                DFSUtil(e.dest,visited, graphModel);
        }
    }


    void fillOrder(int v, boolean visited[], Stack stack, Graph graphModel)
    {

        visited[v] = true;

        for (Edge e: graphModel.adjacencyLists.get(v)) {
            if(!visited[e.dest])
                fillOrder(e.dest, visited, stack, graphModel);
        }
        stack.push(new Integer(v));
    }

    Graph getTranspose()
    {         Graph g = new Graph(graphModel.V,graphModel.E);

        for (int i = 0; i < graphModel.V; i++)
            g.getAdjacencyList().add(new LinkedList<Edge>());
int i=0;
        for (Edge e: graphModel.edge) {
                Edge tmp= new Edge();
                tmp.setEdges(e.dest,e.src,e.weight);
                g.addEdgeoneway(tmp);
            g.edge[i++]=tmp;

        }
        return g;

    }

    public void printSCCs()
    {
        Stack stack = new Stack();
        boolean visited[] = new boolean[this.graphModel.V];

        for(int i = 0; i < this.graphModel.V; i++)
            visited[i] = false;
        for (int i = 0; i < this.graphModel.V; i++)
            if (!visited[i])
                fillOrder(i, visited, stack, this.graphModel);

        Graph graphModel = getTranspose();

        for (int i = 0; i < graphModel.V; i++)
            visited[i] = false;


        while (!stack.empty())
        {
            int v = (int)stack.pop();

            if (!visited[v]) {
                DFSUtil(v, visited, graphModel);
                System.out.println();
            }
        }
    }
}