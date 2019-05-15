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

    public static void main(String[] args) throws InterruptedException {
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
                        scanner.reset();
                        scanner.useDelimiter(" ");
                        String File = scanner.next();
                        String data = "";

                            try {
                                data = new String(Files.readAllBytes(Paths.get(new File("./src/examples/" + File).toURI())));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String[] make = data.split("[\\p{P} \\s]");

                            ArrayList<String> filee = new ArrayList<>();
                            for (String m : make) {
                                if (!m.equals(""))
                                    filee.add(m);
                            }
                            for (int j = 0; j < filee.size(); j++) {
                                if (trimString(filee.get(j)).equals("")) {
                                    filee.remove(j);
                                    j--;
                                } else {
                                    filee.set(j, trimString(filee.get(j)));
                                    if (filee.get(j).equals("")) {
                                        filee.remove(j);
                                        j--;
                                    }
                                }
                            }

                        tr.load(filee,0);
                            scanner.reset();
                            scanner.useDelimiter("[\\p{P} \\s]");

                        System.out.println("pomyślnie");


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
            tr.printCounters();
            tr.printOperations();
            tr.zerocounters();

        } else {
            String aspell_wordlist="aspell_wordlist.txt";
            String aspell_wordlistrandom="aspel_wordlist random";
            String lotr="lotr.txt";
            String KJB = "KJB.txt";
            String data = "";

            try {

                //data = new String(Files.readAllBytes(Paths.get((new File("./src/examples/lotr.txt")).toURI())));
                //data = new String(Files.readAllBytes(Paths.get((new File("./src/examples/sample.txt")).toURI())));
                //data = new String(Files.readAllBytes(Paths.get((new File("./src/examples/KJB.txt")).toURI())));

                data = new String(Files.readAllBytes(Paths.get((new File("./src/examples/"+aspell_wordlist)).toURI())));

            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] make = data.split("[\\p{P} \\s]");

            ArrayList<String> filee = new ArrayList<>();
            for (String m : make) {
                if (!m.equals(""))
                    filee.add(m);
            }
            for (int i = 0; i < filee.size(); i++) {
                if (trimString(filee.get(i)).equals("")) {
                    filee.remove(i);
                    i--;
                } else {
                    filee.set(i, trimString(filee.get(i)));
                    if (filee.get(i).equals("")) {
                        filee.remove(i);
                        i--;
                    }
                }
            }



            if(args[1].equals("2")) {
                int j = filee.size();
                long rand = System.nanoTime();
                while (j > 0) {
                    int i = (int) (Math.random() * j);
                    String tmp = filee.get(j - 1);
                    filee.set(j - 1, filee.get(i));
                    filee.set(i, tmp);
                    j--;
                }
                System.out.println(((double) (System.nanoTime() - rand) / 1_000_000) + " ms");
            }
            if(args[1].equals("3")) {
                int j = filee.size()/2;
                int k = filee.size()/2+1;

                int i = 0;

                long rand = System.nanoTime();
                while (i<  filee.size()) {
                    String tmp = filee.get(i);
                    filee.set(i,filee.get(j));
                    filee.set(j,tmp);
                    i++;
                    j--;
                    if(i== filee.size()) break;
                     tmp = filee.get(i);
                    filee.set(i,filee.get(k));
                    filee.set(k,tmp);
                    i++;
                    k++;
                }
                System.out.println(((double) (System.nanoTime() - rand) / 1_000_000) + " ms");
            }



            for (int i = 0; i < 3; i++) {
                Tree tr;
                if (i == 0)
                    tr = new RBT();
                else if (i == 1)
                    tr = new BST();
                else
                    tr = new SplayT();

                double time;

                System.out.println(i==0?("RBT for "+aspell_wordlistrandom):(i==1?("BST for "+aspell_wordlistrandom):("Splay for "+aspell_wordlistrandom)));

                long start = System.nanoTime();
                tr.load(filee, 0);
                time = (double) (System.nanoTime() - start) / 1_000_000;
                System.out.println("Inserting " + filee.size() + " nodes time: " + time + " ms");
                tr.printCounters();
                tr.zerocounters();
                start = System.nanoTime();
                tr.loadSearch(filee, 0);
                time = (double) (System.nanoTime() - start) / 1_000_000;

                System.out.println("Searching for " + filee.size() + " nodes time: " + time + " ms");
                tr.printCounters();
                tr.zerocounters();
                start = System.nanoTime();
                tr.loadDelete(filee, 0);
                time = (double) (System.nanoTime() - start) / 1_000_000;
                System.out.println("Deleting " + filee.size() + " nodes time: " + time + " ms");
                tr.printCounters();
                tr.zerocounters();
            }
        }
    }
}
