
class Edge  implements Comparable<Edge> {
    int src, dest, weight;


    // Comparator function used for sorting edges
    // based on their weight
    public int compareTo(Edge compareEdge) {
        return this.weight - compareEdge.weight;
    }

    void setEdges(int s,int d, int w){
        this.src=s;
        this.dest=d;
        this.weight=w;


    }
    public int getEitherVertex() {
        return src;
    }


    public int getOtherVertex(int vertex) {
        if (vertex == src) {
            return dest;
        } else if (vertex == dest) {
            return src;
        }
        System.out.println(src+" " + dest+" "+vertex);
        throw new IllegalArgumentException("Wrong vertex!");
    }

}

