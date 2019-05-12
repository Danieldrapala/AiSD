import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Testing {

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj ilość operacji:");
        int n = scanner.nextInt();
        System.out.println("delete,search,load,insert,inorder dla drzewa "+ args[0].substring(6));
        Tree tr;
        switch(args[0]){
            case "--type rbt":
                tr= new RedBlackTree();
                break;
            case "--type bts":
                tr=new BTS();
                break;
            case "--type splay":
                tr=new SplayT();
                break;
                default:
                    System.out.println("błędna flaga");
                    return ;
        }
        int i=0;
        while(i<n){
            String s=scanner.next();
            switch (s) {
                case "insert":
                        String insertingValue = scanner.next();
                        tr.insert(insertingValue);
                    break;
                case "delete":
                    String deletingValue = scanner.next();
                    tr.delete(deletingValue);
                    break;
                case "search":
                    String findOut = scanner.next();
                        System.out.println(tr.search(findOut) ?"1":"0");
                        break;
                case "load":
                    String File = scanner.next();
                    File f = new File("./examples/"+File);
                    if(f.exists())
                    tr.load(f);
                    else
                        System.out.println("Taki plik nie istnieje");
                    break;
                case "inorder":
                    if(tr.isEmpty())
                        System.out.println();
                    else
                    tr.inOrder();
                    break;

            }
            i++;
        }


    }
}
