import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Testing {
    static String trimString(String str) {
        StringBuilder sb = new StringBuilder(str);
            while (sb.length()>0&&!Pattern.matches("[a-zA-Z]", String.valueOf(sb.charAt(0)))) {
                if (!Pattern.matches("[a-zA-Z]", String.valueOf(sb.charAt(0)))) {
                    sb.deleteCharAt(0);
                }

            }

            while (sb.length()>0&&!Pattern.matches("[a-zA-Z]", String.valueOf(sb.charAt(sb.length() - 1)))) {
                if (!Pattern.matches("[a-zA-Z]", String.valueOf(sb.charAt(sb.length() - 1)))) {
                    sb.deleteCharAt(sb.length() - 1);
                }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        if (args[1].equals("1")) {
            Scanner scanner = new Scanner(System.in);
            scanner.useDelimiter("[\\p{P} \\s]");
            System.out.println("Podaj ilość operacji:");
            int n = scanner.nextInt();
            System.out.println("delete,search,load,insert,inorder dla drzewa " + args[0].substring(2));
            Tree tr;
            switch (args[0]) {
                case "--rbt":
                    tr = new RBT();
                    break;
                case "--bst":
                    tr = new BST();
                    break;
                case "--splay":
                    tr = new SplayT();
                    break;
                default:
                    System.out.println("błędna flaga");
                    return;
            }
            int i = 0;
            while (i < n) {
                String s = scanner.next();
                switch (s) {
                    case "insert":
                        String insertingValue = scanner.next();

                        tr.insert(trimString(insertingValue));

                        break;
                    case "delete":
                        String deletingValue = scanner.next();
                        tr.delete(deletingValue);
                        break;
                    case "search":
                        String findOut = scanner.next();
                        System.out.println(tr.search(findOut) ? "1" : "0");
                        break;
                    case "load":
                        String File = scanner.next();
                        File f = new File("./examples/" + File);
                        if (f.exists()) {
                            String data = "";

                            try {
                                data = new String(Files.readAllBytes(Paths.get(f.toURI())));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String [] filee= data.split("[\\p{P} \\s]");
                            for(String loadVal:filee) {
                                while (!Pattern.matches("[a-zA-Z]", String.valueOf(loadVal.charAt(0))) || !Pattern.matches("[a-zA-Z]", String.valueOf(loadVal.charAt(loadVal.length() - 1)))) {
                                    if (!Pattern.matches("[a-zA-Z]", String.valueOf(loadVal.charAt(0)))) {
                                        loadVal = loadVal.substring(1);
                                    }
                                    if (!Pattern.matches("[a-zA-Z]", String.valueOf(loadVal.charAt(loadVal.length() - 1)))) {
                                        loadVal = loadVal.substring(0, loadVal.length() - 1);
                                    }
                                    if (loadVal.equals("")) {
                                        break;
                                    }
                                }
                            }
                           // tr.load(filee);

                        }
                        else
                            System.out.println("Taki plik nie istnieje");
                        break;
                    case "inorder":
                        if (tr.isEmpty())
                            System.out.println();
                        else
                            tr.inOrder();
                        break;

                }
                i++;
            }


        }
        else{
            Tree tr;
            if(args[1].equals("2"))
            tr=new BST();
            else if(args[1].equals("3"))
                tr=new RBT();
            else if(args[1].equals("4"))
                tr=new SplayT();
            else
                return;

            String data = "";

            try {
                data = new String(Files.readAllBytes(Paths.get((new File("./src/examples/lotr.txt")).toURI())));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] make= data.split("[\\p{P} \\s]");

            ArrayList<String> filee= new ArrayList<>();
            for (String m:make)
            {
                if(!m.equals(""))
                    filee.add(m);
            }
            for(int i=0;i<filee.size();i++) {
                if (trimString(filee.get(i)).equals("")) {
                    filee.remove(i);
                    i--;
                } else
                {
                    filee.set(i, trimString(filee.get(i)));
                    if (filee.get(i).equals("")) {
                        filee.remove(i);
                        i--;
                    }
                }
            }

            tr.load(filee);
            tr.loadSearch(filee);
            tr.inOrder();
            tr.loadDelete(filee);
        }
    }

}
