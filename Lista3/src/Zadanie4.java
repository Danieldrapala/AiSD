
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Zadanie4 {

    public static void main(String[] args) {
            int vertexSize = 6;
            int edgesSize = 1000000;

            System.out.print("Podaj liczbę wierzchołków n: ");
            Scanner scanner = new Scanner(System.in);
            vertexSize = scanner.nextInt();


            System.out.print("Podaj liczbę krawędzi m: ");
            edgesSize = scanner.nextInt();
            Graph graphModel = new Graph(vertexSize,edgesSize);

            System.out.println("Podaj kolejno m definicji krawędzi w postaci : ( u , v , w ) ");
            int previous, next;
            int weight;
            for (int i = 1; i <= edgesSize; i++) {
                previous = scanner.nextInt();
                next = scanner.nextInt();
                weight = scanner.nextInt();
                Edge e=new Edge();
                e.setEdges(previous, next, weight);
                graphModel.addEdgeoneway(e);
                graphModel.edge[i-1]=e;
                System.out.println("Dodano krawędź: (" + previous + "," + next + "," + weight + ")");
            }

            Kosaraju g = new Kosaraju(graphModel);

            long start = System.currentTimeMillis();
            System.out.println("Spójne składowe  :");
            g.printSCCs();
            System.out.println("Time :"+(System.currentTimeMillis()-start));


        }



    }


