
import java.util.Scanner;

public class Zadanie1 {

    public static void main(String[] args) {
    // write your code here
        Priority_Queue pq=new Priority_Queue(100,"Item");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj ilość operacji:");
        int n = scanner.nextInt();
        int i=1;
        while(i<=n) {
            System.out.println("Wpisz funkjcję którą chcesz przeprowadzić: ");
            String s = scanner.next();
            int v,p;
            switch (s) {
                case "exit":
                    i=n+1;
                    continue;
                case "empty":
                    System.out.println("Queue is " + (pq.isEmpty() ? "empty" : "not empty"));
                    break;
                case "pop":
                    System.out.println("Max value deleted succesfully " + pq.popMaxItem());
                    break;
                case "top":
                    System.out.println("First value in queue is " + pq.maxShow());
                    break;
                case "print":
                    pq.printQue();
                    break;
                case "insert":
                     v=scanner.nextInt();
                     p =scanner.nextInt();
                    pq.insert_value(v,p);

                    break;
                case "priority":
                     v=scanner.nextInt();
                     p =scanner.nextInt();
                     pq.changePriority(v,p);
            }
            i++;
        }
    }
}
   
  
