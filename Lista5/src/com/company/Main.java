package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private static List<List<Edge>> rGraph;

    static  int verticles=0;
    static class Edge{
        int source,destination;
        int capacity;
        Edge(int s,int d)
        {
            this.source=s;
            destination=d;
            capacity=0;
        }
        Edge(int s,int d,int c)
        {
            this.source=s;
            destination=d;
            capacity=c;
        }

        public void setCapacity(int flow) {
            this.capacity = capacity;
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

    static void getBipartiteGraphWithFlow(int k, int i) {

        ArrayList<Integer> drawn;
        int random;
        Edge a, b;
        List<List<Edge>> adjacencyLists=new ArrayList<>();
        verticles=(int)Math.pow(2,k);

        for (int src = 1; src <= (1<<k); ++src) {
            a = new Edge(0, src,1);
            b = new Edge(src, 0, 0);

            adjacencyLists.get(0).add(a);
            adjacencyLists.get(src).add(b);
            a = new Edge((1<<k) + src, adjacencyLists.size() - 1, 1);
            b = new Edge(adjacencyLists.size() - 1, (1<<k) + src, 0);

            adjacencyLists.get((1<<k) + src).add(a);
            adjacencyLists.get(adjacencyLists.size() - 1).add(b);
            drawn = new ArrayList<>();
            for (int n = 0; n < i; ++n) {
                do {
                    random = (int) (Math.random()*(1<<k));
                } while (drawn.contains(random));
                drawn.add(random);
                a = new Edge(src, (1<<k) + random + 1, 1);
                b = new Edge((1<<k) + random + 1, src, 0);

                adjacencyLists.get(src).add(a);
                adjacencyLists.get((1<<k) + random + 1).add(b);
            }
        }
        adjacencyLists.add(new ArrayList<>());
        rGraph=adjacencyLists;
    }


    static void generateGraph(int k){
        rGraph=null;
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
                        e.capacity=(int) (Math.random()*(Math.pow(2,limit)-1))+1;
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
                if (!visited[uNeighbours.get(v).destination]&& uNeighbours.get(v).capacity>0)
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
        List<List<Edge>> graphTEMP=graph;
        int max_flow = 0;
        int parent[] = new int[verticles];

        while (bfs(s, t, parent))
        {
            paths++;

            int path_flow = Integer.MAX_VALUE;
            for (v=t; v!=s; v=parent[v])
            {
                u = parent[v];
                int e =finddest(graphTEMP.get(u),v);
                path_flow = Math.min(path_flow, graphTEMP.get(u).get(e).capacity);
            }

            for (v=t; v != s; v=parent[v])
            {
                u = parent[v];
                int e =finddest(graphTEMP.get(u),v);
                graphTEMP.get(u).get(e).capacity -= path_flow;

                int f =finddest(graphTEMP.get(v),u);

                if(f==-1) {
                    graphTEMP.get(v).add(new Edge(v, u));
                    f=finddest(graphTEMP.get(v),u);
                }

                graphTEMP.get(v).get(f).capacity += path_flow;
            }
            max_flow += path_flow;
        }
        return max_flow;
    }

    public static int getPaths() {
        return paths;
    }

    public static void main(String[] args) {
        if(args.length<2) {
            int avgflow;
            long avgtime;
            int avgpaths;
            for (int i = 0; i < 17; i++) {
                avgflow = 0;
                avgpaths = 0;
                avgtime = 0;
                for (int j = 0; j < 10; j++) {
                    generateGraph(i);
                    if(i==0)
                        continue;
                    long time = System.nanoTime();
                    avgflow += 1.0 * fordFulkerson(rGraph, 0, (1 << i) - 1);
                    avgtime += 1.0 * System.nanoTime() - time;
                    avgpaths += 1.0 * paths;

                }
                java.text.DecimalFormat df=new java.text.DecimalFormat("0.000");

                System.out.println("size is: 2^"+i+" flow: " + avgflow / 10 + " in time: " + df.format((avgtime / (10.0*1000000))) + " ms, paths: " + avgpaths / 10);

            }
        }
        else{
            int k=0;

            if(args.length>2&&args[2].equals("--glpk")){
                makeglpk(k);
            }
            if(args[0].equals("--size"))
            {
                 k= Integer.parseInt(args[1]);
                generateGraph(k);
                long time = System.nanoTime();
                int flow=fordFulkerson(rGraph,0,(1<<k) -1);
                long timef= System.nanoTime()-time;
                System.out.println("flow: " + flow + " in time: " + timef/ (1000000.0)  + " ms, paths: " + paths );

            }
           // for(List<Edge>e:rGraph){
          //      for(Edge ed:e){
            //        System.out.println(ed.source+" "+ed.destination+" "+ed.weight);
          //      }
          //  }

        }


    }

    private static void makeglpk(int size) {
StringBuilder bc=new StringBuilder();
       bc.append("param n, integer, >= 2;\n\nset V, default {1..n};\n\nset E, within V cross V;\n\nparam a{(i,j) in E}, > 0;\n\nparam s, symbolic, in V, default 1;\n\nparam t, symbolic, in V, != s, default n;\n\nvar x{(i,j) in E}, >= 0, <= a[i,j];\n\nvar flow, >= 0;\n\ns.t. node{i in V}:\n\n   sum{(j,i) in E} x[j,i] + (if i = s then flow)\n\n   =\n\n   sum{(i,j) in E} x[i,j] + (if i = t then flow);\n\nmaximize obj: flow;\n\nsolve;\n\nprintf{1..56} \"=\"; printf \"\\n\";\nprintf \"Maximum flow from node %%s to node %%s is %%g\\n\\n\", s, t, flow;\n\ndata;\n");
       int k =1<<size;
       bc.append("param n :="+k +";\n\n");
        bc.append("param : E : a :=\n");
       for(List<Edge>e:rGraph){
           for(Edge ed: e)
           bc.append("\t").append(ed.source).append(" ").append(ed.destination).append(" ").append(ed.capacity).append("\n");
       }

        bc.append(";\nend;\n");
    }


}
