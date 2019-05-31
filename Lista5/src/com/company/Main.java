package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private static List<List<Edge>> rGraph;

    static  int verticles=0;
    static class Edge{
        int source,destination;
        int weight;
        int flow;
        Edge(int s,int d)
        {
            this.source=s;
            destination=d;
            weight=0;
            flow=0;
        }

        public void setFlow(int flow) {
            this.flow = flow;
        }

    }
    static int H(int a) {
        int count = 0;
        while (a > 0) {
            if ((a & 1) > 0)
                count += 1;
            a = a >> 1;
        }
        return count;
    }
    static  boolean isvaluehasOneZero(int a, int b){
        int otherParts=0;
        do{
            int firstA=a&1;
            int firstB=b&1;
            if (firstA != 1&& firstB==1||firstB != 1&& firstA==1) {
                otherParts++;
            }
            if(a>0)
            a=a>>1;
            if(b>0)
             b=b>>1;
        }while(a>0||b>0);
        return otherParts==1;
    }
    static int paths;
    static void generateGraph(int k){
        List<List<Edge>> adjacencyLists=new ArrayList<>();
        verticles=(int)Math.pow(2,k);
        paths=0;
        for (int i = 0; i < (1<<k); i++) {
            int ones = H(i);
            List<Edge> list=new ArrayList<>();

            for (int j = 0; j < (1<<k); j++) {
                if(i<j) {
                    int twice = H(j);
                    int limit = Math.max(Math.max(ones, twice), Math.max(k - ones, k - twice));
                    if (isvaluehasOneZero(j, i))
                    {       Edge e = new Edge(i, j);
                        e.weight=(int) (Math.random()*(Math.pow(2,limit)-1))+1;
                    list.add(e);
                }

                }
            }
            if(!list.isEmpty())
                adjacencyLists.add(list);

        }
        adjacencyLists.add(new ArrayList<>());
        rGraph=adjacencyLists;
    }
    static boolean bfs( int s, int t, int parent[])
    {

        boolean visited[] = new boolean[verticles];
        for(int i=0; i<verticles; ++i) {
            visited[i] = false;
        }

        // Create a queue, enqueue source vertex and mark
        // source vertex as visited
        LinkedList<Integer> queue = new LinkedList<Integer>();

        queue.add(s);
        visited[s] = true;
        parent[s]=-1;

        // Standard BFS Loop
        while (queue.size()!=0)
        {

            int u = queue.poll();

            List<Edge> uNeighbours=rGraph.get(u);
            for (int v=0; v<uNeighbours.size(); v++)
            {
                if (!visited[uNeighbours.get(v).destination]&& uNeighbours.get(v).weight>0)
                {
                    queue.add(uNeighbours.get(v).destination);
                    parent[uNeighbours.get(v).destination] = u;
                    visited[uNeighbours.get(v).destination] = true;
                }
            }
        }

        return (visited[t]);
    }
    static int finddest(List<Edge> list, int dst)
    {
        for(int i=0;i<list.size();i++) {
        if(list.get(i).destination==dst)
            return i;
        }
        return -1;
    }

    static int fordFulkerson(List<List<Edge>> graph, int s, int t)
    {
        int u, v;
        List<List<Edge>> graphR=graph;
        int max_flow = 0;  // There is no flow initially
        int parent[] = new int[verticles];

        while (bfs(s, t, parent))
        {
            paths++;

            int path_flow = Integer.MAX_VALUE;
            for (v=t; v!=s; v=parent[v])
            {
                u = parent[v];
                int e =finddest(graphR.get(u),v);
                path_flow = Math.min(path_flow, graphR.get(u).get(e).weight);
            }

            for (v=t; v != s; v=parent[v])
            {
                u = parent[v];
                int e =finddest(graphR.get(u),v);

                graphR.get(u).get(e).weight -= path_flow;
                graphR.get(v).add(new Edge(v,u));
                int f =finddest(graphR.get(v),u);
                graphR.get(v).get(f).weight += path_flow;
            }
            max_flow += path_flow;
        }
        return max_flow;
    }

    public static int getPaths() {
        return paths;
    }

    public static void main(String[] args) {generateGraph(3);
        List<List<Edge>> t= rGraph;
        for (List<Edge> s:t)
        {                System.out.print("[");

            for(Edge in:s) {

                System.out.print(in.source+" to " +in.destination+" "+in.weight + " ");
                System.out.print("|");
            }
            System.out.print("]");

            System.out.println();

        }
        System.out.println(" xd");
        System.out.println(rGraph.size());
        int p=0;
        System.out.println(fordFulkerson(rGraph,0 ,(1<<3) -1));
        System.out.println(getPaths());





    }


}
