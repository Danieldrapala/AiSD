
import java.util.ArrayList;
import java.util.Scanner;

public class Zadanie3 {
    public static void main (String[] args)
    {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj liczbe wierzchołków:");
        int V = scanner.nextInt();
        System.out.println("Podaj liczbe krawędzi:");
        int E = scanner.nextInt();
        Graph graph = new Graph(V, E);
        for(int i =0;i<E;i++)
        {

            int a= scanner.nextInt(); int b=scanner.nextInt(); int c=scanner.nextInt();
            graph.edge[i].setEdges(a,b,c);
            Edge ed=new Edge();ed.setEdges(a,b,c);
           if(args[0].equals("--d"))
               graph.addEdgeoneway(ed);
            else
            graph.addEdge(ed);

        }
        if(args[0].equals("--k"))
            graph.KruskalMST();
        else if(args[0].equals("--p"))
            graph.PrimMst();
        else if(args[0].equals("--d")){
            int k=scanner.nextInt();
            long start=System.nanoTime();
            graph.dijkstra(k);
            double time=(double)(System.nanoTime()-start)/1_000_000;
            System.out.println("dijkstra alghoritm time: "+time+" ms");

        }
    }
}
